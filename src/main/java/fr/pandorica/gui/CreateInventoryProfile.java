package fr.pandorica.gui;


import fr.pandorica.rank.RankManager;
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

public class CreateInventoryProfile {


    public void openGameMenu(Player playerOpen, Player playerProfil){

        GetPlayer getPlayer = new GetPlayer(playerOpen.getUuid());
        Boolean modo = RankManager.isAdmin(getPlayer.getRank());
        Inventory inv = new Inventory((modo)? InventoryType.CHEST_3_ROW : InventoryType.CHEST_1_ROW, playerProfil.getDisplayName());

        inv.addInventoryCondition((playerClick, slot, click, result) -> {
            if (slot == 2){
                System.out.println("test slot 2 ");
            }
        });
        //inv.setItem(1, main.getItem(Material.DIAMOND_BLOCK, ChatColor.YELLOW + "PARTY!"));

        UUID uuidProfil = playerProfil.getUuid();

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
        } else {
            inv.setItemStack(6,
                    ItemStack.builder(Material.EMERALD_BLOCK)
                            .displayName(Component.text("Invite Party", NamedTextColor.GREEN)).build());
        }

        inv.setItemStack(4,
                ItemStack.builder(Material.PLAYER_HEAD)
                        .displayName(Component.text("Invite Party", NamedTextColor.GREEN))
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
