package org.rubixstudios.teams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.rubixstudios.teams.Teams;
import org.rubixstudios.teams.data.TeamData;

import java.util.UUID;

public class DeleteTeam extends SubCommand {
    @Override
    public String getPrefix() {
        return "teams";
    }

    @Override
    public String getDescription() {
        return "Disbands the team.";
    }

    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public boolean MeetsCondition(CommandSender sender, Command command, String label, String[] args) {
       if (!(sender instanceof Player)) return false;

       final Player player = (Player) sender;
        final UUID playerUUID = player.getUniqueId();

       if (!Teams.getInstance().hasTeam(playerUUID)) {
           player.sendMessage(ChatColor.RED + "You are not in a team");
           return false;
       }

        final TeamData teamData = Teams.getInstance().getTeamDataByMemberID(playerUUID);

       if (!teamData.getTeamOwner().equals(playerUUID)) {
           player.sendMessage(ChatColor.RED + "You are not the team owner.");
           return false;
       }

       return true;
    }

    @Override
    public void Run(CommandSender sender, Command command, String label, String[] args) {
        Teams.getInstance().removeTeamByMemberID(((Player) sender).getUniqueId());
        sender.sendMessage(ChatColor.GREEN + "Succesfully removed your team.");
    }
}
