package fr.pandorica.info;

import fr.pandorica.redis.RedisPlayerSkin;
import fr.pandorica.utils.SendNotification;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.PlayerHeadMeta;

import java.util.Map;
import java.util.UUID;

public class InfoMessage {

    public void send(Map<String, String> body){
        try {
            if(body.get("uuid") != null & body.get("msg") != null) {
                UUID uuidPlayer = UUID.fromString(body.get("uuid"));
                Player player = (Player) Player.getEntity(uuidPlayer);

                player.sendMessage(body.get("msg"));

                SendNotification.sendGoal(player,
                        ItemStack.builder(Material.PLAYER_HEAD)
                                .meta(PlayerHeadMeta.class, meta -> meta.skullOwner(UUID.fromString(body.get("sender_uuid"))).playerSkin(RedisPlayerSkin.getSkin(UUID.fromString(body.get("sender_uuid")))))
                                .build(),
                        Component.text(body.get("msg"), NamedTextColor.YELLOW)
                );



            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
