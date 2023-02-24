package fr.pandorica.teams;

import net.minestom.server.color.DyeColor;

public enum TeamColor {
    RED(DyeColor.RED, "§c"),
    BLUE(DyeColor.BLUE, "§9"),
    GREEN(DyeColor.GREEN, "§a"),
    YELLOW(DyeColor.YELLOW, "§e");

    private DyeColor color_wool;
    private String color_team;


    private TeamColor(DyeColor color_wool, String color_team) {
        this.color_wool = color_wool;
        this.color_team = color_team;
    }

    public DyeColor getWoolColor(){
        return this.color_wool;
    }
    public String getTeamColor(){
        return this.color_team;
    }
}
