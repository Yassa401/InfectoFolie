package main.java;

public class Player {
    private int x;
    private int y;
    private double speed;
    private int radius = IConfig.RADIUS ;
    
    public Player(int initialX, int initialY, double speed) {
        this.x = initialX;
        this.y = initialY;
        this.speed = speed;
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

    public void move(double angle, double distance) {
        // Convertir l'angle en radians
        double radians = Math.toRadians(angle);

        // Calculer les déplacements sur les axes x et y
        int deltaX = (int) (distance * speed * Math.cos(radians));
        int deltaY = (int) (distance * speed * Math.sin(radians) * -1);

        // Appliquer le déplacement
        if( x+deltaX+IConfig.RADIUS < IConfig.LARGEUR_FENETRE && x+deltaX-IConfig.RADIUS > 0)
        	x += deltaX;
        if(y+deltaY+IConfig.RADIUS < IConfig.LONGUEUR_FENETRE && y+deltaY-IConfig.RADIUS > 0)
        	y += deltaY;
    }
}

