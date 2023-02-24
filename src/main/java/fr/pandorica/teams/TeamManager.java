package fr.pandorica.teams;

import fr.pandorica.party.Party;
import fr.pandorica.party.PartyManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamManager {
    public static List<Team> teams = new ArrayList<>();

    public static void partyToTeam(UUID leader){
        Party partyManager = PartyManager.getPlayerParty(leader);
        for (Team team : teams){
            if (!team.haveMaxPlayer()){
                if (team.testMaxPlayer(partyManager.getPlayersUUID().size())){
                    for (UUID uuid : partyManager.getPlayersUUID()){
                        team.addPlayer(uuid);
                    }
                    team.setParty(true);
                }
                return;
            }
        }
    }

    public static void playerToTeam(UUID uuid){
        for (Team team : teams){
            if (!team.haveMaxPlayer()){
                team.addPlayer(uuid);
            }
        }
    }

}
