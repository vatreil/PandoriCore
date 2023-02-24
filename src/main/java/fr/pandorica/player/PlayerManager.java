package fr.pandorica.player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private static HashMap<UUID, Player> players = new HashMap<>();

    public static Player getPlayer(UUID uuidPlayer){
        return players.get(uuidPlayer);
    }
}
