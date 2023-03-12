package fr.pandorica.request.admin;

import com.google.gson.JsonObject;
import fr.pandorica.utils.Request;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;


public class PostMute {

    UUID uuid;

    public PostMute(UUID uuid){
        this.uuid = uuid;
    }

    public void mutePlayer(JsonObject jsonObject, Long time){
        try {
            Date date = new Date();

            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.SHORT);

            jsonObject.addProperty("date", shortDateFormat.format(date));

            new Request("/admin/mute", uuid).postWithHeader(jsonObject, "Time", String.valueOf(time));
            return;
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
