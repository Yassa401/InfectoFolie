package main.java;

import com.badlogic.gdx.graphics.Color;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Player {
    private int x;
    private int y;
    private final double speed;
    private Color couleur ;

    private final Color couleurInit ;
    private final int radius = IConfig.RADIUS ;

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
        this.peutEtreInfect = 1000;
        
        this.statut = 0;	// par défaut
        nbPlayers++;
        this.numPlayer = nbPlayers; // numPlayer vaut nbPlayers actuel
    }
    
    public int getX() {
        return this.x;
    }
    
    public int setX(int newx){
        return this.x = newx;
    }

    public Color getColorInit(){
        return this.couleurInit;
    }

    public int getY() { return this.y; }
    
    public int setY(int newy){
        return this.y = newy;
    }
    
    public int getRadius() {
    	return this.radius;
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
    
    public void setPeutEtreInfect(int nouvelleValeur) {
    	this.peutEtreInfect = nouvelleValeur; 
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
                }
                double angleDePoussée = Math.atan2(this.y - autrePlayer.getY(), this.x - autrePlayer.getX());
                // Appliquez une petite force pour repousser les joueurs
                int forceDeRepulsion = 2; // Ajustez cette valeur selon le besoin
                this.x += forceDeRepulsion * Math.cos(angleDePoussée);
                this.y += forceDeRepulsion * Math.sin(angleDePoussée);

                // Appliquer une force opposée à l'autre joueur
                autrePlayer.setX((int)(autrePlayer.getX() - forceDeRepulsion * Math.cos(angleDePoussée)));
                autrePlayer.setY((int)(autrePlayer.getY() - forceDeRepulsion * Math.sin(angleDePoussée)));

                // Détecter à nouveau la collision après correction (optionnel)
                if (!verifCollision(this.x, this.y, autrePlayer)) {
                    // Plus de collision, appliquez le mouvement
                    this.setX(newX);
                    this.setY(newY);
                    return;
                } else {
                    // Il y a encore une collision, ne pas bouger plus loin
                    return;
                }
            }
        }
   
        if (verifCollisionMur(newX, newY)) {
        	return;     
        }

        // Apres toutes les verifications
        this.setX(newX);  this.setY(newY);
    }

    public boolean verifCollisionMur(int newX, int newY) {
        // Create a rectangle that represents the player's new position and area
        Rectangle2D playerBounds = new Rectangle2D.Float(newX - radius, newY - radius, 2 * radius, 2 * radius);
        // Check for collision with each wall

        boolean collided = false;
        if (GameFrame.murHaut.intersects(playerBounds)) {
            newY =(int) GameFrame.murHaut.getY() - radius - 3;
            collided = true;
        }
        if (GameFrame.murBas.intersects(playerBounds)) {
            newY =(int) GameFrame.murBas.getY() + radius + 8 ;
            collided = true;
        }
        if (GameFrame.murGauche.intersects(playerBounds)) {
            newX =(int) GameFrame.murGauche.getX() + radius + 8;
            collided = true;
        }
        if (GameFrame.murDroit.intersects(playerBounds)) {
            newX =(int) GameFrame.murDroit.getX() - radius - 3;
            collided = true;
        }

        if (GameFrame.obs1.intersects(playerBounds)) {
            // Collision horizontale
            // Comparer la position y du centre du joueur avec la position y du centre de l'obstacle
            float centreObstacleY = (float) (GameFrame.obs1.getY() + GameFrame.obs1.getHeight() / 2);
            if (newY < centreObstacleY) {
                // Collision sur le côté gauche de l'obstacle
                newY = (int) GameFrame.obs1.getY() - radius - 2;
            } else {
                // Collision sur le côté droit de l'obstacle
                newY = (int) (GameFrame.obs1.getY() + GameFrame.obs1.getHeight() + radius + 2);
            }
            // Appliquer la position corrigée
            setY(newY);
            collided = true;
        }

        if (GameFrame.obs2.intersects(playerBounds)) {
        // Collision horizontale
        // Comparer la position x du centre du joueur avec la position x du centre de l'obstacle
        float centreObstacleX = (float) (GameFrame.obs2.getX() + GameFrame.obs2.getWidth() / 2);
        if (newX < centreObstacleX) {
            // Collision sur le côté gauche de l'obstacle
            newX = (int) GameFrame.obs2.getX() - radius - 2;
        } else {
            // Collision sur le côté droit de l'obstacle
            newX = (int) (GameFrame.obs2.getX() + GameFrame.obs2.getWidth() + radius + 2);
        }
        // Appliquer la position corrigée
        setX(newX);
            collided = true;
        }

        if (GameFrame.obs3.intersects(playerBounds)) {
            // Collision horizontale
            // Comparer la position x du centre du joueur avec la position x du centre de l'obstacle
            float centreObstacleX = (float) (GameFrame.obs3.getX() + GameFrame.obs3.getWidth() / 2);
            if (newX < centreObstacleX) {
                // Collision sur le côté gauche de l'obstacle
                newX = (int) GameFrame.obs3.getX() - radius - 2;
            } else {
                // Collision sur le côté droit de l'obstacle
                newX = (int) (GameFrame.obs3.getX() + GameFrame.obs3.getWidth() + radius + 2);
            }
            // Appliquer la position corrigée
            setX(newX);
            collided = true;
        }

        if (GameFrame.obs4.intersects(playerBounds)) {
            // Collision horizontale
            // Comparer la position y du centre du joueur avec la position y du centre de l'obstacle
            float centreObstacleY = (float) (GameFrame.obs4.getY() + GameFrame.obs4.getHeight() / 2);
            if (newY < centreObstacleY) {
                // Collision sur le côté gauche de l'obstacle
                newY = (int) GameFrame.obs4.getY() - radius - 2;
            } else {
                // Collision sur le côté droit de l'obstacle
                newY = (int) (GameFrame.obs4.getY() + GameFrame.obs4.getHeight() + radius + 2);
            }
            // Appliquer la position corrigée
            setY(newY);
            collided = true;
        }



        // Mettre à jour la position corrigée si une collision a eu lieu
        if (collided) {
            setX(newX);
            setY(newY);
        }

        return collided;
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
    	return this.couleur ;
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

