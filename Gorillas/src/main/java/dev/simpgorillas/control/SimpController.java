package dev.simpgorillas.control;

import dev.simpgorillas.model.SimpModel;
import dev.simpgorillas.view.SimpView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;
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
        // Get width and height (if legal) from startScene and set the stage's scene to gameScene
        view.playButton.setOnAction(actionEvent -> {
            // Check widthInput and heightInput for legal values
            if (true) {
                // Sets the width and height of the model, and
                model.gameWidth = Integer.parseInt(view.widthInput.getText());
                model.gameHeight = Integer.parseInt(view.heightInput.getText());
                model.init();

                // Setup gameLog
                model.gameLog = model.gameWidth + " " + model.gameHeight;

                view.setGameScene(model.gameWidth, model.gameHeight);
                model.drawGame(view.gc);
                setGameControls();

                stage.setTitle("SimpGorillas!");
                stage.setScene(view.gameScene);
                stage.centerOnScreen();
            } else {
                // Make the view turn red or something
            }

        });

        // Load replay of a Game
        view.fileButton.setOnAction(actionEvent -> {

            // Get a File to read
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            
            // If a file was chosen
            if (file != null) {
                try {
                    Scanner fileReader = new Scanner(file);

                    // Get gameWidth and gameHeight from the first line in the file and open the game
                    if (fileReader.hasNextLine()) {
                        String input = fileReader.nextLine();
                        model.gameWidth = Integer.parseInt(input.split(" ")[0]);
                        model.gameHeight = Integer.parseInt(input.split(" ")[1]);
                        model.init();
                        view.setGameScene(Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1]));
                    }

                    // Setup the game, and disable controls.
                    model.gameLog = model.gameWidth + " " + model.gameHeight;
                    stage.setTitle("SimpGorillas!");
                    stage.setScene(view.gameScene);
                    stage.centerOnScreen();
                    model.drawGame(view.gc);
                    view.player1Controls.setDisable(true);
                    view.player2Controls.setDisable(true);

                    // Save the fileReader in the model for later
                    model.replayer = fileReader;
                   
                    // Define a new animation
                    Timeline timeline = new Timeline();
                    timeline.setCycleCount(Timeline.INDEFINITE);
                   
                    // Add a Shoot event to the animation as a frame
                    timeline.getKeyFrames().add(
                            new KeyFrame(
                                // interval between frames.
                                Duration.millis(1000), 

                                // Shoot event 
                                // NOTE: Had to be defined like this, otherwise it cause a unsecure warning
                                new EventHandler<ActionEvent>(){
                                    public void handle(ActionEvent t) {
                                        Scanner fileReader = model.replayer;
                                        if (fileReader.hasNextLine()) {
                                            // Get Line
                                            String input = fileReader.nextLine();

                                            // Get variables from file
                                            int Angle = Integer.parseInt(input.split(" ")[0]);
                                            int Velocity = Integer.parseInt(input.split(" ")[1]);
                                            int Wind = Integer.parseInt(input.split(" ")[2]);
                                            
                                            // Shoot
                                            Shoot(Angle, Velocity, Wind);
                                            model.replayer = fileReader;
                                        }
                                        else {
                                            // If there are no more lines in file, 
                                            // Stop timeline and restore player control
                                            timeline.stop();
                                            setGameControls();
                                            view.player1Controls.setDisable(!model.player1Turn);
                                            view.player2Controls.setDisable(model.player1Turn);
                                        }
                                    }
                                }
                        )
                    );

                    // Play the animation
                    timeline.play();

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void setGameControls() {

        view.throwBtn1.setOnAction(actionEvent -> {
            if (model.player1Turn) {
                // Check for legal values and throw banana
                int angle = Integer.parseInt(view.angle1Input.getText());
                int velocity = Integer.parseInt(view.velocity1Input.getText());
                long wind = 0;

                Shoot(angle, velocity, wind);

                // Change turn
                view.player1Controls.setDisable(true);
                view.player2Controls.setDisable(false);
            }
        });

        view.throwBtn2.setOnAction(actionEvent -> {
            if (!model.player1Turn) {
                // Check for legal values and throw banana
                int angle = Integer.parseInt(view.angle2Input.getText());
                int velocity = Integer.parseInt(view.velocity2Input.getText());
                long wind = 0;

                Shoot(angle, velocity, wind);

                view.player1Controls.setDisable(false);
                view.player2Controls.setDisable(true);
            }
        });

        // Mouse graphics
        view.gamePane.setOnMouseMoved(actionEvent -> {
            if (model.player1Turn) {
                // player1
                // Draw the previous throw and the mouse graphics on top
                model.drawGame(view.gc);

                if (!view.angle2Input.getText().isEmpty() && !view.velocity2Input.getText().isEmpty()) {
                    int angle = Integer.parseInt(view.angle2Input.getText());
                    int velocity = Integer.parseInt(view.velocity2Input.getText());

                    model.player2.throwBanana(view.gc, angle, velocity, model.gameHeight);
                }

                view.gc.setStroke(Color.rgb(200,0,0));
                view.gc.strokeLine(model.player1.centerX, model.player1.y,
                                       actionEvent.getX(), actionEvent.getY());

                // Update the text fields to show current ang and vel
                int angle = -1 * model.calcAngle(model.player1.centerX, model.player1.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                int velocity = model.calcDist(model.player1.centerX, model.player1.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());

                view.angle1Input.setText(String.valueOf(angle));
                view.velocity1Input.setText(String.valueOf(velocity));

            } else {
                // player2
                // Draw the previous throw and the mouse graphics on top
                model.drawGame(view.gc);

                if (!view.angle1Input.getText().isEmpty() && !view.velocity1Input.getText().isEmpty()) {
                    int angle = Integer.parseInt(view.angle1Input.getText());
                    int velocity = Integer.parseInt(view.velocity1Input.getText());

                    model.player1.throwBanana(view.gc, angle, velocity, model.gameHeight);
                }

                view.gc.setStroke(Color.RED);
                view.gc.strokeLine(model.player2.centerX,model.player2.y,
                        actionEvent.getX(),actionEvent.getY());

                // Update the text fields to show current ang and vel
                int angle = model.calcAngle(model.player2.centerX, model.player2.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                int velocity = model.calcDist(model.player2.centerX, model.player2.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());

                view.angle2Input.setText(String.valueOf(angle));
                view.velocity2Input.setText(String.valueOf(velocity));
            }
        });

        // Mouse control
        view.gamePane.setOnMousePressed(actionEvent -> {
            if (model.player1Turn) {
                // player1

                // Get input based on mouse pos and throw the banana
                int angle = -1 * model.calcAngle(model.player1.centerX, model.player1.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                int velocity = model.calcDist(model.player1.centerX, model.player1.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                long wind = 0;

                view.angle1Input.setText(String.valueOf(angle));
                view.velocity1Input.setText(String.valueOf(velocity));

                Shoot(angle, velocity, wind);

                // Change turn
                view.player1Controls.setDisable(true);
                view.player2Controls.setDisable(false);
            } else {
                // player2

                // Get input based on mouse pos and throw the banana
                int angle = model.calcAngle(model.player2.centerX, model.player2.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                int velocity = model.calcDist(model.player2.centerX, model.player2.y,
                        (int) actionEvent.getX(), (int) actionEvent.getY());
                long wind = 0;

                view.angle2Input.setText(String.valueOf(angle));
                view.velocity2Input.setText(String.valueOf(velocity));

                Shoot(angle, velocity, wind);

                // Change turn
                view.player1Controls.setDisable(false);
                view.player2Controls.setDisable(true);
            }
        });
    }

    public void setEndControls() {

        // PLay Again, got to start screen and reset variables
        view.replayButton.setOnAction(actionEvent -> {
            model.playerWin = 0;
            model.player1.score = 0;
            model.player2.score = 0;

            view.setStartScene();
            setStartControls();

            stage.setTitle("SimpLauncher");
            stage.setScene(view.startScene);
            stage.centerOnScreen();
        });

        // Save gameLog to file chosen using explorer
        view.saveButton.setOnAction(actionEvent -> {

            // Get save location
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(stage);

            // Save gameLog to location
            try {
                FileOutputStream fout = new FileOutputStream(file);
                fout.write(model.gameLog.getBytes());
                fout.close();
            } catch(Exception e){
                e.printStackTrace();
            }

        });
    }

    public void Shoot(int Angle, int Velocity, long Wind) {
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

        // Win Condition
        if (model.player1.score >= model.WinScoreCondition) {
            model.playerWin = 1;

            view.setEndScene(model.playerWin);
            setEndControls();
            model.player1Turn = true;

            stage.setTitle("SimpLauncher");
            stage.setScene(view.endScene);
            stage.centerOnScreen();
        }
        else if (model.player2.score >= model.WinScoreCondition) {
            model.playerWin = 2;

            view.setEndScene(model.playerWin);
            setEndControls();
            model.player1Turn = true;

            stage.setTitle("SimpLauncher");
            stage.setScene(view.endScene);
            stage.centerOnScreen();
        }

        // Save shot in the gameLog
        model.gameLog = model.gameLog.concat("\n" + Angle + " " + Velocity + " " + Wind);
    }
}
