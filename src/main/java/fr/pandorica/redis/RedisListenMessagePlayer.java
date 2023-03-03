package fr.pandorica.redis;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.minestom.server.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

public class RedisListenMessagePlayer implements Runnable {

    private String srvname;
    private List<Map.Entry<String, List<StreamEntry>>> messages;
    private static Jedis jedis;

    public RedisListenMessagePlayer(String url, String pwd, String srvname) {
        this.srvname = srvname;
        jedis = new Jedis(url, 6379);
        jedis.auth(pwd);

        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("up", "up");
        jedis.xadd(srvname, null, messageBody);
        try{
            jedis.xgroupCreate(srvname, srvname, null, false);
        }catch (JedisException e){
            System.out.println( String.format(" Group '%s' already exists", srvname));
        }

    }


    @Override
    public void run(){

        Map.Entry<String, StreamEntryID> entry = new AbstractMap.SimpleImmutableEntry<>(srvname, new StreamEntryID().UNRECEIVED_ENTRY);

        while(true) {
            try {
                messages = jedis.xreadGroup(srvname, srvname, 1, 10000L, false, entry);
            } catch (JedisConnectionException e) {
                break;
            }
            if(messages != null) {
                Player player;

                for (Map.Entry<String, List<StreamEntry>> message : messages) {



                    StreamEntry streamEntry = message.getValue().get(0);
                    Map<String, String> body = new HashMap(message.getValue().get(0).getFields());
                    if(body.get("uuid") != null & body.get("msg") != null) {
                        UUID uuidPlayer = UUID.fromString(body.get("uuid"));
                        player = (Player) Player.getEntity(uuidPlayer);

                        if(body.get("cmd") != null){
                            System.out.println("with command");
                            Component profile = Component.text(body.get("msg")).clickEvent(Component.text().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, body.get("cmd"))).build().clickEvent());
                            player.sendMessage(profile);
                        } else {
                            player.sendMessage(body.get("msg"));
                        }


                    } else if (body.get("up") != null){
                        System.out.println("ListenStatus: up");
                    } else {
                        return;
                    }


                    //TextComponent msg = (TextComponent)body.get("msg");

                    jedis.xack(srvname, srvname, streamEntry.getID());
                }

            }
        }
        messages = null;
    }




}
