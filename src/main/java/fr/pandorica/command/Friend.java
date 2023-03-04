package fr.pandorica.command;

import fr.pandorica.friend.RequestFriend;
import fr.pandorica.gui.friend.AddFriend;
import fr.pandorica.redis.MessagePlayer.MessageBody;
import fr.pandorica.redis.MessagePlayer.MessageType;
import fr.pandorica.redis.RedisPlayerFriend;
import fr.pandorica.redis.RedisPlayerServer;
import fr.pandorica.redis.RedisSendStream;
import fr.pandorica.request.GetFriend;
import fr.pandorica.request.GetPlayer;
import fr.pandorica.request.PostFriend;
import fr.pandorica.utils.ParseComponent;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.listener.manager.PacketListenerConsumer;
import net.minestom.server.network.packet.client.play.ClientCommandChatPacket;

import java.util.Map;
import java.util.UUID;

public class Friend implements PacketListenerConsumer<ClientCommandChatPacket> {
    @Override
    public void accept(ClientCommandChatPacket packet, Player player) {
        String[] cmd = packet.message().split(" ");
        if (cmd[0].equalsIgnoreCase("f")) {

            if (cmd.length <= 1) {
                //displayHelp(proxiedPlayer);
                return;
            }

            RedisPlayerFriend redisPlayerFriend = new RedisPlayerFriend(player.getUuid());

            if (cmd.length == 2) {
                if (cmd[1].equalsIgnoreCase("accept")) {
                    // si il n'a pas de demande d'ami
                    if (!(redisPlayerFriend.haveRequestCurrently())) {
                        player.sendMessage("§cVous n'avez pas de demande d'ami");
                        return;
                    }

                    // si le player est deco
                    if (redisPlayerFriend.getKeyRequestFriend() == null) {
                        player.sendMessage("§cErreur lors de la création d'un ami");
                        return;
                    }

                    String receiver = redisPlayerFriend.getKeyRequestFriend();
                    if (new GetFriend(player.getUuid()).isFriendWith(UUID.fromString(receiver))) {
                        player.sendMessage("§cVous êtes déjà amis avec cette personne");
                        return;
                    }

                    new PostFriend(player.getUuid()).addFriend(UUID.fromString(receiver));
                    new PostFriend(UUID.fromString(receiver)).addFriend(player.getUuid());

                    player.sendMessage("§6Vous étes désormais amis avec §a" + new GetPlayer(UUID.fromString(redisPlayerFriend.getKeyRequestFriend())).getPseudo() + " §6.");

                    //RETURN MESSAGE SENDER
//                    Map<String, String> messageBody = new HashMap<>();
//                    messageBody.put("uuid", String.valueOf(redisPlayerFriend.getKeyRequestFriend()));
//                    messageBody.put("sender_uuid", player.getUuid().toString());
//                    messageBody.put("msg", "§6Vous étes désormais amis avec §a" + ParseComponent.getString(player.getDisplayName()));
                    Map<String, String> messageBody = MessageBody.getBody(
                            MessageType.SEND_INFO,
                            UUID.fromString(redisPlayerFriend.getKeyRequestFriend()),
                            player.getUuid(),
                            "§6Vous étes désormais amis avec §a" + ParseComponent.getString(player.getDisplayName()),
                            ""
                    );
                    new RedisSendStream(new RedisPlayerServer(UUID.fromString(redisPlayerFriend.getKeyRequestFriend())).getServerInKeyPlayer(), messageBody).sendMessage();


                    redisPlayerFriend.delKeyRequestFriend();

                } else if (cmd[1].equalsIgnoreCase("refuse")) {

                    // si il n'a pas de demande d'ami
                    if (!(redisPlayerFriend.haveRequestCurrently())) {
                        player.sendMessage("§cVous n'avez pas de demande d'ami");
                        return;
                    }

                    // si le player est deco
                    if (redisPlayerFriend.getKeyRequestFriend() == null) {
                        player.sendMessage("§cErreur lors de la création d'un ami");
                        return;
                    }


                    if (new GetFriend(player.getUuid()).isFriendWith(UUID.fromString(redisPlayerFriend.getKeyRequestFriend()))) {
                        player.sendMessage("§cVous ètes déjà amis avec cette personne");
                        return;
                    }

                    player.sendMessage("§6Vous avez refusé la demande d'amis de §a" + new GetPlayer(UUID.fromString(redisPlayerFriend.getKeyRequestFriend())).getPseudo() + " §6.");

                    //RETURN MESSAGE SENDER
                    Map<String, String> messageBody = MessageBody.getBody(
                            MessageType.SEND_INFO,
                            UUID.fromString(redisPlayerFriend.getKeyRequestFriend()),
                            player.getUuid(),
                            "§6Le joueur §a" + ParseComponent.getString(player.getDisplayName()) + " §6à refusé votre demande d'ami.",
                            ""
                    );

                    new RedisSendStream(new RedisPlayerServer(UUID.fromString(redisPlayerFriend.getKeyRequestFriend())).getServerInKeyPlayer(), messageBody).sendMessage();

                    redisPlayerFriend.delKeyRequestFriend();

                }
            } else if (cmd.length == 3){
                if (cmd[1].equalsIgnoreCase("add")) {
                    System.out.println(cmd.toString());
                    ItemStack it = new RequestFriend().check(player, cmd[2]);
                    if (it != null){
                        AddFriend.openInventory(player, cmd[2], it);
                    }
                }
            }
        }
    }
}
