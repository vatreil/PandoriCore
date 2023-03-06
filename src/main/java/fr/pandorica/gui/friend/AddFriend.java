package fr.pandorica.gui.friend;

import fr.pandorica.friend.RequestFriend;
import fr.pandorica.utils.ParseComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.listener.manager.PacketListenerConsumer;
import net.minestom.server.network.packet.client.play.ClientNameItemPacket;

public class AddFriend implements PacketListenerConsumer<ClientNameItemPacket> {

    @Override
    public void accept(ClientNameItemPacket packet, Player player) {
        if (player.getOpenInventory().getInventoryType().equals(InventoryType.ANVIL)){
            player.getOpenInventory().setItemStack(2, ItemStack.builder(Material.PAPER)
                    .displayName(Component.text(packet.itemName(), NamedTextColor.WHITE)).build());
        }
    }



    public static void openInventory(Player player, String pseudo, ItemStack itinfo){

        Inventory invAnvil = new Inventory(InventoryType.ANVIL, "Click on the paper");

        if (itinfo == null){
            invAnvil.setItemStack(0,
                    ItemStack.builder(Material.PAPER)
                            .displayName(Component.text("PlayerName", NamedTextColor.GREEN)).build());
        } else {
            invAnvil.setItemStack(0,
                    ItemStack.builder(Material.PAPER)
                            .displayName(Component.text(pseudo, NamedTextColor.GREEN)).build());
            invAnvil.setItemStack(2, itinfo);
        }

        player.openInventory(invAnvil);

        invAnvil.addInventoryCondition((playerClick, slot, click, result) -> {
            result.setCancel(true);

            ItemStack itemStack = result.getClickedItem();
            String reply = ParseComponent.getString(itemStack.getDisplayName());

            if (itemStack.material().equals(Material.PAPER)) {
                ItemStack it = new RequestFriend().check(player, reply);
                if (it != null){
                    invAnvil.setItemStack(2, it);
                }
            }
        });

    }
}
