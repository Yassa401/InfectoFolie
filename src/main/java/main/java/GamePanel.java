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
        murs.add(new Obstacle(0, 0, IConfig.LARGEUR_FENETRE, 10)); // Mur supérieur
        murs.add(new Obstacle(0, 0, 10, IConfig.LONGUEUR_FENETRE)); // Mur gauche
        murs.add(new Obstacle(0, IConfig.LONGUEUR_FENETRE - 10, IConfig.LARGEUR_FENETRE, 10)); // Mur inférieur
        murs.add(new Obstacle(IConfig.LARGEUR_FENETRE - 10, 0, 10, IConfig.LONGUEUR_FENETRE)); // Mur droit
        murs.add(new Obstacle(100, 200, 10, 100));

        // Petit murs sur la carte
        murs.add(new Obstacle(300, 50, 150, 10));
        murs.add(new Obstacle(500, 250, 10, 150));
        murs.add(new Obstacle(200, 400, 100, 10));
        murs.add(new Obstacle(600, 100, 10, 100));
    }


    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessinez les joueurs sur le panneau
        for (Player player : players.values()) {
            drawPlayer(g, player);
        }
        for (Obstacle mur : murs) {
            g.setColor(Color.GRAY);
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
