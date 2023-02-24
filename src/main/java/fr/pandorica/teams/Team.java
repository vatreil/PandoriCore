package fr.pandorica.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {

    private TeamColor teamColor;
    private Integer maxPlayer;
    private Boolean party;
    private List<UUID> players;

    public Team(TeamColor teamColor, Integer maxPlayer){
        this.teamColor = teamColor;
        this.maxPlayer = maxPlayer;
        this.players = new ArrayList<>();
    }

    public Boolean haveMaxPlayer(){
        return players.size()+1 >= maxPlayer;
    }

    public Boolean testMaxPlayer(Integer integer){
        return players.size()+integer <= maxPlayer;
    }

    public Boolean haveParty(){
        return party;
    }
    public void setParty(Boolean party){
        this.party = party;
    }

    public void addPlayer(UUID uuid){
        this.players.add(uuid);
    }
    public void removePlayer(UUID uuid){
        this.players.remove(uuid);
    }
}
