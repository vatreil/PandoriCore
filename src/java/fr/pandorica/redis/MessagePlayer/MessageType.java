package fr.pandorica.redis.MessagePlayer;

import fr.pandorica.friend.FriendMessage;
import fr.pandorica.info.InfoMessage;
import fr.pandorica.rank.RankManager;
import fr.pandorica.redis.RedisInfoPlayer;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {

    SEND_FRIEND(0, new FriendMessage()),
    SEND_PARTY(1, new RedisInfoPlayer()),
    SEND_INFO(2, new InfoMessage());

    private int id;
    private Object object;
    public static Map<Integer, MessageType> ids = new HashMap<>();

    private MessageType(int id, Object object) {
        this.id = id;
        this.object = object;
    }

    static {
        for (MessageType type : MessageType.values()) {
            ids.put(type.getId(), type);
        }
    }

    public int getId() {
        return id;
    }

    public Object getObject() {
        return object;
    }



}
