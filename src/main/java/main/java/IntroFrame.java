package main.java;
import javax.imageio.ImageIO;
import javax.swing.*;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IntroFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel nbPlayers;
	private BufferedImage backgroundImage;
	private Image image;
	public IntroFrame() {
        setTitle("Game Settings");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        

        
        JPanel Panel = new JPanel();
        Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
        Panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Panel.setBackground(new Color(70, 70, 70));
        
        
        JButton con = new JButton("Autoriser les connexions");
        con.setAlignmentX(Component.CENTER_ALIGNMENT);
        con.setBackground(new Color(26, 188, 156));
        con.setForeground(Color.WHITE);
        con.setBorderPainted(false);
        con.setFocusPainted(false);
        
        con.addActionListener(e -> {
           	GameServer.partieCommence = false;
        });

        
        
        JButton startButton = new JButton("Commencer");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.setBackground(new Color(26, 188, 156));
        startButton.setForeground(Color.WHITE);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
           	GameServer.gameFrame = new GameFrame(GameServer.game);
           	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
           	config.title = "InfectoFolie";
           	config.width = IConfig.LARGEUR_FENETRE;
           	config.height = IConfig.LONGUEUR_FENETRE;
           	new LwjglApplication(GameServer.gameFrame, config);
            
            setVisible(false);
        });

        JButton configButton = new JButton("Configuration");
        configButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        configButton.setBackground(new Color(26, 188, 156));
        configButton.setForeground(Color.WHITE);
        configButton.setBorderPainted(false);
        configButton.setFocusPainted(false);

        configButton.addActionListener(e -> new Configuration().setVisible(true));

        nbPlayers = new JLabel("Joueurs en ligne: **Chargement**");
        nbPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        nbPlayers.setForeground(new Color(236, 240, 241));
        nbPlayers.setFont(new Font("Arial", Font.BOLD, 18));

        Panel.add(Box.createVerticalGlue()); 
        Panel.add(startButton);
        Panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        Panel.add(configButton);
        Panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        Panel.add(con);
        Panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        
        
        Panel.add(nbPlayers);
        Panel.add(Box.createVerticalGlue()); 
        
        getContentPane().add(Panel, BorderLayout.CENTER);
        new Timer(5000, this::majPlayers).start();

        setVisible(true);
    }
    
    private void majPlayers(ActionEvent event) {
        int players = GameServer.players.size();
        nbPlayers.setText("Joueurs en ligne: " + players);
    }
    
    
}
