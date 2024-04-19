package main.java;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

@WebSocket
public class WebSocketHandler{

    Random r = new Random();
    private org.eclipse.jetty.websocket.api.Session session;
    private String playerId;
    
    private Timer timer;
    
    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        if(!GameServer.partieCommence) {
            System.out.println("New connection: " + user.getRemoteAddress());
            this.session = user;
            this.playerId = user.getRemoteAddress().toString();
            GameServer.clients.put(playerId, this);
            // Créer un nouveau joueur et l'ajouter à la liste des joueurs
            int x, y;
            boolean collision;
            do {
                collision = false;

                x = r.nextInt(IConfig.LARGEUR_FENETRE / 2 + 590) - (int) (IConfig.LARGEUR_FENETRE / 2) + 30;
                y = r.nextInt(IConfig.LONGUEUR_FENETRE / 2 + 260) - (int) (IConfig.LONGUEUR_FENETRE / 2) + 110;

                for (Player otherPlayer : GameServer.players.values()){
                    int distanceX = x - otherPlayer.getX();
                    int distanceY = y - otherPlayer.getY();
                    double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
                    if (distance < (IConfig.RADIUS * 2 + 2)) {
                        collision = true;
                        break;
                    }
                }

            } while (collision);

            Player player = new Player(x, y, IConfig.SPEED);
            GameServer.players.put(playerId, player);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("playerNumber", player.getNumPlayer());
            try {
                session.getRemote().sendString(jsonObj.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //GameServer.gameFrame.actualiseJoueurs(GameServer.players);
            GameServer.game.setPlayers(GameServer.players);
        }
        // Gestion du timer
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        		// vérifier si le timer est en cours d'exécution
        		if(Chrono.isRunning()) {}
        	}
        }, 0, 20);
    }
    
    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Connection closed: " + user.getRemoteAddress() + " Code: " + statusCode + ", Reason: " + reason);
        GameServer.clients.remove(user.getRemoteAddress().toString());
    	GameServer.players.remove(user.getRemoteAddress().toString());
    	
    	// A la fermeture de la connexion, arréter le timer
    	if(timer != null) {
    		timer.cancel();
    	}
    }
    
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
    	// Handle incoming text messages
        //System.out.println("Received message: " + message);

        // Convertir la chaîne JSON en objet JSONObject
        JSONObject jsonObject = new JSONObject(message);

        // Extraire les valeurs d'angle et de distance avec les décimales
        double angle = jsonObject.getDouble("angle");
        double distance = jsonObject.getDouble("distance");
        
        //System.out.println("\n\n\nplayerID " + session.getRemoteAddress().toString() + "\n\n\n") ;

        // Utiliser les valeurs extraites pour déplacer le joueur
        GameServer.players.get(session.getRemoteAddress().toString()).move(angle, distance);

    }
    
    public org.eclipse.jetty.websocket.api.Session getSession(){
    	return session ; 
    }
    
}
