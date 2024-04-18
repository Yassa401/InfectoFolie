package main.java;


import javax.swing.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.awt.*;

public class IntroFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel nbPlayers;
	public IntroFrame() {
        setTitle("Game Settings");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel Panel = new JPanel();
        Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
        Panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Panel.setBackground(new Color(70, 70, 70));


        
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
               GameServer.partieCommence = false;
            setVisible(false);
        });

        JButton configButton = new JButton("Configuration");
        configButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        configButton.setBackground(new Color(26, 188, 156));
        configButton.setForeground(Color.WHITE);
        configButton.setBorderPainted(false);
        configButton.setFocusPainted(false);

        configButton.addActionListener(e -> new Configuration().setVisible(true));

        Panel.add(Box.createVerticalGlue()); 
        Panel.add(startButton);
        Panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        Panel.add(configButton);
        Panel.add(Box.createRigidArea(new Dimension(0, 10)));
        

        Panel.add(Box.createVerticalGlue()); 
        
        getContentPane().add(Panel, BorderLayout.CENTER);
        setVisible(true);
    }

    
    
}
