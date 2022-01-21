package com.simpgorillas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Gorilla {

    public int x, y, dir, xCenter, score;
    public static final int WIDTH = 12;
    public static final int HEIGHT = 20;


    public Gorilla(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        xCenter = x + WIDTH / 2;
    }

    public double throwBanana(int angle, int velocity, GraphicsContext gc) {
        Banana banana = new Banana(xCenter, y, angle, velocity, dir);
        banana.update(gc);

        return banana.x;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(128, 83, 29));
        gc.fillRect(x, y, WIDTH, HEIGHT);
    }
}
