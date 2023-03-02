package fr.pandorica.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.UUID;

public class RedisInfoPlayer {

    private Jedis jedis = RedisManager.getJedis();

    public UUID getUUIDPlayer(String pseudo){
        try{
            UUID uuid =  UUID.fromString(jedis.hget("index:pseudo", pseudo));
            jedis.close();
            return uuid;
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return null;
    }
}
