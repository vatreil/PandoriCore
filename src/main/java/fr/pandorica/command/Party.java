package fr.pandorica.command;

import fr.pandorica.redis.MessagePlayer.MessageBody;
import fr.pandorica.redis.MessagePlayer.MessageType;
import fr.pandorica.redis.RedisInfoPlayer;
import fr.pandorica.redis.RedisPlayerParty;
import fr.pandorica.redis.RedisPlayerServer;
import fr.pandorica.redis.RedisSendStream;
import fr.pandorica.request.GetFriend;
import fr.pandorica.utils.ParseComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.listener.manager.PacketListenerConsumer;
import net.minestom.server.network.packet.client.play.ClientCommandChatPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Party implements PacketListenerConsumer<ClientCommandChatPacket> {
    @Override
    public void accept(ClientCommandChatPacket packet, Player player) {
        String[] cmd = packet.message().split(" ");


        if (cmd[0].equalsIgnoreCase("p")) {
            RedisPlayerParty redisParty = new RedisPlayerParty(player.getUuid());
            if (cmd.length <= 1) {
                displayHelp(player);
                return;
            }

            if (cmd.length == 2) {
                if (cmd[0].equalsIgnoreCase("accept")) {

                    // si il n'a pas de demande de party
                    if (!(redisParty.haveRequestCurrently())) {
                        player.sendMessage("§cVous n'avez pas de demande de party");
                    }

                    // si le player est deco
                    if (redisParty.getKeyRequestParty() == null) {
                        player.sendMessage("§cErreur lors de la connexion à la party");
                    }

                    if (redisParty.hasProfilParty()) {
                        player.sendMessage("§cVous êtes déjà dans une party.");
                    }

                    String uuidLeader = redisParty.getKeyRequestParty();
                    redisParty.createProfilParty(uuidLeader);

                    player.sendMessage("§6Vous êtes désormais en party avec §a" + new RedisPlayerParty(UUID.fromString(uuidLeader)) + " §6.");

                    redisParty.delKeyRequestParty();

                } else if (cmd[1].equalsIgnoreCase("refuse")) {

                    // si il n'a pas de demande de party
                    if (!(redisParty.haveRequestCurrently())) {
                        player.sendMessage("§cVous n'avez pas de demande de party");
                    }

                    if (redisParty.hasProfilParty()) {
                        player.sendMessage("§cVous êtes déjà dans une party.");
                    }

                    player.sendMessage("§6Vous avez refusé la demande de party à §a" + new RedisPlayerServer(UUID.fromString(redisParty.getKeyRequestParty())) + " §6.");
                    redisParty.delKeyRequestParty();

                } else if (cmd[1].equalsIgnoreCase("add")) {
                    if (cmd.length < 3) {
                        displayHelp(player);
                    }
                } else if (cmd[1].equalsIgnoreCase("remove")) {
                    if (cmd.length < 3) {
                        displayHelp(player);
                    }

                    // joueurs cmd 2 = ok
                }
            }

            if (cmd.length == 3) {
                if (cmd[1].equalsIgnoreCase("add")) {
                    String targetName = cmd[2];
                    UUID uuidPlayer = new RedisInfoPlayer().getUUIDPlayer(targetName);
                    if (uuidPlayer != null) {

                        if (new GetFriend(player.getUuid()).isFriendWith(uuidPlayer)) {

                            if (player.getUuid() == uuidPlayer) {
                                player.sendMessage("§cVous ne pouvez pas vous ajouter en party.");
                            }

                            if (redisParty.hasProfilParty()) {
                                if (uuidPlayer != player.getUuid()) {
                                    player.sendMessage("§cVous êtes pas leader de votre party.");
                                }
                            }

                            if (redisParty.hasProfilParty()) {
                                player.sendMessage("§cLe joueur est déjà dans une party.");
                            }

                            //REDIS SET KEY "UUIDSENDER" WITH VALUE "UUIDRECEIVER"
                            redisParty.setKeyRequestParty(uuidPlayer);

                            //SEND MESSAGE RECEIVER
                            Map<String, String> messageBody = MessageBody.getBody(
                                    MessageType.SEND_PARTY,
                                    uuidPlayer,
                                    player.getUuid(),
                                    "§e" + ParseComponent.getString(player.getDisplayName()) + "§6 Vous a demandé en party.",
                                    ""
                            );
                            new RedisSendStream(new RedisPlayerServer(uuidPlayer).getServerInKeyPlayer(), messageBody).sendMessage();


                            player.sendMessage("§6Demande envoyé!§e");

                        } else {
                            // player n'accepte pas les demande d'amis
                            player.sendMessage("§cTu n'es pas amis avec cette personne.");
                        }
                    } else {
                        player.sendMessage("§cLe joueur est deconnecté.");
                    }
                } else if (cmd[1].equalsIgnoreCase("quit")) {

                    if (!redisParty.hasProfilParty()) {
                        player.sendMessage("§cVous avez pas de party.");
                    }

                    player.sendMessage("§eVous avez quitté la party !");
                    redisParty.delPlayerParty(UUID.fromString(redisParty.getLeader()));

                }
            }
        }
    }

    private void displayHelp(Player player) {
        player.sendMessage("");
        player.sendMessage(Component.text("Commands party :", NamedTextColor.GOLD));
        player.sendMessage(ParseComponent.getClickPreSetCmdWithDesc("/f add Username", "Ajouter un amis", "/f add"));
        player.sendMessage(ParseComponent.getClickPreSetCmdWithDesc("/f accept", "Accepter la demandes d'ami", "/f accept"));
        player.sendMessage(ParseComponent.getClickPreSetCmdWithDesc("/f refuse", "Refuser la demandes d'ami", "/f refuse"));
        player.sendMessage("");
    }
}
