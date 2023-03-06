package fr.pandorica.party;

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

public class PartyMessage {

    public void send(Map<String, String> body){
        try {
            if(body.get("uuid") != null & body.get("msg") != null) {
                UUID uuidPlayer = UUID.fromString(body.get("uuid"));
                Player player = (Player) Player.getEntity(uuidPlayer);

                if(body.get("cmd") != null){

                    Component accept = Component.text("  Accepter", NamedTextColor.GREEN)
                            .hoverEvent(HoverEvent.showText(Component.text("Accepter", NamedTextColor.GREEN)))
                            .clickEvent(Component.text().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, body.get("cmd"))).build().clickEvent());

                    Component refuse = Component.text("  Refuser", NamedTextColor.RED)
                            .hoverEvent(HoverEvent.showText(Component.text("Refuser", NamedTextColor.RED)))
                            .clickEvent(Component.text().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/p refuse")).build().clickEvent());

                    Component msg = Component.text(body.get("msg")).append(accept).append(refuse);

                    player.sendMessage(msg);
                } else {
                    player.sendMessage(body.get("msg"));
                }

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
