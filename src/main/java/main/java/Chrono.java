package main.java;

import java.text.DecimalFormat;

public class Chrono {
    private static final DecimalFormat decimalFormat1 = new DecimalFormat("00");  // pour h, min, s
    private static final DecimalFormat decimalFormat2 = new DecimalFormat("000"); // pour les ms
    private static long startTime;
    public static boolean running = false;
    private static int totalTime = 5000; //20000; 
    private static long remainingTime = totalTime;
    private static String timer = "00.00.20.000";
    private static int hours = 0, minutes = 0;
    private static int seconds = 0;	// en static pour l'envoyer au client
    

    public static void startTimer() {
    	long currentTime;
    	long elapsedTime;
    	
        startTime = System.currentTimeMillis();
        //running = true;

        while (running) {
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;
            
            // calculer le temps restant
            remainingTime = totalTime - elapsedTime;
            
            if(remainingTime <= 0) {
            	running = false;
            	remainingTime = 0;
            }
            
            updateTimer(remainingTime);
            
            timer = formatTime(remainingTime);
            //System.out.println("(" + timer + ")");

            try {
                Thread.sleep(1); // Attendre 1ms avant la prochaine itération pour éviter d'utiliser trop de ressources CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void updateTimer(long millis) {
        seconds = (int) (millis / 1000) % 60;
        minutes = (int) ((millis / (1000 * 60)) % 60);
        hours = (int) ((millis / (1000 * 60 * 60)) % 24);
    }
    
    private static String formatTime(long millis) {
        int milliseconds = (int) (millis % 1000);

        return decimalFormat1.format(hours) + ":" + decimalFormat1.format(minutes) + ":" +
                decimalFormat1.format(seconds) + "." + decimalFormat2.format(milliseconds);
    }

    public static int getSecondes() {
        return seconds;
    }
    
    public static String getTimer() {
    	return timer; 
    }
    
    public static boolean isRunning() {
    	return running;
    }
    
    public static void stopTimer() {
        running = false;
    }
}
