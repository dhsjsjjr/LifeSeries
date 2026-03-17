package net.mat0u5.lifeseries.item;

import net.mat0u5.lifeseries.Main;
import net.mat0u5.lifeseries.seasons.season.Seasons;
import net.mat0u5.lifeseries.seasons.season.heartbreaklife.HeartbreakLife;
import net.mat0u5.lifeseries.utils.player.PlayerUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ValentinesFlowerItem extends Item {
    public ValentinesFlowerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
        if (!(user instanceof ServerPlayer player) || !(entity instanceof ServerPlayer target)) {
            return InteractionResult.PASS;
        }

        if (Main.getSeason() != Seasons.HEARTBREAK_LIFE) {
            return InteractionResult.PASS;
        }

        HeartbreakLife season = (HeartbreakLife) Main.currentSeason;
        if (!season.isProposalPhase()) {
            player.sendSystemMessage(Component.literal("The proposal phase has ended!"));
            return InteractionResult.PASS;
        }

        if (season.hasPartner(player) || season.hasPartner(target)) {
            player.sendSystemMessage(Component.literal("One of you already has a partner!"));
            return InteractionResult.PASS;
        }

        season.sendProposal(player, target);
        stack.shrink(1); // Consume the item
        return InteractionResult.SUCCESS;
    }
}