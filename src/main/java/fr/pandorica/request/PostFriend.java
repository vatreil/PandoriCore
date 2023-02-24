package fr.pandorica.request;


import com.google.gson.JsonObject;
import fr.pandorica.utils.Request;

import java.io.IOException;
import java.util.UUID;

public class PostFriend {

    UUID uuid;

    public PostFriend(UUID uuid){
        this.uuid = uuid;
    }

    public void removeFriend(UUID uuidReceiver){
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("friends", uuidReceiver.toString());
            new Request("/player/friends/remove", uuid).post(jsonObject);
            return;
        } catch (IOException e){
            e.printStackTrace();
        }
        return;
    }

    public void setAllow(Boolean bool){
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("friend_allow", bool);
            new Request("/player/friends/setallow", uuid).post(jsonObject);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
