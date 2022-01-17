package dev.simpgorillas.model;

import dev.simpgorillas.model.entities.Banana;
import dev.simpgorillas.model.entities.Gorilla;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimpModel {

    public static int gameWidth, gameHeight;
    public static int hitZone;
    public static boolean player1Turn = true;

    public static Gorilla player1;
    public static Gorilla player2;


    public static void init() {
        player1 = new Gorilla(0, gameHeight - Gorilla.HEIGHT,true);
        player2 = new Gorilla(gameWidth - Gorilla.WIDTH, gameHeight - Gorilla.HEIGHT, false);

        hitZone = gameWidth / 50;
    }

    public static void drawGame(GraphicsContext gc) {
        gc.setFill(Color.rgb(3, 123, 252));
        gc.fillRect(0,0, SimpModel.gameWidth, SimpModel.gameHeight);

        player1.render(gc);
        player2.render(gc);
    }

}
