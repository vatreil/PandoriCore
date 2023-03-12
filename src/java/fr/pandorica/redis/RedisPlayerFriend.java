package fr.pandorica.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.UUID;

public class RedisPlayerFriend {

    UUID uuid;

    public RedisPlayerFriend(UUID uuid){
        this.uuid = uuid;
    }

    public void setKeyRequestFriend(UUID receiver){
        try{
            Jedis jedis = RedisManager.getJedis();
            jedis.set("request:" + receiver.toString(), uuid.toString());
            jedis.expire("request:" + receiver.toString(), 60);
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }

    public void delKeyRequestFriend(){
        try{
            Jedis jedis = RedisManager.getJedis();
            jedis.del("request:" + uuid.toString());
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }

    public String getKeyRequestFriend(){
        try{
            Jedis jedis = RedisManager.getJedis();
            return jedis.get("request:" + uuid.toString());
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean haveRequestCurrently(){
        try{
            Jedis jedis = RedisManager.getJedis();
            return jedis.exists("request:" + uuid.toString());
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return false;
    }
}
