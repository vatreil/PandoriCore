package fr.pandorica.request.admin;

import fr.pandorica.manageris.utils.Request;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;


public class PostWarn {

    UUID uuid;

    public PostWarn(UUID uuid){
        this.uuid = uuid;
    }

    public void warnPlayer(JSONObject jsonObject){
        try {
            Date date = new Date();

            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.SHORT);

            jsonObject.put("date", shortDateFormat.format(date));
            new Request("/admin/warn", uuid).post(jsonObject);
            return;
        } catch (IOException e){
            e.printStackTrace();
        }
        return;
    }
}
