package fr.pandorica.redis;

import redis.clients.jedis.Jedis;

public class RedisManager {


    private String url;
    private String pwd;

    private static Jedis jedis;

    public RedisManager(String url, String pwd) {
        this.url = url;
        this.pwd = pwd;
    }

    public static Jedis getJedis() {
        return jedis;
    }

    public void connexion() {
        if(!IsOnline()) {
            jedis = new Jedis(url, 6379);
            jedis.connect();
            System.out.println("[Redis] Succefully connected !");
        }
    }

    public void deconnexion() {
        if(IsOnline()) {
            jedis.disconnect();
            System.out.println("[Redis] Connection closed.");
        }
    }

    public boolean IsOnline() {
        if((jedis != null)) {
            return true;
        }
        return false;
    }
}
