package main.java;

import com.badlogic.gdx.graphics.Color;

import java.awt.geom.Rectangle2D;
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
        this.couleur = Color.CYAN;
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
        // Enregistrer la position actuelle du joueur
        int oldX = x;
        int oldY = y;

        // Convertir l'angle en radians
        double radians = Math.toRadians(angle);

        // Calculer les déplacements sur les axes x et y
        int deltaX = (int) (distance * speed * Math.cos(radians));
        int deltaY = (int) (distance * speed * Math.sin(radians));

        // Appliquer le déplacement temporairement
        int newX = x + deltaX;
        int newY = y + deltaY;

        // Vérifier les collisions avec les autres joueurs
        for (Player autrePlayer : GameServer.players.values()) {
            if (autrePlayer != this && verifCollision(newX, newY, autrePlayer)) {
                // Annuler le déplacement en restaurant les positions précédentes du joueur
                return; // Sortir de la méthode après avoir détecté une collision
            }
        }
        
        if (verifCollisionMur(newX, newY)) {
                return ;
        }


        // Apres toutes les verifications
        x = newX; y = newY;
    }

    public boolean verifCollisionMur(int newX, int newY) {
        for (Rectangle2D mur : GameFrame.murs) {
                // Collision détectée
        		/*if (mur.intersects(newX, getY(), getRadius(), getRadius())) {
        			if(mur.intersects(getX(), newY, getRadius(), getRadius())) {
        				return true ;
        			}else {
        				y = newY;
        				return true;
        			}
        		}else {
	        		if(mur.intersects(getX(), newY, getRadius(),getRadius())) {
	        			if(mur.intersects(newX, getY(), getRadius(), getRadius())) {
	        				return true ;
	        			}else {
		        			x = newX ;
	        				System.out.println("mur \n\n\n");
	        				return true;
	        			}
        			}else {
        				if(mur.intersects(newX, newY, getRadius(),getRadius())) {
        					return true ;
        				}
        			}
        		}*/
        	// Teste avec les deux nouvelles coordonnees d'abord
	        if(mur.intersects(newX, newY, getRadius(),getRadius())) {
	        	// Teste chacun des coordonnées comme ça l'autre peut avoir une nouvelle valeur
	        	// si ça ne rentre pas dans l'obstacle (ça rend le déplacement plus fluide)
	        	if (mur.intersects(newX, getY(), getRadius(), getRadius())) {
	    			if(mur.intersects(getX(), newY, getRadius(), getRadius())) {
	    				return true ;
	    			}else {
	    				y = newY;
	    				return true;
	    			}
	    		}else {
        			x = newX ;
    				System.out.println("mur \n\n\n");
    				return true;	
	    		}
	        }
	        
        }
        return false;
    }

    /**
     * Calculer la ndistance entre autrePlayer et le player acutel
     * @param newX : position x du joueur actuel
     * @param newY : position y du joueur actuel
     * @param autrePlayer : joueur avec qui il faut vérifier si il y a une collision ou non
     */

    public boolean verifCollision(int newX, int newY, Player autrePlayer) {
        // Calculer la distance entre les centres des deux joueurs
        int distanceX = newX - autrePlayer.getX();
        int distanceY = newY - autrePlayer.getY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        // Vérifier s'ils se chevauchent
        return distance < (this.radius + autrePlayer.getRadius());
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

    public int setX(int newx){
        return this.x = newx;
    }

    public int setY(int newy){
        return this.y = newy;
    }
}

