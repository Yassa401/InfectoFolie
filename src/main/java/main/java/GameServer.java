package main.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
public class GameServer {

    static final Map<String, WebSocketHandler> clients = new HashMap<>();
    static final Map<String, Player> players = new HashMap<>();
    
    static IntroFrame lobby;
    static GameFrame gameFrame;
    static Game game;

    // Si true, empêche les joueurs de se connecter au jeu
    static boolean partieCommence = false ;

    public static void main(String[] args) {

        // Desactive les messages du log
        StdErrLog logger = new StdErrLog();
        logger.setDebugEnabled(false);
        Log.setLog(logger);
    	
    	// INITIALISATION DE L'API SPARK POUR GERER LES EVENEMENTS DES CLIENTS
        Spark.staticFiles.location("/public");
        Spark.webSocket("/ws", WebSocketHandler.class);
        Spark.init();
        Spark.awaitInitialization();
        
        game = new Game(players);
        lobby = new IntroFrame();
        lobby.setVisible(true);
       
    }
    
    

}
