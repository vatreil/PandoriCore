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
            jedis.persist("player:" + uuid.toString());
            jedis.close();
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }

    public String getServerInKeyPlayer(){
        try{
            String server = jedis.hget("player:" + uuid.toString(), "server");
            jedis.close();
            return server;
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return "";
    }

    public void delServerInKeyPlayer(){
        try{
            jedis.hdel("player:" + uuid.toString(), "server");
            jedis.expire("player:" + uuid.toString(), 3600);
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }



}
