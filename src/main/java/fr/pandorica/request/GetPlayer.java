package fr.pandorica.request;

import com.google.gson.JsonObject;
import fr.pandorica.utils.Request;

import java.io.IOException;
import java.util.UUID;

public class GetPlayer {
    UUID uuid;

    public GetPlayer(UUID uuid){
        this.uuid = uuid;
    }

    public Long getTemps() {
        try{
            JsonObject json = new Request("/player/temps", uuid).get();
            Long temps = json.get("temps").getAsLong();
            return temps;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public UUID getPlayerUUID(String pseudo) {
        try{
            JsonObject json =  new Request("/player/uuid", uuid).getWithHeader("Pseudo", pseudo);

            return UUID.fromString(String.valueOf(json.get("_id")));
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Boolean getPlayerExist(String pseudo) {
        try{
            JsonObject json = new Request("/player/exist/pseudo", uuid).getWithHeader("Pseudo", pseudo);
            return  json.get("playerExist").getAsBoolean();
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }


    public void updateTemps(Long temps) {
        try{
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("temps", temps);
            new Request("/player/temps/update", uuid).post(jsonObject);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getPseudo() {
        try{
            JsonObject json = new Request("/player/pseudo", uuid).get();
            return String.valueOf(json.get("pseudo"));
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public int getRank() {
        try{
            JsonObject jsonObject = new Request("/player/rank", uuid).get();
            System.out.println(jsonObject.get("rank"));
            return jsonObject.get("rank").getAsInt();
        } catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }
}
