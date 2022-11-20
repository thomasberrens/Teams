package org.rubixstudios.teams.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamData {
    @Getter private UUID teamID;
    @Getter @Setter private UUID teamOwner;
    @Getter private String teamName;
    @Getter private List<UUID> memberList;
    @Getter private List<UUID> allyUUIDS;

    public TeamData(String teamName, UUID teamOwner) {
        this.memberList = new ArrayList<>();
        this.allyUUIDS = new ArrayList<>();

        teamID = UUID.randomUUID();

        this.teamOwner = teamOwner;

        memberList.add(teamOwner);
        this.teamName = teamName;
    }

}
