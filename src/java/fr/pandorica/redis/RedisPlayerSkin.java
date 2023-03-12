package fr.pandorica.redis;

import net.minestom.server.entity.PlayerSkin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Map;
import java.util.UUID;

public class RedisPlayerSkin {

    public static PlayerSkin getSkin(UUID uuid){
        try {
            Map skin = RedisManager.getJedis().hgetAll("skin-" + uuid);
            if (skin != null && !skin.isEmpty()) {
                return new PlayerSkin((String) skin.get("textures"), (String) skin.get("signature"));
            } else {
                return putSkin(uuid);
            }
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return PlayerSkin.fromUsername("Waggo");
    }

    private static PlayerSkin putSkin(UUID uuid){
        Jedis jedis = RedisManager.getJedis();
        try {
            PlayerSkin playerSkin = PlayerSkin.fromUuid(uuid.toString());
            jedis.hset("skin-" + uuid, "textures", playerSkin.textures());
            jedis.hset("skin-" + uuid, "signature", playerSkin.signature());
            return playerSkin;
        } catch (JedisConnectionException e){
            e.printStackTrace();
        }
        return PlayerSkin.fromUsername("Waggo");
    }
}
