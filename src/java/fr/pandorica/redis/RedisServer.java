package fr.pandorica.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisServer {

    private Jedis jedis = RedisManager.getJedis();

    //UPDATE de l'index des serveurs
    public void updateIndexServer(String type, int nbplayer, String alias){
        try {
            jedis.zadd(type, nbplayer, alias);
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();

        }
    }

    public boolean playerIsConnect(String player){
        try{
            String playerUuid = jedis.hget("index:pseudo", player);
            if (playerUuid != null){
                return true;
            } else {
                return false;
            }
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return false;
    }

}
