package fr.pandorica.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public class ParseComponent {

    public static String getString(Component component){
        return ((TextComponent) component).content();
    }

    public static Component getClickRunWithDesc(String msg, String desc, String cmd){
        return Component.text(msg, NamedTextColor.GREEN)
                .hoverEvent(HoverEvent.showText(Component.text(desc, NamedTextColor.GREEN)))
                .clickEvent(Component.text().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, cmd)).build().clickEvent());
    }

    public static Component getClickPreSetCmdWithDesc(String msg, String desc, String cmd){
        return Component.text(msg, NamedTextColor.GREEN)
                .hoverEvent(HoverEvent.showText(Component.text(desc, NamedTextColor.GREEN)))
                .clickEvent(Component.text().clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, cmd)).build().clickEvent());
    }
}
