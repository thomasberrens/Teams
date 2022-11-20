package org.rubixstudios.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.rubixstudios.teams.Teams;
import org.rubixstudios.teams.data.TeamData;

import java.util.UUID;

public class SetTeamOwner extends SubCommand {
    @Override
    public String getPrefix() {
        return "teams";
    }

    @Override
    public String getDescription() {
        return "Sets owner of the team";
    }

    @Override
    public String getName() {
        return "setowner";
    }

    @Override
    public boolean MeetsCondition(CommandSender sender, Command command, String label, String[] args) {
       if (!(sender instanceof Player)) return false;
       if (args == null || args.length == 0 ) {
           sender.sendMessage(ChatColor.RED + "You didn't specify a player.");
           return false;
       }

       final String newOwnerName = args[0];

        final Player player = (Player) sender;
        final UUID playerID = player.getUniqueId();
       final Player newOwner = Bukkit.getPlayer(newOwnerName);

       if (newOwner == null) {
           player.sendMessage(ChatColor.RED + newOwnerName + " is not a online player.");
           return false;
       }

       if (!Teams.getInstance().hasTeam(playerID)) {
           player.sendMessage(ChatColor.RED + "You are not in a team.");
           return false;
       }

       final TeamData teamData = Teams.getInstance().getTeamDataByMemberID(playerID);

       if (teamData.getTeamOwner().equals(playerID)){
           player.sendMessage(ChatColor.GREEN + "You're already the owner.");
           return false;
       }

       if (!Teams.getInstance().isInTeam(teamData, newOwner.getUniqueId())) {
           player.sendMessage(ChatColor.RED + newOwnerName + " is not in your team.");
           return false;
       }

        return true;
    }

    @Override
    public void Run(CommandSender sender, Command command, String label, String[] args) {
        final Player player = (Player) sender;
        final UUID playerID = player.getUniqueId();
        final Player newOwner = Bukkit.getPlayer(args[0]);
        final TeamData teamData = Teams.getInstance().getTeamDataByMemberID(playerID);

        teamData.setTeamOwner(newOwner.getUniqueId());
        player.sendMessage(ChatColor.GREEN + "You are no longer the owner.");
        newOwner.sendMessage(ChatColor.GREEN + "You are the new team owner!");
    }
}
