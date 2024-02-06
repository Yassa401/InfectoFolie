package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

// GamePanel.java
public class GamePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Player> players;

    public GamePanel(Map<String, Player> players) {
    	this.setBackground(Color.BLACK);
        this.players = players;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessinez les joueurs sur le panneau
        for (Player player : players.values()) {
            drawPlayer(g, player);
        }
    }

    private void drawPlayer(Graphics g, Player player) {
        int x = player.getX();
        int y = player.getY();
        int radius = player.getRadius();
        

        g.setColor(Color.BLUE);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
    
    void ajoutJoueur(Map<String, Player> players) {
    	this.players = players ;
    	
    }
}
