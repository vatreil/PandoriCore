package org.example;

import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerPostLogin implements EventListener<PlayerLoginEvent> {
    @Override
    public @NotNull Class<PlayerLoginEvent> eventType() {
        return PlayerLoginEvent.class;
    }

    @Override
    public @NotNull Result run(@NotNull PlayerLoginEvent event) {
        Player player = event.getPlayer();
        player.setUsernameField("§cSuceBite");
        return EventListener.Result.SUCCESS;
    }
}
