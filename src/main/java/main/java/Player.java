package main.java;

import com.badlogic.gdx.graphics.Color;

import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Player {
    private int x;
    private int y;
    private double speed;
    private Color couleur ;

    private Color couleurInit ;
    private int radius = IConfig.RADIUS ;

    private int peutEtreInfect;
    
    private int statut;		// 0 : non-inf, 1 : inf, 2 : mort
    private static int nbPlayers = 0;
    private int numPlayer;
    public Player(int initialX, int initialY, double speed) {
        this.x = initialX;
        this.y = initialY;
        this.speed = speed;
        this.randomCouleur();
        couleurInit = couleur;
        this.peutEtreInfect = 100;
        
        this.statut = 0;	// par défaut
        nbPlayers++;
        this.numPlayer = nbPlayers; // numPlayer vaut nbPlayers actuel
    }
    
    public int getX() {
        return x;
    }
    
    public int setX(int newx){
        return this.x = newx;
    }

    public Color getColorInit(){
        return this.couleurInit;
    }

    public int getY() {
        return y;
    }
    
    public int setY(int newy){
        return this.y = newy;
    }
    
    public int getRadius() {
    	return radius;
    }
    
    public int getStatut() {
    	return this.statut;
    }
    
    public void setStatut(int s) {
    	this.statut = s;
    }
    
    public int getNumPlayer() {
    	return this.numPlayer;
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
            if ((autrePlayer != this && verifCollision(newX, newY, autrePlayer))) {
                if ((this.getStatut() == 1 && autrePlayer.getStatut() != 1) && (autrePlayer.peutEtreInfect - Chrono.getSecondes()) >= IConfig.cooldown ) {
                    Game.infectPlayer(autrePlayer);
                    this.peutEtreInfect = Chrono.getSecondes();
                    Game.healPlayer(this);
                }/*else if ((this.getStatut() != 1 && autrePlayer.getStatut() == 1 )){
                    Game.infectPlayer(this);
                    Game.healPlayer(autrePlayer);
                }*/
                // Annuler le déplacement en restaurant les positions précédentes du joueur
                return; // Sortir de la méthode après avoir détecté une collision
            }
        }
        
        
        
        
        
   
        if (verifCollisionMur(newX, newY)) {
        	return;     
        }
        
        

        // Apres toutes les verifications
        x = newX; y = newY;
    }

    public boolean verifCollisionMur(int newX, int newY) {
    	if((GameFrame.murHaut.intersects(getX(), newY, getRadius(), getRadius()) ||
    		GameFrame.murBas.intersects(getX(), newY, getRadius(), getRadius()) ) && 
    		(GameFrame.murDroit.intersects(newX, getY(), getRadius(), getRadius()) || 
    		GameFrame.murGauche.intersects(newX, getY(), getRadius(),getRadius())
        	)) {
    		return true;
    	}
    	
    	else{ 
	    	for (Rectangle2D mur : GameFrame.murs) {
	    		// Collision détectée
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
	    				return true;	
		    		}
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
    
    public void setCouleur(Color c) {
    	this.couleur = c;
    }


    
    /*
     * Associer une couleur ALEATOIRE au joueur (appelé dans le constructeur
     */
    public void randomCouleur() {
        Random random = new Random();
        float red;
        do {
            red = random.nextFloat();
        } while (red == 240f / 255f);

        couleur = new Color(
                red,
                random.nextFloat(),
                random.nextFloat(),
                1
        );

    }

}

