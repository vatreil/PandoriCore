package fr.pandorica.player.listeners;

import fr.pandorica.player.PlayerManager;
import fr.pandorica.rank.RankManager;
import fr.pandorica.request.GetPlayer;
import fr.pandorica.utils.ParseComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;


public class PlayerChat implements EventListener<PlayerChatEvent> {

    private static Instance instance;

    public PlayerChat(Instance instance){
        this.instance = instance;
    }
    @Override
    public @NotNull Class<PlayerChatEvent> eventType() {
        return PlayerChatEvent.class;
    }

    @Override
    public @NotNull Result run(@NotNull PlayerChatEvent event) {
        Player player = event.getPlayer();

        Component msg = Component.text(RankManager.powerToRank(PlayerManager.getPlayer(player.getUuid()).getRank()).getDisplayName() + ParseComponent.getString(player.getDisplayName()) + " §7>> §f" + event.getMessage())
                .clickEvent(Component.text().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/profil " + ParseComponent.getString(player.getDisplayName()))).build().clickEvent());

        for (Player ply : instance.getPlayers()) {
            ply.sendMessage(msg);
        }

        event.setCancelled(true);
        return Result.SUCCESS;
    }
}
