package dev.simpgorillas.model;

import dev.simpgorillas.model.entities.Gorilla;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimpModel {

    public int gameWidth, gameHeight;
    public int hitZone;
    public boolean player1Turn = true;
    public int playerWin = 0;
    public int WinScoreCondition = 10;
    public String gameLog = "";

    public Gorilla player1;
    public Gorilla player2;


    public void init() {
        player1 = new Gorilla(0, gameHeight - Gorilla.HEIGHT, true);
        player2 = new Gorilla(gameWidth - Gorilla.WIDTH, gameHeight - Gorilla.HEIGHT, false);

        hitZone = gameWidth / 50;
    }

    public void drawGame(GraphicsContext gc) {
        gc.setFill(Color.rgb(3, 123, 252));
        gc.fillRect(0, 0, gameWidth, gameHeight);

        player1.render(gc);
        player2.render(gc);
    }

    public int calcDist(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow(((y2 - y1)), 2));
    }

    public int calcAngle(double x1, double y1, double x2, double y2) {
        return (int) Math.toDegrees(Math.atan((y2 - y1) / (x2 - x1)));
    }
}
