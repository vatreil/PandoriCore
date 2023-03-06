package fr.pandorica.gui;


import fr.pandorica.friend.RequestFriend;
import fr.pandorica.gui.friend.AddFriend;
import fr.pandorica.rank.RankManager;
import fr.pandorica.redis.MessagePlayer.MessageBody;
import fr.pandorica.redis.MessagePlayer.MessageType;
import fr.pandorica.redis.RedisPlayerParty;
import fr.pandorica.redis.RedisPlayerServer;
import fr.pandorica.redis.RedisPlayerSkin;
import fr.pandorica.redis.RedisSendStream;
import fr.pandorica.request.GetFriend;
import fr.pandorica.request.GetPlayer;
import fr.pandorica.request.PostFriend;
import fr.pandorica.utils.ParseComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.PlayerHeadMeta;

import java.util.Map;
import java.util.UUID;

public class CreateInventoryProfile {


    public void openGameMenu(Player playerOpen, UUID uuidProfil){

        GetPlayer getPlayer = new GetPlayer(playerOpen.getUuid());
        Boolean modo = RankManager.isAdmin(getPlayer.getRank());
        Inventory inv = new Inventory((modo)? InventoryType.CHEST_3_ROW : InventoryType.CHEST_1_ROW, new GetPlayer(uuidProfil).getPseudo());

        inv.addInventoryCondition((playerClick, slot, click, result) -> {
            switch (slot){
                case 2:
                    if (result.getClickedItem().material().equals(Material.EMERALD_BLOCK)){
                        String pseudo = new GetPlayer(uuidProfil).getPseudo();
                        ItemStack itemStack = new RequestFriend().check(playerClick, pseudo);
                        if (itemStack != null){
                            playerClick.closeInventory();
                            AddFriend.openInventory(playerClick, pseudo, itemStack);
                        }
                    } else if (result.getClickedItem().material().equals(Material.REDSTONE_BLOCK)){
                        new PostFriend(playerClick.getUuid()).removeFriend(uuidProfil);
                    }
                    break;
                case 6:
                    if (result.getClickedItem().material().equals(Material.EMERALD_BLOCK)){
                        Map<String, String> messageBody = MessageBody.getBody(
                                MessageType.SEND_PARTY,
                                uuidProfil,
                                playerClick.getUuid(),
                                "§e"+ ParseComponent.getString(playerClick.getDisplayName())  + "§6 Vous invite à sa party.",
                                "/p accept"
                        );
                        new RedisSendStream(new RedisPlayerServer(uuidProfil).getServerInKeyPlayer(), messageBody).sendMessage();
                        new RedisPlayerParty(playerClick.getUuid()).setKeyRequestParty(uuidProfil);
                    } else if (result.getClickedItem().material().equals(Material.REDSTONE_BLOCK)){

                    }
            }
        });
        //inv.setItem(1, main.getItem(Material.DIAMOND_BLOCK, ChatColor.YELLOW + "PARTY!"));

        if(new GetFriend(playerOpen.getUuid()).isFriendWith(uuidProfil)){
            inv.setItemStack(2,
                    ItemStack.builder(Material.REDSTONE_BLOCK)
                            .displayName(Component.text("Remove friend!", NamedTextColor.RED)).build());
        } else {
            inv.setItemStack(2,
                    ItemStack.builder(Material.EMERALD_BLOCK)
                            .displayName(Component.text("Add friend!", NamedTextColor.GREEN)).build());
        }

        if(new RedisPlayerParty(uuidProfil).hasProfilParty()){
            if(new RedisPlayerParty(uuidProfil).getLeader() == playerOpen.getUuid().toString()){
                inv.setItemStack(6,
                        ItemStack.builder(Material.REDSTONE_BLOCK)
                                .displayName(Component.text("Kick Party", NamedTextColor.RED)).build());
            } else {
                inv.setItemStack(6,
                        ItemStack.builder(Material.DIAMOND_BLOCK)
                                .displayName(Component.text("Player have party!", NamedTextColor.YELLOW)).build());
            }
        } else if (new RedisPlayerParty(playerOpen.getUuid()).hisLeaderParty()){
            inv.setItemStack(6,
                    ItemStack.builder(Material.EMERALD_BLOCK)
                            .displayName(Component.text("Invite Party", NamedTextColor.GREEN)).build());
        } else {
            inv.setItemStack(6,
                    ItemStack.builder(Material.BARRIER)
                            .displayName(Component.text("Tu n'es pas leader de ta partie", NamedTextColor.YELLOW)).build());
        }

        inv.setItemStack(4,
                ItemStack.builder(Material.PLAYER_HEAD)
                        .displayName(Component.text(new GetPlayer(uuidProfil).getPseudo()))
                        .meta(PlayerHeadMeta.class, meta -> meta.skullOwner(playerOpen.getUuid()).playerSkin(RedisPlayerSkin.getSkin(playerOpen.getUuid())))
                        .build());


        if(modo){
            inv.setItemStack(20,
                    ItemStack.builder(Material.DIAMOND_SWORD)
                            .displayName(Component.text("BAN", NamedTextColor.RED)).build());

            inv.setItemStack(22,
                    ItemStack.builder(Material.STONE_SWORD)
                            .displayName(Component.text("MUTE", NamedTextColor.GOLD)).build());

            inv.setItemStack(24,
                    ItemStack.builder(Material.WOODEN_SWORD)
                            .displayName(Component.text("WARN", NamedTextColor.YELLOW)).build());
        }

        playerOpen.openInventory(inv);

    }
}
