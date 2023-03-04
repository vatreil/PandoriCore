package fr.pandorica.redis.MessagePlayer;

import fr.pandorica.utils.ParseComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageBody {

    public static Map<String, String> getBody(MessageType messageType, UUID uuid_receiver,UUID uuid_sender, String msg, String cmd){
        Map<String, String> messageBody = new HashMap<>();
        switch (messageType){
            case SEND_FRIEND:
                messageBody.put("type", MessageType.SEND_PARTY.getId()+"");
                messageBody.put("uuid", uuid_receiver.toString());
                messageBody.put("sender_uuid", uuid_sender.toString());
                messageBody.put("cmd", uuid_sender.toString());
                messageBody.put("msg", msg);
        }
        return messageBody;
    }
}
