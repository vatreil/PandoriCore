package fr.pandorica.utils;

import net.kyori.adventure.text.Component;
import net.minestom.server.advancements.FrameType;
import net.minestom.server.advancements.notifications.Notification;
import net.minestom.server.advancements.notifications.NotificationCenter;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

public class SendNotification {

    public static void sendGoal(Player player, ItemStack itemStack, Component component){
        Notification notification = new Notification(
                component,
                FrameType.GOAL,
                itemStack
        );

        NotificationCenter.send(notification, player);
    }
}
