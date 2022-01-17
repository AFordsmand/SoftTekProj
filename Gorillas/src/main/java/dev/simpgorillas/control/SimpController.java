package dev.simpgorillas.control;

import dev.simpgorillas.model.SimpModel;
import dev.simpgorillas.model.entities.Gorilla;
import dev.simpgorillas.view.SimpView;
import javafx.event.Event;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimpController {

    // TODO change this
    private SimpModel model;
    private SimpView view;
    private Stage stage;

    public SimpController(SimpView view, SimpModel model, Stage stage){
        this.view = view;
        this.model = model;
        this.stage = stage;

        // TODO init stuff due to the order of calling and passing
    }



    public static void setStartControls(Stage stage) {
        // Get width and height (if legal) from startScene and set the stage's scene to gameScene
        SimpView.playButton.setOnAction(actionEvent -> {
            // Check widthInput and heightInput for legal values
            if (true) {
                // Sets the width and height of the model, and
                SimpModel.gameWidth = Integer.parseInt(SimpView.widthInput.getText());
                SimpModel.gameHeight = Integer.parseInt(SimpView.heightInput.getText());
                SimpModel.init();

                SimpView.setGameScene();
                setGameControls(stage);

                stage.setTitle("SimpGorillas!");
                stage.setScene(SimpView.gameScene);
                stage.centerOnScreen();
            } else {
                // Make the view turn red or something
            }

        });

    }

    public static void setGameControls(Stage stage) {
        SimpView.throwBtn1.setOnAction(actionEvent -> {
            if (SimpModel.player1Turn) {
                // Clear map
                SimpModel.drawGame(SimpView.gc);

                // Check for legal values and throw banana
                int angle = Integer.parseInt(SimpView.angle1Input.getText());
                int velocity = Integer.parseInt(SimpView.velocity1Input.getText());

                int lands = SimpModel.player1.throwBanana(SimpView.gc, angle, velocity);

                // Check for hit
                if (SimpModel.player2.isHit(lands, SimpModel.hitZone)) {
                    SimpModel.player1.score++;
                    SimpView.player1Label.setText("Player 1 - Score: " + SimpModel.player1.score);
                } else if (SimpModel.player1.isHit(lands, SimpModel.hitZone)) {
                    SimpModel.player2.score++;
                    SimpView.player2Label.setText("Player 2 - Score: " + SimpModel.player2.score);
                }

                // Change turn
                SimpModel.player1Turn = false;
                SimpView.player1Controls.setDisable(true);
                SimpView.player2Controls.setDisable(false);
            }
        });

        SimpView.throwBtn2.setOnAction(actionEvent -> {
            if (!SimpModel.player1Turn) {
                // Clear map
                SimpModel.drawGame(SimpView.gc);

                // Check for legal values and throw banana
                int angle = Integer.parseInt(SimpView.angle2Input.getText());
                int velocity = Integer.parseInt(SimpView.velocity2Input.getText());

                int lands = SimpModel.player2.throwBanana(SimpView.gc, angle, velocity);

                // Check for hit and inc score if hit
                if (SimpModel.player1.isHit(lands, SimpModel.hitZone)) {
                    SimpModel.player2.score++;
                    SimpView.player2Label.setText("Player 2 - Score: " + SimpModel.player2.score);
                } else if (SimpModel.player2.isHit(lands, SimpModel.hitZone)) {
                    SimpModel.player1.score++;
                    SimpView.player1Label.setText("Player 1 - Score: " + SimpModel.player1.score);
                }

                // Change turn
                SimpModel.player1Turn = true;
                SimpView.player1Controls.setDisable(false);
                SimpView.player2Controls.setDisable(true);
            }


        });

        // Mouse graphics
        SimpView.gamePane.setOnMouseMoved(actionEvent -> {
            if (SimpModel.player1Turn) {
                // player1
                // Draw the previous throw and the mouse graphics on top
                SimpModel.drawGame(SimpView.gc);

                if (!SimpView.angle2Input.getText().isEmpty() && !SimpView.velocity2Input.getText().isEmpty()) {
                    int angle = Integer.parseInt(SimpView.angle2Input.getText());
                    int velocity = Integer.parseInt(SimpView.velocity2Input.getText());

                    SimpModel.player2.throwBanana(SimpView.gc, angle, velocity);
                }

                SimpView.gc.setStroke(Color.rgb(200,0,0));
                SimpView.gc.strokeLine(SimpModel.player1.centerX, SimpModel.player1.y,
                                       actionEvent.getX(), actionEvent.getY());

                // Update the text fields to show current ang and vel
                int angle = -1 * SimpModel.calcAngle(SimpModel.player1.centerX, SimpModel.player1.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                int velocity = SimpModel.calcDist(SimpModel.player1.centerX, SimpModel.player1.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());

                SimpView.angle1Input.setText(String.valueOf(angle));
                SimpView.velocity1Input.setText(String.valueOf(velocity));

            } else {
                // player2
                // Draw the previous throw and the mouse graphics on top
                SimpModel.drawGame(SimpView.gc);

                if (!SimpView.angle1Input.getText().isEmpty() && !SimpView.velocity1Input.getText().isEmpty()) {
                    int angle = Integer.parseInt(SimpView.angle1Input.getText());
                    int velocity = Integer.parseInt(SimpView.velocity1Input.getText());

                    SimpModel.player1.throwBanana(SimpView.gc, angle, velocity);
                }

                SimpView.gc.setStroke(Color.RED);
                SimpView.gc.strokeLine(SimpModel.player2.centerX,SimpModel.player2.y,
                        actionEvent.getX(),actionEvent.getY());

                // Update the text fields to show current ang and vel
                int angle = SimpModel.calcAngle(SimpModel.player2.centerX, SimpModel.player2.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                int velocity = SimpModel.calcDist(SimpModel.player2.centerX, SimpModel.player2.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());

                SimpView.angle2Input.setText(String.valueOf(angle));
                SimpView.velocity2Input.setText(String.valueOf(velocity));
            }
        });

        // Mouse control
        SimpView.gamePane.setOnMousePressed(actionEvent -> {
            if (SimpModel.player1Turn) {
                // player1
                // Clear map
                SimpModel.drawGame(SimpView.gc);

                // Get input based on mouse pos and throw the banana
                int angle = -1 * SimpModel.calcAngle(SimpModel.player1.centerX, SimpModel.player1.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                int velocity = SimpModel.calcDist(SimpModel.player1.centerX, SimpModel.player1.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());

                SimpView.angle1Input.setText(String.valueOf(angle));
                SimpView.velocity1Input.setText(String.valueOf(velocity));

                int lands = SimpModel.player1.throwBanana(SimpView.gc, angle, velocity);

                // Check for hit and inc score if hit
                if (SimpModel.player2.isHit(lands, SimpModel.hitZone)) {
                    SimpModel.player1.score++;
                    SimpView.player1Label.setText("Player 1 - Score: " + SimpModel.player1.score);
                } else if (SimpModel.player1.isHit(lands, SimpModel.hitZone)) {
                    SimpModel.player2.score++;
                    SimpView.player2Label.setText("Player 2 - Score: " + SimpModel.player2.score);
                }

                // Change turn
                SimpModel.player1Turn = false;
                SimpView.player1Controls.setDisable(true);
                SimpView.player2Controls.setDisable(false);
            } else {
                // player2
                // Clear map
                SimpModel.drawGame(SimpView.gc);

                // Get input based on mouse pos and throw the banana
                int angle = SimpModel.calcAngle(SimpModel.player2.centerX, SimpModel.player2.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                int velocity = SimpModel.calcDist(SimpModel.player2.centerX, SimpModel.player2.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());

                SimpView.angle2Input.setText(String.valueOf(angle));
                SimpView.velocity2Input.setText(String.valueOf(velocity));

                int lands = SimpModel.player2.throwBanana(SimpView.gc, angle, velocity);

                // Check for hit and inc score if hit
                if (SimpModel.player1.isHit(lands, SimpModel.hitZone)) {
                    SimpModel.player2.score++;
                    SimpView.player2Label.setText("Player 2 - Score: " + SimpModel.player2.score);
                } else if (SimpModel.player2.isHit(lands, SimpModel.hitZone)) {
                    SimpModel.player1.score++;
                    SimpView.player1Label.setText("Player 1 - Score: " + SimpModel.player1.score);
                }

                // Change turn
                SimpModel.player1Turn = true;
                SimpView.player1Controls.setDisable(false);
                SimpView.player2Controls.setDisable(true);
            }
        });
    }
}
