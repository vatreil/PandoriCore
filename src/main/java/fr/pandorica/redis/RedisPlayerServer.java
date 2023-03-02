package fr.pandorica.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RedisPlayerServer {

    private Jedis jedis = RedisManager.getJedis();
    private UUID uuid;

    public RedisPlayerServer(UUID uuid){
        this.uuid = uuid;
    }

    public void addServerInKeyPlayer(String serverName){
        Map<String, String> map = new HashMap<>();
        map.put("server", serverName);
        try{
            jedis.hset("player:" + uuid.toString(), map);
            jedis.close();
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }

    public void delServerInKeyPlayer(){
        try{
            jedis.hdel("player:" + uuid.toString(), "server");
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }



}
