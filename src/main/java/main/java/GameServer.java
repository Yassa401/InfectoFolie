package main.java;

import com.google.gson.Gson;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.json.JSONObject;

public class GameServer {

    private static final Map<String, WebSocketHandler> clients = new HashMap<>();
    private static final Map<String, Player> players = new HashMap<>();
    
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
            client.session.getRemote().sendStringByFuture(message);
        }
    }

    public static int getPlayerX(String playerId) {
        return players.get(playerId).getX();
    }

    public static int getPlayerY(String playerId) {
        return players.get(playerId).getY();
    }

    public static class WebSocketHandler implements org.eclipse.jetty.websocket.api.WebSocketListener {
        private org.eclipse.jetty.websocket.api.Session session;
        private String playerId;

        @Override
        public void onWebSocketConnect(org.eclipse.jetty.websocket.api.Session session) {
            this.session = session;
            this.playerId = session.getRemoteAddress().toString();
            clients.put(playerId, this);
            
            // Créer un nouveau joueur et l'ajouter à la liste des joueurs
            Player player = new Player(100, 100, IConfig.SPEED);
            players.put(playerId, player);
            
            gameFrame.repaintGamePanel();
        }

        @Override
        public void onWebSocketClose(int statusCode, String reason) {
            clients.remove(playerId);
            players.remove(playerId);
        }

        @Override
        public void onWebSocketError(Throwable cause) {
            cause.printStackTrace();
        }

        @Override
        public void onWebSocketBinary(byte[] payload, int offset, int len) {
            // Ignore binary messages
        }

        @Override
        public void onWebSocketText(String message) {
            // Handle incoming text messages
            System.out.println("Received message: " + message);

            // Convertir la chaîne JSON en objet JSONObject
            JSONObject jsonObject = new JSONObject(message);

            // Extraire les valeurs d'angle et de distance avec les décimales
            double angle = jsonObject.getDouble("angle");
            double distance = jsonObject.getDouble("distance");
            
            System.out.println("\n\n\nplayerID " + playerId + "\n\n\n") ;

            // Utiliser les valeurs extraites pour déplacer le joueur
            players.get(playerId).move(angle, distance);

            // Informer les clients du mouvement
            broadcast(message);
            
         // Mettez à jour le panneau de jeu pour refléter les nouveaux emplacements
            gameFrame.repaintGamePanel();
        }
    }
}
