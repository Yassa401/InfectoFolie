package main.java;

import com.badlogic.gdx.graphics.Color;
import java.util.Random;

public class ColorUpdater implements Runnable {
    private volatile boolean running = true;
    private volatile Color currentColor = Color.LIGHT_GRAY;

    public Color getCurrentColor() {
        return currentColor;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (running) {
            // Générer une nouvelle couleur aléatoire semi-transparente
            float r = random.nextFloat() * 0.24f + 0.75f; // rouge entre 0.8 et 0.99
            float g = random.nextFloat() * 0.24f + 0.75f;
            float b = random.nextFloat() * 0.24f + 0.75f;
            currentColor = new Color(r, g, b, 0.5f);

            // Pause de 100ms
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }
}
