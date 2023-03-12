package fr.pandorica.gui.friend;

import fr.pandorica.gui.CreateInventoryProfile;
import fr.pandorica.rank.RankManager;
import fr.pandorica.request.GetFriend;
import fr.pandorica.request.GetPlayer;
import fr.pandorica.utils.SkinThread;
import fr.pandorica.utils.Timer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.PlayerHeadMeta;

import java.util.UUID;

public class PlayerFriend {

    private String addf = "Add friend";
    private String remf = "Remove friend";
    private String namegui = "Friend";

    public void openInventory(Player player){

        Inventory inv = new Inventory(InventoryType.CHEST_6_ROW, namegui);

        inv.setItemStack(3,
                ItemStack.builder(Material.EMERALD_BLOCK)
                        .displayName(Component.text(addf, NamedTextColor.GREEN)).build());

        inv.setItemStack(5,
                ItemStack.builder(Material.REDSTONE_BLOCK)
                        .displayName(Component.text(remf, NamedTextColor.RED)).build());

        for (int i = 9; i < 18; i++){
            inv.setItemStack(i,
                    ItemStack.builder(Material.WHITE_STAINED_GLASS)
                            .displayName(Component.text("--------", NamedTextColor.WHITE)).build());
        }

        int slot_inv = 18;
        for (String uuid : new GetFriend(player.getUuid()).getFriendsList()){
            if (!uuid.equals("[]")) {
                if(slot_inv >= 35)break;
                GetPlayer getPlayer = new GetPlayer(UUID.fromString(uuid));
                ItemStack itemStack = ItemStack.builder(Material.PLAYER_HEAD)
                        .displayName(Component.text(RankManager.powerToRank(getPlayer.getRank()).getDisplayName() + getPlayer.getPseudo()))
                        .lore(Component.text(Timer.getTimerToString(getPlayer.getTemps())),
                                Component.text("§7Zone :§r"))
                        .build();

                inv.setItemStack(slot_inv,itemStack);
                new SkinThread().getSkin(inv, slot_inv, itemStack, UUID.fromString(uuid));
                slot_inv++;
            }
        }

        inv.addInventoryCondition((playerClick, slot, click, result) -> {
            switch (slot){
                case 3:
                    player.closeInventory();
                    AddFriend.openInventory(playerClick, null, null);

                default:
                    result.setCancel(true);
            }
            if (result.getClickedItem().material().equals(Material.PLAYER_HEAD)){
                player.closeInventory();
                PlayerHeadMeta playerHeadMeta = result.getClickedItem().meta(PlayerHeadMeta.class);
                new CreateInventoryProfile().openGameMenu(playerClick, playerHeadMeta.getSkullOwner(), inv);
            }
        });

        player.openInventory(inv);

    }

}
