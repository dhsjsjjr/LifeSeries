package net.mat0u5.lifeseries.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.mat0u5.lifeseries.Main;
import net.mat0u5.lifeseries.command.manager.Command;
import net.mat0u5.lifeseries.seasons.season.Seasons;
import net.mat0u5.lifeseries.seasons.season.heartbreaklife.HeartbreakLife;
import net.mat0u5.lifeseries.utils.player.PlayerUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class HeartbreakLifeCommand extends Command {
    @Override
    public boolean isAllowed() {
        return Main.getSeason() == Seasons.HEARTBREAK_LIFE;
    }

    @Override
    public Component getBannedText() {
        return Component.literal("This command is only available in Heartbreak Life.");
    }

    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("heartbreak")
                .then(Commands.literal("accept")
                        .then(Commands.argument("proposer", StringArgumentType.string())
                                .executes(ctx -> {
                                    if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) return 0;
                                    if (Main.getSeason() != Seasons.HEARTBREAK_LIFE) return 0;
                                    HeartbreakLife season = (HeartbreakLife) Main.currentSeason;
                                    try {
                                        UUID proposerUUID = UUID.fromString(StringArgumentType.getString(ctx, "proposer"));
                                        season.acceptProposal(proposerUUID, player);
                                    } catch (Exception e) {
                                        player.sendSystemMessage(Component.literal("Invalid UUID."));
                                    }
                                    return 1;
                                })))
                .then(Commands.literal("decline")
                        .then(Commands.argument("proposer", StringArgumentType.string())
                                .executes(ctx -> {
                                    if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) return 0;
                                    if (Main.getSeason() != Seasons.HEARTBREAK_LIFE) return 0;
                                    HeartbreakLife season = (HeartbreakLife) Main.currentSeason;
                                    try {
                                        UUID proposerUUID = UUID.fromString(StringArgumentType.getString(ctx, "proposer"));
                                        season.declineProposal(proposerUUID, player);
                                    } catch (Exception e) {
                                        player.sendSystemMessage(Component.literal("Invalid UUID."));
                                    }
                                    return 1;
                                }))));
    }
}