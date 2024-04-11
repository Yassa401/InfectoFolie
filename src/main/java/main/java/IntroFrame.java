package main.java;
import javax.swing.*;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroFrame extends JFrame {
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
        
 /*       
     // Déclaration du JLabel comme attribut de la classe pour y accéder facilement
        private JLabel playerCountLabel;

        // Initialisation et configuration du JLabel dans le constructeur ou une méthode d'initialisation
        public IntroFrame() {
            ...
            playerCountLabel = new JLabel("Joueurs en ligne: 0");
            playerCountLabel.setBounds(20, IConfig.LONGUEUR_FENETRE - 70, 200, 50); // Ajustez selon votre layout
            this.add(playerCountLabel, BorderLayout.SOUTH);
            ...
        }
*/
        
        startButton.setBounds((IConfig.LARGEUR_FENETRE/2 - 150),(IConfig.LONGUEUR_FENETRE/2 - 50),300,100);
        
        // Adding components to the frame
        add(configPanel);
        add(startButton);
        add(playerCountLabel);
    }

    
}
