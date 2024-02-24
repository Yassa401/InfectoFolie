package main.java;

import java.text.DecimalFormat;

public class Chrono {
    private static final DecimalFormat decimalFormat = new DecimalFormat("00");
    private static long startTime;
    private static boolean running;
    private static int totalTime = 5000; // 5 secondes pour l'instant
    private static int hours = 0, minutes = 0, seconds = 0;

    public void startTimer() {
        startTime = System.currentTimeMillis();
        running = true;

        while (running) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;
            updateTimer(elapsedTime);

            String formattedTime = formatTime(elapsedTime);
            //System.out.println("(" + formattedTime + ")");

            try {
                Thread.sleep(1); // Attendre 1ms avant la prochaine itération pour éviter d'utiliser trop de ressources CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (elapsedTime >= totalTime) {
                startTime = System.currentTimeMillis(); // Réinitialiser le temps de départ
                elapsedTime = 0; // Réinitialiser le temps écoulé
                
                try {
                    Thread.sleep(1000); // Attente 1s avant la prochaine itération pour éviter d'utiliser trop de ressources CPU
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

        return decimalFormat.format(hours) + ":" + decimalFormat.format(minutes) + ":" +
                decimalFormat.format(seconds) + "." + decimalFormat.format(milliseconds);
    }

    public static int getSecondes() {
        return seconds;
    }
    
    public String getTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        updateTimer(elapsedTime);
        return formatTime(elapsedTime);
    }
    /*
    public static String getTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        updateTimer(elapsedTime);
        return formatTime(elapsedTime);
    }*/
    
    public static void stopTimer() {
        running = false;
    } 
}
