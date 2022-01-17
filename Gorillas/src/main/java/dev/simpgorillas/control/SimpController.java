package dev.simpgorillas.control;

import dev.simpgorillas.model.SimpModel;
import dev.simpgorillas.view.SimpView;
import javafx.event.Event;
import javafx.stage.Stage;

public class SimpController {

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
                // Clear map?
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
                // Clear map?
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
    }


}
