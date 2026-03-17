package net.mat0u5.lifeseries.seasons.season.heartbreaklife;

import net.mat0u5.lifeseries.config.ConfigManager;
import net.mat0u5.lifeseries.network.packets.simple.SimplePackets;
import net.mat0u5.lifeseries.registries.ItemRegistry;
import net.mat0u5.lifeseries.seasons.other.LivesManager;
import net.mat0u5.lifeseries.seasons.season.Season;
import net.mat0u5.lifeseries.seasons.season.Seasons;
import net.mat0u5.lifeseries.utils.other.TaskScheduler;
import net.mat0u5.lifeseries.utils.other.Time;
import net.mat0u5.lifeseries.utils.player.PlayerUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeartbreakLife extends Season {
    private boolean proposalPhase = false;
    private Map<UUID, UUID> partners = new HashMap<>();
    private Map<UUID, UUID> pendingProposals = new HashMap<>(); // proposer -> target

    @Override
    public Seasons getSeason() {
        return Seasons.HEARTBREAK_LIFE;
    }

    @Override
    public ConfigManager createConfig() {
        return new HeartbreakLifeConfig();
    }

    @Override
    public LivesManager createLivesManager() {
        return new HeartbreakLifeLivesManager();
    }

    @Override
    public void initialize() {
        super.initialize();
        // Give Valentine's Flower to all players at start
        for (ServerPlayer player : PlayerUtils.getAllPlayers()) {
            giveValentinesFlower(player);
        }
    }

    @Override
    public void tickSessionOn(MinecraftServer server) {
        super.tickSessionOn(server);
        if (currentSession.statusStarted() && !proposalPhase) {
            startProposalPhase();
        }
    }

    private void startProposalPhase() {
        proposalPhase = true;
        PlayerUtils.broadcastMessage(Component.literal("Heartbreak Life: Proposal phase started! You have 5 minutes to find a partner with the Valentine's Flower!").withStyle(ChatFormatting.LIGHT_PURPLE));
        // Give flowers again in case
        for (ServerPlayer player : PlayerUtils.getAllPlayers()) {
            giveValentinesFlower(player);
        }
        // Schedule end after 5 minutes
        TaskScheduler.scheduleTask(Time.minutes(5).getTicks(), this::endProposalPhase);
    }

    private void endProposalPhase() {
        proposalPhase = false;
        PlayerUtils.broadcastMessage(Component.literal("Heartbreak Life: Proposal phase ended!").withStyle(ChatFormatting.RED));
        // Assign lone wolves
        for (ServerPlayer player : PlayerUtils.getAllPlayers()) {
            if (!hasPartner(player)) {
                makeLoneWolf(player);
            }
        }
    }

    private void giveValentinesFlower(ServerPlayer player) {
        ItemStack flower = new ItemStack(ItemRegistry.VALENTINES_FLOWER);
        if (!player.getInventory().contains(flower)) {
            player.getInventory().add(flower);
        }
    }

    public void sendProposal(ServerPlayer proposer, ServerPlayer target) {
        if (pendingProposals.containsKey(proposer.getUUID()) || pendingProposals.containsValue(target.getUUID())) {
            proposer.sendSystemMessage(Component.literal("A proposal is already pending!"));
            return;
        }
        pendingProposals.put(proposer.getUUID(), target.getUUID());
        proposer.sendSystemMessage(Component.literal("Proposal sent to " + target.getName().getString() + "!"));
        target.sendSystemMessage(Component.literal(proposer.getName().getString() + " has proposed to you! ")
                .append(Component.literal("[Accept]").withStyle(ChatFormatting.GREEN)
                        .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/heartbreak accept " + proposer.getUUID()))))
                .append(Component.literal(" [Decline]").withStyle(ChatFormatting.RED)
                        .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/heartbreak decline " + proposer.getUUID())))));
    }

    public void acceptProposal(UUID proposerUUID, ServerPlayer accepter) {
        UUID targetUUID = pendingProposals.get(proposerUUID);
        if (targetUUID == null || !targetUUID.equals(accepter.getUUID())) {
            accepter.sendSystemMessage(Component.literal("No pending proposal from that player."));
            return;
        }
        ServerPlayer proposer = PlayerUtils.getPlayer(proposerUUID);
        if (proposer == null) return;
        partners.put(proposerUUID, targetUUID);
        partners.put(targetUUID, proposerUUID);
        pendingProposals.remove(proposerUUID);
        PlayerUtils.broadcastMessage(Component.literal(proposer.getName().getString() + " and " + accepter.getName().getString() + " are now partners!").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    public void declineProposal(UUID proposerUUID, ServerPlayer decliner) {
        UUID targetUUID = pendingProposals.get(proposerUUID);
        if (targetUUID == null || !targetUUID.equals(decliner.getUUID())) {
            decliner.sendSystemMessage(Component.literal("No pending proposal from that player."));
            return;
        }
        pendingProposals.remove(proposerUUID);
        ServerPlayer proposer = PlayerUtils.getPlayer(proposerUUID);
        if (proposer != null) {
            proposer.sendSystemMessage(Component.literal(decliner.getName().getString() + " declined your proposal."));
        }
        decliner.sendSystemMessage(Component.literal("Proposal declined."));
    }

    public boolean hasPartner(ServerPlayer player) {
        return partners.containsKey(player.getUUID());
    }

    public boolean isProposalPhase() {
        return proposalPhase;
    }

    @Override
    public void assignDefaultLives(ServerPlayer player) {
        super.assignDefaultLives(player);
        giveValentinesFlower(player);
    }
