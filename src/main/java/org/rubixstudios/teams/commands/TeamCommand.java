package org.rubixstudios.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TeamCommand implements CommandExecutor {

    private final List<SubCommand> commandList = new ArrayList<>();

    public TeamCommand() {
    initialize();
    }

    private void initialize() {
        commandList.add(new CreateTeam());
        commandList.add(new DisbandTeam());
        commandList.add(new SetTeamOwner());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args == null || args.length == 0 || args[0].equalsIgnoreCase("help")) {


            return true;
        }

        for (SubCommand subCommand : commandList) {
            if (subCommand.getName().equals(args[0]) && subCommand.MeetsCondition(sender, command, label, skipIndex(args, 0))) {
                Bukkit.broadcastMessage("GO");
                subCommand.Run(sender, command, label, args);
                return true;
            }
        }

        return false;
    }

    public String[] skipIndex(String[] list, int indexToSkip) {
        List<String> filteredList = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            if (i == indexToSkip) continue;
            String element = list[i];
            filteredList.add(element);
        }

        return filteredList.toArray(new String[list.length - 1]);
    }
}
