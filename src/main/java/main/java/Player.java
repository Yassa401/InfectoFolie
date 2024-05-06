package main.java;

import com.badlogic.gdx.graphics.Color;

import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.util.List;
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

    public void setX(int newx){
        this.x = newx;
    }

    public Color getColorInit(){
        return this.couleurInit;
    }

    public int getY() { return this.y; }

    public void setY(int newy){
        this.y = newy;
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
        int deltaX = (int) (distance * IConfig.SPEED * Math.cos(radians));
        int deltaY = (int) (distance * IConfig.SPEED * Math.sin(radians));

        // Appliquer le déplacement temporairement
        int newX = x + deltaX;
        int newY = y + deltaY;

        // Vérifier les collisions avec les autres joueurs
        for (Player autrePlayer : GameServer.players.values()) {
            if ((autrePlayer != this && verifCollision(newX, newY, autrePlayer))) {
                if ((this.getStatut() == 1 && autrePlayer.getStatut() == 0) && (autrePlayer.peutEtreInfect - Chrono.getSecondes()) >= IConfig.cooldown ) {
                    Game.infectPlayer(autrePlayer);
                    this.peutEtreInfect = Chrono.getSecondes();
                    Game.healPlayer(this);
                }

                handleRepulsion(autrePlayer);
                return; // Annuler toute action supplémentaire et sortir de la méthode
            }
        }

        if (!isCollidingWithWalls(newX, y, GameFrame.murs)) {
            this.x = newX;
        }
        if (!isCollidingWithWalls(x, newY, GameFrame.murs)) {
            this.y = newY;
        }
    }

    private void handleRepulsion(Player autrePlayer) {
        // Trouvons l'angle de poussée entre les joueurs, de 'this' à 'autrePlayer'
        double angleDePoussée = Math.atan2(autrePlayer.getY() - this.y, autrePlayer.getX() - this.x);

        // Définissons une force de répulsion (plus ce nombre est élevé, plus la répulsion est forte)
        int forceDeRepulsion = 2;

        // Appliquons cette force aux deux joueurs, dans des directions opposées
        int repulsedXThis = this.x - (int)(forceDeRepulsion * Math.cos(angleDePoussée));
        int repulsedYThis = this.y - (int)(forceDeRepulsion * Math.sin(angleDePoussée));
        int repulsedXAutre = autrePlayer.getX() + (int)(forceDeRepulsion * Math.cos(angleDePoussée));
        int repulsedYAutre = autrePlayer.getY() + (int)(forceDeRepulsion * Math.sin(angleDePoussée));

        // Assurons-nous que la répulsion n'entraîne pas les joueurs dans un mur
        if (!isCollidingWithWalls(repulsedXThis, this.y, GameFrame.murs)) {
            this.x = repulsedXThis;
        }
        if (!isCollidingWithWalls(this.x, repulsedYThis, GameFrame.murs)) {
            this.y = repulsedYThis;
        }
        if (!isCollidingWithWalls(repulsedXAutre, autrePlayer.getY(), GameFrame.murs)) {
            autrePlayer.setX(repulsedXAutre);
        }
        if (!isCollidingWithWalls(autrePlayer.getX(), repulsedYAutre, GameFrame.murs)) {
            autrePlayer.setY(repulsedYAutre);
        }
    }

    public boolean isCollidingWithWalls(int newX, int newY, List<Rectangle2D> murs) {
        // Créez un rectangle correspondant à la nouvelle position du joueur avec le rayon pour détecter les collisions
        Rectangle2D playerRect = new Rectangle2D.Float(newX - radius, newY - radius, radius * 2, radius * 2);

        // Vérifiez la collision avec chaque mur
        for (Rectangle2D mur : murs) {
            if (mur.intersects(playerRect)) {
                return true;
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