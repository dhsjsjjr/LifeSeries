package net.mat0u5.lifeseries.command.manager;

import com.mojang.brigadier.CommandDispatcher;
import net.mat0u5.lifeseries.command.HeartbreakLifeCommand;
import net.mat0u5.lifeseries.seasons.boogeyman.BoogeymanCommand;
import net.mat0u5.lifeseries.seasons.season.doublelife.DoubleLifeCommands;
import net.mat0u5.lifeseries.seasons.season.nicelife.NiceLifeCommands;
import net.mat0u5.lifeseries.seasons.season.nicelife.NiceLifeTriviaCommand;
import net.mat0u5.lifeseries.seasons.season.pastlife.PastLifeCommands;
import net.mat0u5.lifeseries.seasons.season.secretlife.SecretLifeCommands;
import net.mat0u5.lifeseries.seasons.season.wildlife.WildLifeTriviaCommand;
import net.mat0u5.lifeseries.seasons.season.wildlife.WildLifeCommands;
import net.mat0u5.lifeseries.seasons.secretsociety.SocietyCommands;
import net.mat0u5.lifeseries.seasons.subin.SubInCommands;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    public static List<Command> commands = new ArrayList<>();
    private static void loadCommands() {
        commands.add(new net.mat0u5.lifeseries.command.LifeSeriesCommand());
        commands.add(new net.mat0u5.lifeseries.command.SessionCommand());
        commands.add(new net.mat0u5.lifeseries.command.LivesCommand());
        commands.add(new net.mat0u5.lifeseries.command.ClaimKillCommand());

        commands.add(new net.mat0u5.lifeseries.seasons.boogeyman.BoogeymanCommand());
        commands.add(new net.mat0u5.lifeseries.command.GivelifeCommand());
        commands.add(new net.mat0u5.lifeseries.command.WatcherCommand());
        commands.add(new net.mat0u5.lifeseries.seasons.subin.SubInCommands());
        commands.add(new net.mat0u5.lifeseries.seasons.secretsociety.SocietyCommands());

        commands.add(new net.mat0u5.lifeseries.seasons.season.doublelife.DoubleLifeCommands());
        commands.add(new net.mat0u5.lifeseries.seasons.season.secretlife.SecretLifeCommands());
        commands.add(new net.mat0u5.lifeseries.seasons.season.wildlife.WildLifeCommands());
        commands.add(new net.mat0u5.lifeseries.seasons.season.pastlife.PastLifeCommands());
        commands.add(new net.mat0u5.lifeseries.seasons.season.nicelife.NiceLifeCommands());

        commands.add(new net.mat0u5.lifeseries.seasons.season.wildlife.wildcards.wildcard.trivia.WildLifeTriviaCommand());
        commands.add(new net.mat0u5.lifeseries.seasons.season.nicelife.NiceLifeTriviaCommand());

        commands.add(new net.mat0u5.lifeseries.command.HeartbreakLifeCommand());

        commands.add(new net.mat0u5.lifeseries.command.SelfMessageCommand());
        commands.add(new net.mat0u5.lifeseries.command.SideTitleCommand());
        commands.add(new net.mat0u5.lifeseries.command.TestingCommands());
        commands.add(new net.mat0u5.lifeseries.command.OtherCommands());
        commands.add(new net.mat0u5.lifeseries.command.LifeSkinsCommand());
    }

    public static void registerAllCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess, net.minecraft.commands.Commands.CommandSelection registrationEnvironment) {
        if (commands.isEmpty()) {
            loadCommands();
        }
        for (Command command : commands) {
            command.register(dispatcher, commandRegistryAccess);
        }
    }
}
