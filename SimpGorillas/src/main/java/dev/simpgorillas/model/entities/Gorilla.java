package dev.simpgorillas.model.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Gorilla {

    public int x, y, centerX, dir;
    public int score;
    public static final int WIDTH = 12;
    public static final int HEIGHT = 20;

    public Gorilla(int x, int y, boolean facingRight) {
        this.x = x;
        this.y = y;
        if (facingRight) {
            dir = 1;
        } else {
            dir = -1;
        }
        this.centerX = x + WIDTH / 2;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(128, 83, 29));
        gc.fillRect(x, y, WIDTH, HEIGHT);
    }

    public int throwBanana(GraphicsContext gc, double angle, double velocity, int gameHeight) {
        Banana banana = new Banana(angle, velocity, dir);

        double t = 0.01;
        while (true) {
            banana.update(x + WIDTH / 2, y, t);
            t += 0.01;

            // Check if the Banana is still in the air
            if (banana.getY() < gameHeight) {
                banana.render(gc);
                continue;
            }
            break;
        }

        return (int) banana.getX();
    }

    public boolean isHit(int lands, int hitZone) {
        if ((lands >= centerX - hitZone) && (centerX + hitZone >= lands)) {
            return true;
        }
        return false;
    }

}
