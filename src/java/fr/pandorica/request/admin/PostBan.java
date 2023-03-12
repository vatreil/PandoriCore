package fr.pandorica.request.admin;


import com.google.gson.JsonObject;
import fr.pandorica.utils.Request;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;


public class PostBan {

    UUID uuid;

    public PostBan(UUID uuid){
        this.uuid = uuid;
    }

    public void banPlayer(JsonObject jsonObject, Integer time){
        try {
            Date date = new Date();

            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.SHORT);

            jsonObject.addProperty("date", shortDateFormat.format(date));
            new Request("/admin/ban", uuid).postWithHeader(jsonObject, "Time", String.valueOf(time));
            return;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
