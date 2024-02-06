package main.java;

import com.google.gson.Gson;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.json.JSONObject;

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

    public static int getPlayerX(String playerId) {
        return players.get(playerId).getX();
    }

    public static int getPlayerY(String playerId) {
        return players.get(playerId).getY();
    }

}
