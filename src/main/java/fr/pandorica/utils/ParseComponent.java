package fr.pandorica.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ParseComponent {

    public static String getString(Component component){
        return ((TextComponent) component).content();
    }
}
