package fr.pandorica.redis;

import fr.pandorica.friend.FriendMessage;
import fr.pandorica.info.InfoMessage;
import fr.pandorica.party.PartyMessage;
import fr.pandorica.redis.MessagePlayer.MessageType;
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
        jedis.connect();

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

                for (Map.Entry<String, List<StreamEntry>> message : messages) {



                    StreamEntry streamEntry = message.getValue().get(0);
                    Map<String, String> body = new HashMap(message.getValue().get(0).getFields());
                    if (body.get("type") != null){
                        switch (MessageType.ids.get(Integer.parseInt(body.get("type")))){
                            case SEND_FRIEND:
                                new FriendMessage().send(body);
                                break;
                            case SEND_PARTY:
                                new PartyMessage().send(body);
                                break;
                            case SEND_INFO:
                                new InfoMessage().send(body);
                                break;
                        }
                    } else if (body.get("up") != null){
                        System.out.println("ListenStatus: up");
                    } else {
                        return;
                    }

                    jedis.xack(srvname, srvname, streamEntry.getID());
                }

            }
        }
        messages = null;
    }




}
