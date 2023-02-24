package fr.pandorica.request.admin;

import fr.pandorica.manageris.utils.Request;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class PostMute {

    UUID uuid;

    public PostMute(UUID uuid){
        this.uuid = uuid;
    }

    public void mutePlayer(JSONObject jsonObject, Long time){
        try {
            Date date = new Date();

            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.SHORT);

            jsonObject.put("date", shortDateFormat.format(date));

            new Request("/admin/mute", uuid).postWithHeader(jsonObject, "Time", String.valueOf(time));
            return;
        } catch (IOException e){
            e.printStackTrace();
        }
        return;
    }
}
