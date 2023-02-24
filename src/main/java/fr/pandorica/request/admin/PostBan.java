package fr.pandorica.request.admin;

import fr.pandorica.manageris.utils.Request;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class PostBan {

    UUID uuid;

    public PostBan(UUID uuid){
        this.uuid = uuid;
    }

    public void banPlayer(JSONObject jsonObject, Integer time){
        try {
            Date date = new Date();

            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.SHORT);

            jsonObject.put("date", shortDateFormat.format(date));
            new Request("/admin/ban", uuid).postWithHeader(jsonObject, "Time", String.valueOf(time));
            return;
        } catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
}
