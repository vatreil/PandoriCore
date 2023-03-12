package fr.pandorica.rank;

import java.util.HashMap;
import java.util.Map;

public enum RankManager {
    PLAYER(0, "§a§cAdmin", "§7Joueur", "§7"),
    VIP(1, "§e§cAdmin", "§eVIP", "§e"),
    MODO(9, "§2§cAdmin", "§mModérateur", "§6"),
    ADMIN(10, "§1§cAdmin", "§cAdmin", "§c");

    private int power;
    private String name, orderRank, displayName;
    public static Map<Integer, RankManager> ranks = new HashMap<>();

    private RankManager(int power, String orderRank, String name, String displayName) {
        this.power = power;
        this.name = name;
        this.orderRank = orderRank;
        this.displayName = displayName;
    }

    static {
        for (RankManager rank : RankManager.values()) {
            ranks.put(rank.getPower(), rank);
        }
    }

    public String getName() {
        return name;
    }

    public static RankManager powerToRank(int power) {
        return ranks.get(power);
    }

    public int getPower() {
        return power;
    }

    public String getOrderRank() {
        return orderRank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Boolean isAdmin(Integer rank){
        return powerToRank(rank) == RankManager.ADMIN;
    }
}
