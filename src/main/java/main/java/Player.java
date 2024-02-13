package main.java;

import com.badlogic.gdx.graphics.Color;
import java.util.Random;

public class Player {
    private int x;
    private int y;
    private double speed;
    private Color couleur;
    private int radius = IConfig.RADIUS ;
    
    public Player(int initialX, int initialY, double speed) {
        this.x = initialX;
        this.y = initialY;
        this.speed = speed;
        randomCouleur();
        
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getRadius() {
    	return radius;
    }
    
    /**
     * Calculer la nouvelle position selon l'angle du joystick
     * @param angle : angle du joystick ( 0° à 360° )
     * @param distance : distance par rapport au centre du joystick (peut être utilisé pour la vitesse)
     */
    public void move(double angle, double distance) {
        // Convertir l'angle en radians
        double radians = Math.toRadians(angle);

        // Calculer les déplacements sur les axes x et y
        int deltaX = (int) (distance * speed * Math.cos(radians));
        int deltaY = (int) (distance * speed * Math.sin(radians));

        // Appliquer le déplacement
        if( x+deltaX+IConfig.RADIUS < IConfig.LARGEUR_FENETRE/2 && x+deltaX-IConfig.RADIUS > -IConfig.LARGEUR_FENETRE/2)
        	x += deltaX;
        if(y+deltaY+2*IConfig.RADIUS < IConfig.LONGUEUR_FENETRE/2 && y+deltaY > -IConfig.LONGUEUR_FENETRE/2)
        	y += deltaY;
    }
    
    /*
     * Renvoyer la couleur du joueur
     */
    Color getCouleur() {
    	return couleur ;
    }
    
    /*
     * Associer une couleur ALEATOIRE au joueur (appelé dans le constructeur
     */
    public void randomCouleur() {
    	Random random = new Random();
    	couleur = new Color(
    		    random.nextFloat(), // Composante rouge aléatoire entre 0 et 1
    		    random.nextFloat(), // Composante verte aléatoire entre 0 et 1
    		    random.nextFloat(), // Composante bleue aléatoire entre 0 et 1
    		    1                  // Opacité à 100% (valeur entre 0 et 1)
    		);
    	
    }
}

