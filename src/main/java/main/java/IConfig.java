package main.java;

public class IConfig {

	public static int LARGEUR_FENETRE = 1300 ;
	public static int LONGUEUR_FENETRE = 800 ;
	
	public static int RADIUS = 18 ;
	
	public static double SPEED = 0.4 ;

    public static final int maxJoueurs = 45 ;
	public static int widthObs = 100;
	public static int heightObs = 10;
	public static int cooldown = 2;
	public static int time = 20000;
	public static int pauseTime = 5000;
    public static float pourcentageInfectes = 30;


    public static void setSpeed(double newValue) {
        SPEED = newValue;
    }

    public static void setRadius(int newValue) {
        RADIUS = newValue;
    }

    public static void setCooldown(int newValue) {
        cooldown = newValue;
    }

    public static void setTime(int newValue) {
        time = newValue;
    }

    public static void setPauseTime(int newValue) {
        pauseTime = newValue;
    }
}
