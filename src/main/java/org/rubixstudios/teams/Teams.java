package org.rubixstudios.teams;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.rubixstudios.teams.commands.TeamCommand;
import org.rubixstudios.teams.data.TeamData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Teams extends JavaPlugin {

    @Getter private static Teams instance;

    @Getter private List<TeamData> teamsList = new ArrayList<>();
    @Getter private int maxAllyCount = 2;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.getCommand("teams").setExecutor(new TeamCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void createTeam(String teamName, Player creator) {
        teamsList.add(new TeamData(teamName, creator.getUniqueId()));
    }

    public boolean isInTeam(TeamData teamData, UUID playerUUID) {
        if (teamData == null) return false;

        for (UUID uuid : teamData.getMemberList()) {
            if (uuid.equals(playerUUID)) return true;
        }
        return false;
    }

    public boolean hasTeam(UUID playerUUID) {
        for (TeamData teamData : teamsList) {
            if (isInTeam(teamData, playerUUID)) return true;
        }

        return false;
    }

    public void AddAlly(UUID teamID, UUID allyID) {
        TeamData team1 = getTeamDataByTeamID(teamID);
        TeamData team2 = getTeamDataByTeamID(allyID);

        team1.getAllyUUIDS().add(team2.getTeamID());
        team2.getAllyUUIDS().add(team1.getTeamID());
    }

    public boolean hasMaxAmountOfAllies(UUID teamID) {
        TeamData teamData = getTeamDataByTeamID(teamID);

        return teamData != null && teamData.getAllyUUIDS().size() >= maxAllyCount;
    }

    public void removeTeamByTeamID(UUID teamUUID) {
        for (TeamData teamData : (TeamData[]) teamsList.toArray()) {
            if (teamData.getTeamID().equals(teamUUID)) teamsList.remove(teamData);
        }
    }

    public void removeTeamByMemberID(UUID memberUUID) {
        for (TeamData teamData : getCopiedTeamList()) {
            for (UUID uuid : teamData.getMemberList()) {
                if (uuid.equals(memberUUID)) teamsList.remove(teamData);
            }
        }
    }

    public void removeTeamByName(String name) {
        for (TeamData teamData : getCopiedTeamList()) {
            if (teamData.getTeamName().equals(name)) teamsList.remove(teamData);
        }
    }

    public List<TeamData> getCopiedTeamList() {
        return new ArrayList<>(teamsList);
    }

    public boolean isTeamNameValid(String teamName) {
        for (TeamData teamData : teamsList) {
            if (teamData.getTeamName().equals(teamName)) return false;
        }

        return true;
    }

    public TeamData getTeamDataByTeamID(UUID teamID) {
        for (TeamData teamData : teamsList) {
            if (teamData.getTeamID().equals(teamID)) return teamData;
        }

        return null;
    }

    public TeamData getTeamDataByTeamName(String teamName) {
        for (TeamData teamData : teamsList) {
            if (teamData.getTeamName().equals(teamName)) return teamData;
        }

        return null;
    }

    public TeamData getTeamDataByMemberID(UUID playerUUID) {
        for (TeamData teamData : teamsList) {
            if (isInTeam(teamData, playerUUID)) return teamData;
        }

        return null;
    }
}
