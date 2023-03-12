package org.example;

import net.minestom.server.event.EventListener;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.item.EntityEquipEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerInteract implements EventListener<EntityEquipEvent> {
    @Override
    public @NotNull Class<EntityEquipEvent> eventType() {
        return EntityEquipEvent.class;
    }

    @Override
    public @NotNull Result run(@NotNull EntityEquipEvent event) {
        System.out.println("fds");
        return EventListener.Result.SUCCESS;
    }
}
