package com.simpgorillas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Banana {
    public double x, y, velocity, dir;
    double angle;
    private final double GRAV = 9.81;


    public Banana(int x, int y, int angle, int velocity, int dir) {
        this.x = x;
        this.y = Main.SCREEN_HEIGHT - y;
        this.angle = angle * Math.PI / 180;
        this.velocity = velocity;
        this.dir = dir;
    }

    public boolean hit() {

        return true;
    }

    public void update(GraphicsContext gc) {
        double t = 0.001;
        double initX = x;
        double initY= y;

        while(true) {
            y = initY + velocity * Math.sin(angle) * t - 0.5 * GRAV * t * t;
            t += 0.01;
            if (y >= 0) {
                x = initX + (dir * velocity * Math.cos(angle) * t);
                draw(gc);
                System.out.println(x + ", " + y);
                continue;
            }
            break;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(255, 221, 0));
        gc.fillRect(x, Main.SCREEN_HEIGHT - y,1,1);
    }
}
