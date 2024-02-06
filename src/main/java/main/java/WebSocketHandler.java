package main.java;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

@WebSocket
public class WebSocketHandler{
    private org.eclipse.jetty.websocket.api.Session session;
    private String playerId;

    /*
    @Override
    public void onWebSocketConnect(org.eclipse.jetty.websocket.api.Session session) {
        this.session = session;
        this.playerId = session.getRemoteAddress().toString();
        GameServer.clients.put(playerId, this);
        // Créer un nouveau joueur et l'ajouter à la liste des joueurs
        Player player = new Player(100, 100, IConfig.SPEED);
        GameServer.players.put(playerId, player);
        
        GameServer.gameFrame.getGamePanel().ajoutJoueur(GameServer.players);
        GameServer.gameFrame.getGamePanel().repaint();
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
    	GameServer.clients.remove(playerId);
    	GameServer.players.remove(playerId);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        cause.printStackTrace();
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
        GameServer.players.get(playerId).move(angle, distance);

        // Mettez à jour le panneau de jeu pour refléter les nouveaux emplacements
        GameServer.gameFrame.getGamePanel().repaint();
    }
    */
    
    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("New connection: " + user.getRemoteAddress());
        this.session = user;
        this.playerId = user.getRemoteAddress().toString();
        GameServer.clients.put(playerId, this);
        // Créer un nouveau joueur et l'ajouter à la liste des joueurs
        Player player = new Player(100, 100, IConfig.SPEED);
        GameServer.players.put(playerId, player);
        
        GameServer.gameFrame.getGamePanel().ajoutJoueur(GameServer.players);
        GameServer.gameFrame.getGamePanel().repaint();
    }
    
    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Connection closed: " + user.getRemoteAddress() + " Code: " + statusCode + ", Reason: " + reason);
        GameServer.clients.remove(user.getRemoteAddress().toString());
    	GameServer.players.remove(user.getRemoteAddress().toString());
    }
    
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
    	// Handle incoming text messages
        System.out.println("Received message: " + message);

        // Convertir la chaîne JSON en objet JSONObject
        JSONObject jsonObject = new JSONObject(message);

        // Extraire les valeurs d'angle et de distance avec les décimales
        double angle = jsonObject.getDouble("angle");
        double distance = jsonObject.getDouble("distance");
        
        System.out.println("\n\n\nplayerID " + session.getRemoteAddress().toString() + "\n\n\n") ;

        // Utiliser les valeurs extraites pour déplacer le joueur
        GameServer.players.get(session.getRemoteAddress().toString()).move(angle, distance);

        // Mettez à jour le panneau de jeu pour refléter les nouveaux emplacements
        GameServer.gameFrame.getGamePanel().repaint();
    }
    
    public org.eclipse.jetty.websocket.api.Session getSession(){
    	return session ; 
    }
    
}
