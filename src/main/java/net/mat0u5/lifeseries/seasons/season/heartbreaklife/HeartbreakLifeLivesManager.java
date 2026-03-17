package net.mat0u5.lifeseries.seasons.season.heartbreaklife;

import net.mat0u5.lifeseries.seasons.other.LivesManager;
import net.mat0u5.lifeseries.utils.player.TeamUtils;
import net.minecraft.ChatFormatting;

public class HeartbreakLifeLivesManager extends LivesManager {
    @Override
    public void createTeams() {
        TeamUtils.createTeam("lives_null", "Unassigned", ChatFormatting.GRAY);
        TeamUtils.createTeam("lives_0", "Dead", ChatFormatting.DARK_GRAY);
        TeamUtils.createTeam("lives_1", "Red", ChatFormatting.RED);
        TeamUtils.createTeam("lives_2", "Purple", ChatFormatting.DARK_PURPLE);
        TeamUtils.createTeam("lives_3", "Pink", ChatFormatting.LIGHT_PURPLE);
    }
}
