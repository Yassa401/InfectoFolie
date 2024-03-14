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

public class GameServer {

    static final Map<String, WebSocketHandler> clients = new HashMap<>();
    static final Map<String, Player> players = new HashMap<>();
    
    static GameFrame gameFrame;
    static Game game;

    public static void main(String[] args) {
    	
    	// Récupérer le logger racine de Spark
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        // Configurer le niveau de journalisation à ERROR pour Spark
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        rootLogger.setLevel(Level.ERROR);
    	
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
