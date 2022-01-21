package dev.simpgorillas.control;

import dev.simpgorillas.model.SimpModel;
import dev.simpgorillas.model.entities.Banana;
import dev.simpgorillas.view.SimpView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.Scanner;
import java.util.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SimpController {

    public SimpModel model;
    public SimpView view;
    public Stage stage;

    public SimpController(SimpModel model, SimpView view, Stage stage){
        this.model = model;
        this.view = view;
        this.stage = stage;

        view.setStartScene();
        setStartControls();
    }

    public void setStartControls() {
        // Get parameters for game, if legal values, and set up the game
        // Else indicate illegal values directly at the GUI
        view.playButton.setOnAction(actionEvent -> {
            // Checks for legal input
            boolean legalWidth = model.legalWidth(
                    view.widthInput.getText(),
                    Screen.getPrimary().getBounds().getWidth());
            boolean legalHeight = model.legalHeight(
                    view.heightInput.getText(),
                    Screen.getPrimary().getVisualBounds().getHeight());

            boolean startCond = legalWidth && legalHeight;

            if (startCond) {
                // Sets the width and height of the model
                model.gameWidth = Integer.parseInt(view.widthInput.getText());
                model.gameHeight = Integer.parseInt(view.heightInput.getText());
                model.init();

                view.setGameScene();
                model.drawGame(view.gc);
                setGameControls();

                stage.setTitle("SimpGorillas!");
                stage.setScene(view.gameScene);
                stage.centerOnScreen();
            } else {
                // Width input error effect
                if (!legalWidth) {
                    view.widthInput.setBorder(view.redBorder);
                } else {
                    view.widthInput.setBorder(view.greenBorder);
                }
                // Height input error effect
                if (!legalHeight) {
                    view.heightInput.setBorder(view.redBorder);
                } else {
                    view.heightInput.setBorder(view.greenBorder);
                }
            }
        });
    }

    public void setGameControls() {
        // TextField based controls for player1
        view.throwBtn1.setOnAction(actionEvent -> {
            // Check for legalInput
            boolean legalAngle = model.legalInput(view.angle1Input.getText());
            boolean legalVelocity = model.legalInput(view.velocity1Input.getText());

            boolean legalInput = legalAngle && legalVelocity;

            if (model.player1Turn && legalInput) {
                // Get values and throw banana
                int angle = Integer.parseInt(view.angle1Input.getText());
                int velocity = Integer.parseInt(view.velocity1Input.getText());

                Shoot(angle, velocity);

                // Change turn
                view.player1Controls.setDisable(true);
                view.player2Controls.setDisable(false);

                // Set the borders to normal in case of previous illegal input
                view.angle1Input.setBorder(view.normalBorder);
                view.velocity1Input.setBorder(view.normalBorder);
            } else {
                if (!legalAngle) {
                    view.angle1Input.setBorder(view.redBorder);
                } else {
                    view.angle1Input.setBorder(view.normalBorder);
                }
                if (!legalVelocity) {
                    view.velocity1Input.setBorder(view.redBorder);
                } else {
                    view.velocity1Input.setBorder(view.normalBorder);
                }
            }
        });

        // TextField based controls for player2
        view.throwBtn2.setOnAction(actionEvent -> {
            // Check for legalInput
            boolean legalAngle = model.legalInput(view.angle2Input.getText());
            boolean legalVelocity = model.legalInput(view.velocity2Input.getText());

            boolean legalInput = legalAngle && legalVelocity;

            if (!model.player1Turn && legalInput) {
                // Get values and throw banana
                int angle = Integer.parseInt(view.angle2Input.getText());
                int velocity = Integer.parseInt(view.velocity2Input.getText());
                long wind = 0;

                Shoot(angle, velocity);

                // Change turn
                view.player1Controls.setDisable(false);
                view.player2Controls.setDisable(true);

                // Set the borders to normal in case of previous illegal input
                view.angle2Input.setBorder(view.normalBorder);
                view.velocity2Input.setBorder(view.normalBorder);
            } else {
                if (!legalAngle) {
                    view.angle2Input.setBorder(view.redBorder);
                } else {
                    view.angle2Input.setBorder(view.normalBorder);
                }
                if (!legalVelocity) {
                    view.velocity2Input.setBorder(view.redBorder);
                } else {
                    view.velocity2Input.setBorder(view.normalBorder);
                }
            }
        });
    }

    public void Shoot(int Angle, int Velocity) {
        // Clear map
        model.drawGame(view.gc);

        // Setup Parameters
        boolean Player1Turn = model.player1Turn;
        int lands;

        // Throw
        if (Player1Turn) {
            lands = model.player1.throwBanana(view.gc, Angle, Velocity, model.gameHeight);
        }
        else {
            lands = model.player2.throwBanana(view.gc, Angle, Velocity, model.gameHeight);
        }

        // Check for hit
        if (model.player2.isHit(lands, model.hitZone)) {
            model.player1.score++;
            view.player1Label.setText("Player 1 - Score: " + model.player1.score);
        } else if (model.player1.isHit(lands, model.hitZone)) {
            model.player2.score++;
            view.player2Label.setText("Player 2 - Score: " + model.player2.score);
        }

        // Change turn
        model.player1Turn = !Player1Turn;
    }
}
