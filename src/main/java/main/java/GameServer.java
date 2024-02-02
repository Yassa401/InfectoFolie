package main.java;

import com.google.gson.Gson;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class GameServer {

    private static final Map<String, WebSocketHandler> clients = new HashMap<>();

    public static void main(String[] args) {
        Spark.staticFiles.location("/public");
        Spark.webSocket("/ws", WebSocketHandler.class);

        Spark.init();
        Spark.awaitInitialization();
    }

    public static void broadcast(String message) {
        for (WebSocketHandler client : clients.values()) {
            client.session.getRemote().sendStringByFuture(message);
        }
    }

    public static class WebSocketHandler implements org.eclipse.jetty.websocket.api.WebSocketListener {
        private org.eclipse.jetty.websocket.api.Session session;

        @Override
        public void onWebSocketConnect(org.eclipse.jetty.websocket.api.Session session) {
            this.session = session;
            clients.put(session.getRemoteAddress().toString(), this);
        }

        @Override
        public void onWebSocketClose(int statusCode, String reason) {
            clients.remove(session.getRemoteAddress().toString());
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

            // Utiliser les valeurs extraites
            System.out.println("Angle: " + angle);
            System.out.println("Distance: " + distance);
            
            broadcast(message);
        }
    }
}
