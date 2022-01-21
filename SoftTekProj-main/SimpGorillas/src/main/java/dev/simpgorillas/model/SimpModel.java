package dev.simpgorillas.model;

import dev.simpgorillas.model.entities.Gorilla;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Scanner;

public class SimpModel {

    public int gameWidth, gameHeight;
    public int hitZone;
    public boolean player1Turn = true;

    public Gorilla player1;
    public Gorilla player2;



    public void init() {
        player1 = new Gorilla(0, gameHeight - Gorilla.HEIGHT, true);
        player2 = new Gorilla(gameWidth - Gorilla.WIDTH, gameHeight - Gorilla.HEIGHT, false);

        hitZone = gameWidth / 50;
    }

    public boolean legalWidth(String toCheck, double maxWidth) {
        if (isPosInt(toCheck)) {
            int inputWidth = Integer.parseInt(toCheck);
            if (inputWidth < 500) {
                return false;
            }
            if (inputWidth > maxWidth) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean legalHeight(String toCheck, double maxHeight) {
        if (isPosInt(toCheck)) {
            int inputHeight = Integer.parseInt(toCheck);
            if (inputHeight < 400) {
                return false;
            }
            if (inputHeight > maxHeight) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isPosInt(String toCheck) {
        try {
            int input = Integer.parseInt(toCheck);
            if (input > 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public boolean isPosDouble(String toCheck) {
        try {
            double input = Double.parseDouble(toCheck);
            if (input > 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public boolean legalInput(String toCheck) {
        try {
            int input = Integer.parseInt(toCheck);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void drawGame(GraphicsContext gc) {
        gc.setFill(Color.rgb(3, 123, 252));
        gc.fillRect(0, 0, gameWidth, gameHeight);

        player1.render(gc);
        player2.render(gc);
    }
}
