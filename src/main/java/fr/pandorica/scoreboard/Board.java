package fr.pandorica.scoreboard;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;

import java.util.List;

public class Board {

    private List<String> lines;
    private String title;
    private Player player;
    public Board(Player player){
        this.player = player;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateLines(List<String> lines){
        this.lines = lines;
    }
    public void updateBoard(){
        Sidebar sidebar = new Sidebar(Component.text(title));
        int i = lines.size()+1;
        for(String line : lines){
            sidebar.createLine(new Sidebar.ScoreboardLine(i + "", Component.text(line), i));
            i--;
        }
        sidebar.addViewer(player);
    }
}
