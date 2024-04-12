package main.java;
import javax.swing.*;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroFrame extends JFrame {
	private JLabel playerCountLabel;
    public IntroFrame() {
        setTitle("Game Settings");
        setSize(IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton startButton = new JButton("Commencer");
        startButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		GameServer.gameFrame = new GameFrame(GameServer.game);
        		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        		config.title = "InfectoFoli";
        		config.width = IConfig.LARGEUR_FENETRE;
        		config.height = IConfig.LONGUEUR_FENETRE;
        		new LwjglApplication(GameServer.gameFrame, config);
        	        
        	}
        });
        
        if(GameFrame.players == null) {
        	

            playerCountLabel = new JLabel("Joueurs en ligne: 0" );
        }else {

        	playerCountLabel = new JLabel("Joueurs en ligne: " + GameFrame.players.size());
        }
        playerCountLabel.setBounds(IConfig.LARGEUR_FENETRE/2 - 150 , IConfig.LONGUEUR_FENETRE/2 - 50 + 150, 300, 100);
        add(playerCountLabel);
        
      
        
        
        JButton config = new JButton("Configuration");
        config.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Configuration c = new Configuration();
        		c.setVisible(true);
        	}
        });
        
        config.setBounds((IConfig.LARGEUR_FENETRE/2 - 150),(IConfig.LONGUEUR_FENETRE/2 +100),300,100);
            
        

        
        startButton.setBounds((IConfig.LARGEUR_FENETRE/2 - 150),(IConfig.LONGUEUR_FENETRE/2 - 50),300,100);
        add(config);
        add(startButton);
        add(playerCountLabel);
    }
    

    
}
