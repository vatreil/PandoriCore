package fr.pandorica.request.admin;

import com.google.gson.JsonObject;
import fr.pandorica.utils.Request;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;


public class PostWarn {

    UUID uuid;

    public PostWarn(UUID uuid){
        this.uuid = uuid;
    }

    public void warnPlayer(JsonObject jsonObject){
        try {
            Date date = new Date();

            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.SHORT);

            jsonObject.addProperty("date", shortDateFormat.format(date));
            new Request("/admin/warn", uuid).post(jsonObject);
            return;
        } catch (IOException e){
            e.printStackTrace();
        }
        return;
    }
}
