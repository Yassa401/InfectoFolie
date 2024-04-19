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
        getContentPane().setBackground(new Color(50, 50, 50));
        cred.setText("<html><font color='white'>Le joueur " + i + " a gagné !!!</font></html>");
        cred.setFont(new Font("Arial", Font.BOLD, 24));

        // Centrer le label horizontalement
        int labelWidth = cred.getPreferredSize().width;
        int labelHeight = cred.getPreferredSize().height;
        int labelX = (getWidth() - labelWidth) / 2;
        int labelY = 10; // Position en haut de la fenêtre

        cred.setBounds(labelX, labelY, labelWidth, labelHeight);

        JPanel bouton = new JPanel();
        bouton.setLayout(new FlowLayout(FlowLayout.CENTER)); 

        recommencer = new JButton("Recommencer");
        quitter = new JButton("Quitter");

        bouton.add(recommencer);
        bouton.add(quitter);

        // Ajoutez les boutons au contentPane
        getContentPane().setLayout(null); 
        getContentPane().add(cred);
        getContentPane().add(bouton);

        Color green = new Color(0, 128, 0); // RVB: (0, 128, 0)
        bouton.setBounds(0, getHeight() - 80, getWidth(), 50); 
        bouton.setBackground(Color.ORANGE);
        recommencer.setBackground(green);
        recommencer.setForeground(Color.WHITE);
        
        quitter.setBackground(Color.RED);
        quitter.setForeground(Color.WHITE);
        recommencer.setFont(new Font("Arial", Font.BOLD, 14)); 
        quitter.setFont(new Font("Arial", Font.BOLD, 14));
        quitter.addActionListener(new ActionListener() {
           
        public void actionPerformed(ActionEvent e) {
                System.exit(0); // Ferme l'application
            }
        });

        
        recommencer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {   
            	// Redéclenche la fenetre d'acceuil
                IntroFrame introFrame = new IntroFrame();
                introFrame.setVisible(true);
                // Les Joueurs peuvent se reconnecter à nouveau
                GameServer.partieCommence = false ;
                // Ferme la fenetre de victoire
                dispose();
            }
        });

        setVisible(true);
    }

}


