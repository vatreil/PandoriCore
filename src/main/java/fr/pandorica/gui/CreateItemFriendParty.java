package fr.pandorica.gui;


import fr.pandorica.redis.RedisPlayerParty;
import fr.pandorica.redis.RedisPlayerSkin;
import fr.pandorica.request.GetFriend;
import fr.pandorica.request.GetPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.PlayerHeadMeta;

import java.util.UUID;

public class CreateItemFriendParty {


    public void openGameMenu(Player player, String playerProfil, Boolean modo){

        Inventory inv = new Inventory((modo == true)? InventoryType.CHEST_3_ROW : InventoryType.CHEST_1_ROW, playerProfil);
        //inv.setItem(1, main.getItem(Material.DIAMOND_BLOCK, ChatColor.YELLOW + "PARTY!"));

        GetPlayer getPlayer = new GetPlayer(player.getUuid());
        UUID uuidProfil = getPlayer.getPlayerUUID(playerProfil);

        if(new GetFriend(player.getUuid()).isFriendWith(uuidProfil)){
            inv.setItemStack(2,
                    ItemStack.builder(Material.REDSTONE_BLOCK)
                            .displayName(Component.text("Remove friend!", NamedTextColor.RED)).build());
        } else {
            inv.setItemStack(2,
                    ItemStack.builder(Material.EMERALD_BLOCK)
                            .displayName(Component.text("Add friend!", NamedTextColor.GREEN)).build());
        }

        if(new RedisPlayerParty(uuidProfil).hasProfilParty()){
            if(new RedisPlayerParty(uuidProfil).getLeader() == player.getUuid().toString()){
                inv.setItemStack(6,
                        ItemStack.builder(Material.REDSTONE_BLOCK)
                                .displayName(Component.text("Kick Party", NamedTextColor.RED)).build());
            } else {
                inv.setItemStack(6,
                        ItemStack.builder(Material.DIAMOND_BLOCK)
                                .displayName(Component.text("Player have party!", NamedTextColor.YELLOW)).build());
            }
        } else {
            inv.setItemStack(6,
                    ItemStack.builder(Material.EMERALD_BLOCK)
                            .displayName(Component.text("Invite Party", NamedTextColor.GREEN)).build());
        }

        inv.setItemStack(4,
                ItemStack.builder(Material.PLAYER_HEAD)
                        .displayName(Component.text("Invite Party", NamedTextColor.GREEN))
                        .meta(PlayerHeadMeta.class, meta -> meta.skullOwner(player.getUuid()).playerSkin(RedisPlayerSkin.getSkin(player.getUuid())))
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

        player.openInventory(inv);

    }
}
