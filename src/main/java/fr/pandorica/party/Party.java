package fr.pandorica.party;

import java.util.List;
import java.util.UUID;

public class Party {
    private List<UUID> playersUUID;

    public Party(List<UUID> playersUUID){
        this.playersUUID = playersUUID;
    }

    public List<UUID> getPlayersUUID(){
        return this.playersUUID;
    }

}
