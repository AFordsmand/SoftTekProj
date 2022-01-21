package dev.advgorillas.model.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Banana {

    private double x, y;
    private final double angle, velocity, dir, wind;
    public static double grav = 9.81;

    private final Color bananaYellow = Color.rgb(255, 221, 0);

    public Banana(double angle, double velocity, double dir, double wind) {
        this.angle = Math.toRadians(angle);
        this.velocity = velocity;
        this.dir = dir;
        this.wind = wind;
    }

    public void update(int initX, int initY, double t) {
        y = initY - velocity * Math.sin(angle) * t + 0.5 * grav * t * t;
        x = initX + (dir * velocity * Math.cos(angle) * t + wind * t * t);
    }

    public void render(GraphicsContext gc) {
        gc.setFill(bananaYellow);
        gc.fillRect(x, y, 1, 5);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
