package fr.pandorica.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Map;

public class RedisSendStream {

    private Map<String, String> messageBody;
    private String nameQueue;
    private Jedis jedis;

    public RedisSendStream(String nameQueue, Map<String, String> messageBody){
        this.messageBody = messageBody;
        this.nameQueue = nameQueue;
        this.jedis = RedisManager.getJedis();
    }

    public void sendMessage() {
        try {
            jedis.xadd(nameQueue, null, this.messageBody);
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }
}
