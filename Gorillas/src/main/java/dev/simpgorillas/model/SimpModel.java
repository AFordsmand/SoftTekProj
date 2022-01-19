package dev.simpgorillas.model;

import dev.simpgorillas.model.entities.Gorilla;
import dev.simpgorillas.model.entities.Wind;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Scanner;

public class SimpModel {

    public int gameWidth, gameHeight;
    public int hitZone;
    public boolean player1Turn = true;
    public int playerWin = 0;
    public int WinScoreCondition = 10;
    public String gameLog = "";
    public Scanner replayer = null;

    public Gorilla player1;
    public Gorilla player2;

    public Wind wind;

    public int secondsPassed;
    public Timeline timeline;


    public void init() {
        player1 = new Gorilla(0, gameHeight - Gorilla.HEIGHT, true);
        player2 = new Gorilla(gameWidth - Gorilla.WIDTH, gameHeight - Gorilla.HEIGHT, false);

        hitZone = gameWidth / 50;

        secondsPassed = 0;

        wind = new Wind();
    }

    public void drawGame(GraphicsContext gc) {
        gc.setFill(Color.rgb(3, 123, 252));
        gc.fillRect(0, 0, gameWidth, gameHeight);

        player1.render(gc);
        player2.render(gc);
    }

    public String formatTime() {
        String minStr = String.format("%02d", secondsPassed / 60 % 60);
        String secStr = String.format("%02d", secondsPassed % 60);
        return minStr + ":" + secStr;
    }

    public int calcDist(double x1, double y1, double x2, double y2) {
        return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow(((y2 - y1)), 2));
    }

    public int calcAngle(double x1, double y1, double x2, double y2) {
        /*
        double dot = x2 - x1;
        double cross = y2 - y1;
        double mag = calcDist(x1, y1, x2, y2);

        double cos = dot / mag;
        System.out.println(cos);
        System.out.println(Math.toDegrees(Math.acos(cos)));
        System.out.println(Math.toDegrees(Math.atan2(Math.abs(cross), dot)));
        */

        return (int) Math.toDegrees(Math.atan((y2 - y1) / (x2 - x1)));
    }
}
