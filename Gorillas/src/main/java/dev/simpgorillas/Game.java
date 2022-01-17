package dev.simpgorillas;

import dev.simpgorillas.model.entities.Banana;
import dev.simpgorillas.model.entities.Gorilla;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {

    private Group root;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;

    public static int WIDTH;
    public static int HEIGHT;

    private Gorilla player1;
    private Gorilla player2;
    private Banana activeBanana;

    private double angle1 = 50;

    private boolean player1Turn;
    private final int hitZone;


    public Game(GraphicsContext gc, Group root, int width, int height) {
        this.gc = gc;
        this.WIDTH = width;
        this.root = root;
        this.HEIGHT = height;
        hitZone = width / 50;

        run();
    }

    private void setup() {
        // Color the background
        gc.setFill(Color.rgb(0, 52, 224));
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Instantiate players
        player1 = new Gorilla(0, HEIGHT - Gorilla.HEIGHT, true);
        player2 = new Gorilla(WIDTH - Gorilla.WIDTH, HEIGHT - Gorilla.HEIGHT, false);

        // Draw players
        player1.render(gc);
        player2.render(gc);
    }

    public void run() {
        setup();
        int hitX = player1.throwBanana(gc, angle1, 120);
        System.out.println(hitX);
        hitX = player2.throwBanana(gc, angle1, 120);
        System.out.println(hitX);




        /*gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // UPDATE


                // RENDER
                activeBanana.render(gc);

            }
        };

        gameLoop.start();*/

    }
}
