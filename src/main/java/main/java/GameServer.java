package main.java;

import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

public class GameServer {

    static final Map<String, WebSocketHandler> clients = new HashMap<>();
    static final Map<String, Player> players = new HashMap<>();
    
    static GameFrame gameFrame;

    public static void main(String[] args) {
    	// INITIALISATION DE L'API SPARK POUR GERER LES EVENEMENTS DES CLIENTS
        Spark.staticFiles.location("/public");
        Spark.webSocket("/ws", WebSocketHandler.class);
        Spark.init();
        Spark.awaitInitialization();
        
       
        // LANCEMENT DE LA FENETRE DE JEU
        SwingUtilities.invokeLater(() -> {
            gameFrame = new GameFrame(players);
        });
        
    }
}
