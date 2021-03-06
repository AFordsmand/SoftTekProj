package dev.advgorillas.model;

import dev.advgorillas.model.entities.Gorilla;
import dev.advgorillas.model.entities.Wind;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Scanner;

// ==========================================
// AdvModel class, made by William Steffens
// ==========================================
public class AdvModel {

    public int gameWidth, gameHeight;
    public int hitZone;
    public boolean player1Turn = true;
    public int playerWin = 0;
    public int winScoreCondition = 5;
    public String gameLog = "";
    public Scanner replayer = null;

    public Gorilla player1;
    public Gorilla player2;

    public Wind wind;

    public int secondsPassed;
    public Timeline timeline;

    Image background = new Image(getClass().getResourceAsStream("background.png"));


    public void init() {
        player1 = new Gorilla(0, gameHeight - Gorilla.HEIGHT, true);
        player2 = new Gorilla(gameWidth - Gorilla.WIDTH, gameHeight - Gorilla.HEIGHT, false);

        hitZone = gameWidth / 50;

        secondsPassed = 0;

        wind = new Wind();
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

    public boolean legalWind(String toCheck) {
        try {
            int input = Integer.parseInt(toCheck);
            if (input == 1 || input == 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public void drawGame(GraphicsContext gc, Boolean playerTurn) {
        //gc.setFill(Color.rgb(3, 123, 252));
        //gc.fillRect(0, 0, gameWidth, gameHeight);
    	gc.drawImage(background, 0, 0, gameWidth, gameHeight);
    	
        player1.updatePlayerModel(gc, playerTurn);
        player2.updatePlayerModel(gc, playerTurn);
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
        return (int) Math.toDegrees(Math.atan((y2 - y1) / (x2 - x1)));
    }
}
