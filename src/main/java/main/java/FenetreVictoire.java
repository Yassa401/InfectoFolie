package main.java;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.* ;
public class FenetreVictoire extends JFrame {
	GameServer serv;
    JButton recommencer;
    JButton quitter;
    JLabel cred = new JLabel();

    FenetreVictoire(int i) {
        super("Félicitations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 100, 300, 150); // Augmentation de la largeur pour accueillir les boutons

        cred.setText("<html><font color='black'>Le joueur " + i + " a gagné !!!</font></html>");
        cred.setFont(new Font("Arial", Font.PLAIN, 24));

        // Centrer le label horizontalement
        int labelWidth = cred.getPreferredSize().width;
        int labelHeight = cred.getPreferredSize().height;
        int labelX = (getWidth() - labelWidth) / 2;
        int labelY = 10; // Position en haut de la fenêtre

        cred.setBounds(labelX, labelY, labelWidth, labelHeight);

        JPanel boutonsPanel = new JPanel();
        boutonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Alignement des boutons au centre

        recommencer = new JButton("Recommencer");
        quitter = new JButton("Quitter");

        boutonsPanel.add(recommencer);
        boutonsPanel.add(quitter);

        // Ajoutez les boutons au contentPane
        getContentPane().setLayout(null); // Layout null pour positionner manuellement les composants
        getContentPane().add(cred);
        getContentPane().add(boutonsPanel);

       
        boutonsPanel.setBounds(0, getHeight() - 80, getWidth(), 50); // Ajustez la position et la taille du panel des boutons
       
        recommencer.setBackground(Color.GREEN);
        recommencer.setForeground(Color.WHITE);
        
        quitter.setBackground(Color.RED);
        quitter.setForeground(Color.WHITE);
       
        quitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Ferme l'application
            }
        });

        
        recommencer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {   
            	 
                /*GameServer.gameFrame.dispose(); 
                
                IntroFrame introFrame = new IntroFrame();
                introFrame.setVisible(true);*/
            	
            	
                GameServer.partieCommence = false ;
                dispose();
            }
        });

        setVisible(true);
    }

}


