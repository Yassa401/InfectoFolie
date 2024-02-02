package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// GameFrame.java
public class GameFrame extends JFrame {

    private Map<String, Player> players = new HashMap<>();
    private GamePanel gamePanel ;
    
    public GameFrame(Map<String, Player> players) {
        setTitle("Game Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE);
        
        // Créez le panneau de jeu avec la liste des joueurs
        gamePanel = new GamePanel(players);

        // Ajoutez le panneau de jeu au cadre
        add(gamePanel);
        
        repaintGamePanel();

        // Affichez le cadre
        setVisible(true);
    }
    
    
    public void repaintGamePanel() {
    	gamePanel.repaint();
    }
    
    public GamePanel getGamePanel() {
    	return gamePanel ;
    }

    /*
    public static void main(String[] args) {
    	Player player = new Player(100, 100, 5);
    	Map<String, Player> players = new HashMap<String, Player>();
    	players.put("souhail", player);
    	player = new Player(150,150,5);
    	players.put("yasser", player);
    	
    	
    	SwingUtilities.invokeLater(() -> {
            new GameFrame(players);
        });
    }
    */
    
}
