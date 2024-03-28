package main.java;

import spark.Spark;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
public class GameServer {

    static final Map<String, WebSocketHandler> clients = new HashMap<>();
    static final Map<String, Player> players = new HashMap<>();
    
    static GameFrame gameFrame;
    static Game game;

    // Si true, empêche les joueurs de se connecter au jeu
    static boolean partieCommence = false ;

    public static void main(String[] args) {

        StdErrLog logger = new StdErrLog();
        logger.setDebugEnabled(false);
        Log.setLog(logger);
    	
    	// INITIALISATION DE L'API SPARK POUR GERER LES EVENEMENTS DES CLIENTS
        Spark.staticFiles.location("/public");
        Spark.webSocket("/ws", WebSocketHandler.class);
        Spark.init();
        Spark.awaitInitialization();
        
        //gameFrame = new GameFrame(players);
        game = new Game(players);
        gameFrame = new GameFrame(game);
        
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "InfectoFoli";
        config.width = IConfig.LARGEUR_FENETRE;
        config.height = IConfig.LONGUEUR_FENETRE;
        new LwjglApplication(gameFrame, config);
        
    }
}
