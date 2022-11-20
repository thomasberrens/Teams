package org.rubixstudios.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.rubixstudios.teams.Teams;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class CreateTeam extends SubCommand {
    @Override
    public String getPrefix() {
        return "team";
    }

    @Override
    public String getDescription() {
        return "Creates a team";
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public boolean MeetsCondition(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        final Player player = (Player) sender;
        String teamName = args[0];

        if(Teams.getInstance().hasTeam(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You are already in a team.");
            return false;
        }

        if (args[0] == null) {
            Bukkit.broadcastMessage("Arg is null");
            return false;
        }

        if (!Teams.getInstance().isTeamNameValid(teamName)) {
            player.sendMessage(ChatColor.RED + "Chosen team name already exists.");
            return false;
        }


        return true;
    }

    @Override
    public void Run(CommandSender sender, Command command, String label, String[] args) {
        Teams.getInstance().createTeam(args[0], (Player) sender);
    }
}
