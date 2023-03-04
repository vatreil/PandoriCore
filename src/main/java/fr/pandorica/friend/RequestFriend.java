package fr.pandorica.friend;

import fr.pandorica.redis.MessagePlayer.MessageBody;
import fr.pandorica.redis.MessagePlayer.MessageType;
import fr.pandorica.redis.*;
import fr.pandorica.request.GetFriend;
import fr.pandorica.utils.ParseComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.Map;
import java.util.UUID;

public class RequestFriend {

    private static ItemStack getBarrier(Component name){
        return ItemStack.builder(Material.BARRIER)
                .displayName(name).build();

    }

    public ItemStack check(Player player, String reply){
        GetFriend getFriend = new GetFriend(player.getUuid());
        if (reply.length() <= 16) {
            if (!ParseComponent.getString(player.getDisplayName()).equalsIgnoreCase(reply)){
                if(new RedisServer().playerIsConnect(reply)){
                    UUID uuid = new RedisInfoPlayer().getUUIDPlayer(reply);
                    if(!(getFriend.isFriendWith(uuid))) {
                        if(getFriend.isAllow()){
                            Map<String, String> messageBody = MessageBody.getBody(
                                    MessageType.SEND_FRIEND,
                                    uuid,
                                    player.getUuid(),
                                    "§e"+ ParseComponent.getString(player.getDisplayName())  + "§6 Vous a demandé en amis.",
                                    "/f accept"
                            );
                            new RedisSendStream(new RedisPlayerServer(uuid).getServerInKeyPlayer(), messageBody).sendMessage();
                            new RedisPlayerFriend(player.getUuid()).setKeyRequestFriend(uuid);

                            player.sendMessage("§6Demande envoyé à §e" + ParseComponent.getString(player.getDisplayName()));


                            player.closeInventory();
                            return null;
                        } else {
                            return getBarrier(Component.text(reply + " n'accepte pas les demandes d'amis", NamedTextColor.RED));
                        }
                    } else {
                        return getBarrier(Component.text("Tu es déjà ami avec " + reply, NamedTextColor.GREEN));
                    }
                } else {
                    return getBarrier(Component.text(reply + " est hors ligne!", NamedTextColor.RED));
                }
            } else {
                return getBarrier(Component.text("Tu ne peux pas t'ajouter en amis.", NamedTextColor.YELLOW));
            }
        } else {
            return getBarrier(Component.text("16 caractères maximum.", NamedTextColor.YELLOW));
        }
    }
}
