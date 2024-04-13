package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Configuration extends JFrame {

    public Configuration() {
        setTitle("Settings");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1)); 
        addSlider("Radius", IConfig.RADIUS, 0, 20);
        addSlider("Speed", (int)(IConfig.SPEED * 100), 0, 50);
        addSlider("Cooldown", IConfig.cooldown, 1, 10);
        addSlider("Time", IConfig.time, 10000, 30000);
        addSlider("Pause Time", IConfig.pauseTime, 1000, 10000);
        
        JButton exit = new JButton("Terminer");
        exit.addActionListener(e -> setVisible(false));
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitPanel.add(exit);
        add(exitPanel);

        setVisible(true);
    }

    private void addSlider(String name, int initialValue, int min, int max) {
        JLabel label = new JLabel(name + ": " + initialValue);
        JSlider slider = new JSlider(min, max, initialValue);
        
        slider.addChangeListener(e -> {
            int value = ((JSlider) e.getSource()).getValue();
            label.setText(name + ": " + value);
            
            switch (name) {
            case "Radius":
            	IConfig.setRadius(value);
                break;
            case "Speed":
            	IConfig.setSpeed((double)value/100);
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

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(label);
        panel.add(slider);
        add(panel);
    }

}
