package main.java;

import javax.swing.*;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Configuration extends JFrame {

    public Configuration() {
        setTitle("Settings");
        setSize(IConfig.LARGEUR_FENETRE - 300, IConfig.LONGUEUR_FENETRE - 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2)); // GridLayout pour organiser les sliders et labels

         addSlider("Width Obs", IConfig.widthObs, 50, 150);
        addSlider("Height Obs", IConfig.heightObs, 5, 20);
        addSlider("Cooldown", IConfig.cooldown, 1, 10);
        addSlider("Time", IConfig.time, 10000, 30000);
        addSlider("Pause Time", IConfig.pauseTime, 1000, 10000);
        
        JButton exit = new JButton("Terminer");
        exit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setVisible(false);
        	}
        });
        
        add(exit, BorderLayout.SOUTH);
        
        
    }

    // Méthode pour ajouter un slider et son label
    private void addSlider(String name, int initialValue, int min, int max) {
        JLabel label = new JLabel(name + ": " + initialValue);
        JSlider slider = new JSlider(min, max, initialValue);
        
        slider.addChangeListener(e -> {
            int value = ((JSlider) e.getSource()).getValue();
            label.setText(name + ": " + value);
            
            switch (name) {
                case "Width Obs":
                	IConfig.setWidthObs(value);
                    break;
                case "Height Obs":
                	IConfig.setHeightObs(value);
                    break;
                case "Cooldown":
                	IConfig.setCooldown(value);
                    break;
                case "Time":
                	IConfig.setTime(value);
                    break;
                case "Pause Time":
                    IConfig.setPauseTime(value);
                    break;
                default:
                    break;
            }
        });

        add(label);
        add(slider);
    }


    
}
