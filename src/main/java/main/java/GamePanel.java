package main.java;


import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;

// GamePanel.java
public class GamePanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Player> players;
    public static List<Obstacle> murs;

    public GamePanel(Map<String, Player> players) {
        this.setBackground(Color.BLACK);
        this.players = players;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // Force le rafraîchissement du JPanel
                repaint();
            }
        }, 0, 20);

        murs = new ArrayList<>();
        // Initialisation des murs
        murs.add(new Obstacle(300, 100, IConfig.widthObs, IConfig.heightObs)); // Exemple de mur
        // Ajoutez d'autres murs si nécessaire

    }


    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessinez les joueurs sur le panneau
        for (Player player : players.values()) {
            drawPlayer(g, player);
        }
        for (Obstacle mur : murs) {
            g.fillRect(mur.getX(), mur.getY(), mur.getWidth(), mur.getHeight());
        }
    }





    public void drawPlayer(Graphics g, Player player) {
        int x = player.getX();
        int y = player.getY();
        int radius = player.getRadius();
        

        g.setColor(player.getCouleur());
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
    
    void ajoutJoueur(Map<String, Player> players) {
    	this.players = players ;
    	
    }
}
