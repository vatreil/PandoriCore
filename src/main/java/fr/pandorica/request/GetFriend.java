package fr.pandorica.request;


import com.google.gson.JsonObject;
import fr.pandorica.utils.Request;

import java.io.IOException;
import java.util.*;

public class GetFriend {

    UUID uuid;

    public GetFriend(UUID uuid){
        this.uuid = uuid;
    }

    public List<String> getFriendsList(){
        List<String> list = new ArrayList<>();
        try{
            JsonObject json = new Request("/player/friends", uuid).get();
            System.out.println(json.get("friends"));
            List<String> friends = (List<String>) json.get("friends");
            return friends;
        } catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public Boolean isFriendWith(UUID uuidReceiver){
        try{
            JsonObject json = new Request("/player/friends/with", uuid).getWithHeader("FriendUUID", uuidReceiver.toString());
            return json.get("isFriendWith").getAsBoolean();
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public Boolean isAllow(){
        try{
            JsonObject json = new Request("/player/friends/isallow", uuid).get();
            Boolean bool = json.get("isAllow").getAsBoolean();
            return bool;
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
}
