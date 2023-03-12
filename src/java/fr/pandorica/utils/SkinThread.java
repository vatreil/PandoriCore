package fr.pandorica.utils;

import fr.pandorica.redis.RedisPlayerSkin;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.metadata.PlayerHeadMeta;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SkinThread {

    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    public void getSkin(Inventory inv, Integer slot, ItemStack itemStack, UUID uuid){
        FORK_JOIN_POOL.execute(() -> {
            ItemStack it = itemStack.withMeta(PlayerHeadMeta.class, meta -> meta.skullOwner(uuid).playerSkin(RedisPlayerSkin.getSkin(uuid)));
            inv.setItemStack(slot, it);
        });
    }
}
