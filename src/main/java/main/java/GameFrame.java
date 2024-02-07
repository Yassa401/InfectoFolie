package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// GameFrame.java
public class GameFrame extends JFrame {

    private GamePanel gamePanel ;
    
    public GameFrame(Map<String, Player> players) {
        setTitle("Game Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE);
        
        // Créez le panneau de jeu avec la liste des joueurs
        gamePanel = new GamePanel(players);

        // Ajoutez le panneau de jeu au cadre
        getContentPane().add(gamePanel);
        
        // Affichez le cadre
        setVisible(true);
    }
    
    public GamePanel getGamePanel() {
    	return gamePanel ;
    }

}
