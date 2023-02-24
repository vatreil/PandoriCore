package fr.pandorica.party;

import java.util.HashMap;
import java.util.UUID;

public class PartyManager {

    public static HashMap<UUID, Party> partyplayer = new HashMap<>();


    public static Party getPlayerParty(UUID leaderUUID){
        return partyplayer.get(leaderUUID);
    }

    public static void putPartyManager(UUID leaderUUID, Party party){
        partyplayer.put(leaderUUID, party);
    }

}
