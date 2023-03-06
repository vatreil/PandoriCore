package fr.pandorica.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Set;
import java.util.UUID;

public class RedisPlayerParty {

    private UUID uuid;

    public RedisPlayerParty(UUID uuid){
        this.uuid = uuid;
    }

    public boolean hasProfilParty(){
        String party = null;
        try{
            Jedis jedis = RedisManager.getJedis();
            party = jedis.hget("player:" + uuid, "party");
            jedis.close();
            return (party != null);
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return false;
    }

    public void createProfilParty(String uuidLeader) {
        try {
            Jedis jedis = RedisManager.getJedis();
            jedis.hset("player:" + uuid, "party", uuidLeader);
            jedis.sadd("party:" + uuidLeader, uuid.toString());
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }


    public Set<String> getPlayersParty(){
        try {
            Jedis jedis = RedisManager.getJedis();
            Set<String> players = jedis.smembers("party:" + uuid);
            jedis.close();
            return players;
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return null;
    }

    public int getIntPlayerParty(){
        try {
            Jedis jedis = RedisManager.getJedis();
            Long nbplayer = jedis.scard("party:" + uuid);
            jedis.close();
            return nbplayer.intValue();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return 0;
    }

    public String getLeader(){
        try {
            Jedis jedis = RedisManager.getJedis();
            String leader = jedis.hget("player:" + uuid, "party");
            jedis.close();
            return leader;
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean hisLeaderParty(){
        try {
            Jedis jedis = RedisManager.getJedis();
            String leader = jedis.hget("player:" + uuid, "party");
            jedis.close();
            System.out.println(uuid + "   " + leader);
            return (leader != null)?((leader.equals(uuid.toString()))?true:false): false;
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return false;
    }

    public void setKeyRequestParty(UUID receiver){
        try{
            Jedis jedis = RedisManager.getJedis();
            jedis.set(receiver.toString(), uuid.toString());
            jedis.expire(receiver.toString(), 60);
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }

    public void delKeyRequestParty(){
        try{
            Jedis jedis = RedisManager.getJedis();
            jedis.del(uuid.toString());
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }

    public String getKeyRequestParty(){
        try{
            Jedis jedis = RedisManager.getJedis();
            return jedis.get(uuid.toString());
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean haveRequestCurrently(){
        try{
            Jedis jedis = RedisManager.getJedis();
            return jedis.exists(uuid.toString());
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return false;
    }

    public void delPlayerParty(UUID uuidLeader){
        try{
            Jedis jedis = RedisManager.getJedis();
            jedis.hdel("player:" + uuid.toString(), "party");
            jedis.srem("party:" + uuidLeader.toString(), uuid.toString());
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }

    public void delParty(){
        try{
            Jedis jedis = RedisManager.getJedis();
            Set<String> set = jedis.smembers("party:" + uuid);
            for(String str: set){
                jedis.hdel("player:" + str, "party");
            }
            jedis.del("party:" + uuid);
            jedis.close();
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }
}
