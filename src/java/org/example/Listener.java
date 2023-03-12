package org.example;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

public class Listener implements EventListener<PlayerBlockBreakEvent> {

    @Override
    public @NotNull Class eventType() {
        return PlayerBlockBreakEvent.class;
    }

    @Override
    public @NotNull EventListener.Result run(@NotNull PlayerBlockBreakEvent event) {
        Player player = event.getPlayer();
        player.setCustomNameVisible(true);
        player.setUsernameField("§cSuceBite");
        PlayerInventory inventory = player.getInventory();
        inventory.setItemStack(1, ItemStack.of(Material.BEACON));
        inventory.update();

        System.out.println("Breack");
        return EventListener.Result.SUCCESS;
    }
}
