package fr.pandorica.utils;

import fr.pandorica.redis.RedisPlayerSkin;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.metadata.PlayerHeadMeta;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SkinThread {

    public void getSkin(Inventory inv, Integer slot, ItemStack itemStack, UUID uuid){
        final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                ItemStack it = itemStack.withMeta(PlayerHeadMeta.class, meta -> meta.skullOwner(uuid).playerSkin(RedisPlayerSkin.getSkin(uuid)));
                inv.setItemStack(slot, it);
            }
        }, 0, TimeUnit.MICROSECONDS);

    }
}
