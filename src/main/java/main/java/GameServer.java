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
        Spark.staticFiles.location("/public");
        Spark.webSocket("/ws", WebSocketHandler.class);
        
        Spark.init();
        Spark.awaitInitialization();
        
       
        SwingUtilities.invokeLater(() -> {
            gameFrame = new GameFrame(players);
        });
        
    }

    public static void broadcast(String message) {
        for (WebSocketHandler client : clients.values()) {
            client.getSession().getRemote().sendStringByFuture(message);
        }
    }
}
