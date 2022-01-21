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
    
    public void updateWind(){
        model.wind.setWind();
        view.windLabel.setText(Math.abs(model.wind.windValue) + " pixels pr. second");
        model.wind.setArrowDir();
        view.arrowLabel.setText(model.wind.arrowDir);
    }

    // ========================================
    // Start controls, made by William Steffens
    // ========================================
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
            boolean legalGrav = view.gravInput.getText().isBlank() || model.isPosDouble(view.gravInput.getText());
            boolean legalWind = model.legalWind(view.windInput.getText());
            boolean legalPoints = view.pointInput.getText().isBlank() || model.isPosInt(view.pointInput.getText());

            boolean startCond = legalWidth && legalHeight && legalGrav && legalWind && legalPoints;

            if (startCond) {
                // Sets the width and height of the model
                model.gameWidth = Integer.parseInt(view.widthInput.getText());
                model.gameHeight = Integer.parseInt(view.heightInput.getText());
                model.init();

                // Set banana's gravity
                if (!view.gravInput.getText().isBlank()) {
                    Banana.grav = Double.parseDouble(view.gravInput.getText());
                }

                // Set points to win
                if (!view.pointInput.getText().isBlank()) {
                    model.winScoreCondition = Integer.parseInt(view.pointInput.getText());
                }


                view.setGameScene();
                model.drawGame(view.gc);
                setGameControls();

                model.wind.isWind = Integer.parseInt(view.windInput.getText());
                model.wind.setWind();
                view.windLabel.setText(model.wind.windValue + " pixels pr. second");

                // Wind
                model.wind.isWind = Integer.parseInt(view.windInput.getText());
                updateWind();

                // Set up gameLog
                model.gameLog = 
                    model.gameWidth + " " +
                    model.gameHeight + " " + 
                    Banana.grav + " " + 
                    model.winScoreCondition + " " +
                    model.wind.isWind;

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
                // Grav input error effect
                if (!legalGrav) {
                    view.gravInput.setBorder(view.redBorder);
                } else {
                    view.gravInput.setBorder(view.greenBorder);
                }
                // Wind input error effect
                if (!legalWind) {
                    view.windInput.setBorder(view.redBorder);
                } else {
                    view.windInput.setBorder(view.greenBorder);
                }
                // Point input error effect
                if (!legalPoints) {
                    view.pointInput.setBorder(view.redBorder);
                } else {
                    view.pointInput.setBorder(view.greenBorder);
                }
            }
        });

        // ===================================
        // Replay function by Adam Fordsmand
        // ===================================
        view.fileButton.setOnAction(actionEvent -> {

            // Get a File to read
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);

            // Stop timer
            if (model.timeline != null) {
                model.timeline.stop();
            }
            
            // If a file was chosen
            if (file != null) {
                try {
                    Scanner fileReader = new Scanner(file);

                    // Get gameWidth and gameHeight from the first line in the file and open the game
                    if (fileReader.hasNextLine()) {
                        String input = fileReader.nextLine();
                        String[] args = input.split(" ");
                        model.gameWidth = Integer.parseInt(args[0]);
                        model.gameHeight = Integer.parseInt(args[1]);
                        Banana.grav = Double.parseDouble(args[2]);
                        model.winScoreCondition = Integer.parseInt(args[3]);
                        model.init();
                        model.wind.isWind = Integer.parseInt(args[4]);
                        view.setGameScene();

                    }

                    // Set up gameLog
                    model.gameLog = 
                        model.gameWidth + " " +
                        model.gameHeight + " " + 
                        Banana.grav + " " + 
                        model.winScoreCondition + " " +
                        model.wind.isWind;

                    // Set up Game
                    stage.setTitle("SimpGorillas!");
                    stage.setScene(view.gameScene);
                    stage.centerOnScreen();
                    model.drawGame(view.gc);
                    view.player1Controls.setDisable(true);
                    view.player2Controls.setDisable(true);

                    // Save the fileReader in the model for later
                    model.replayer = fileReader;
                   
                    // Define a new animation
                    Timeline replay = new Timeline();
                    replay.setCycleCount(Timeline.INDEFINITE);
                   
                    // Add a Shoot event to the animation as a frame
                    replay.getKeyFrames().add(
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
                                            double Wind = Double.parseDouble(input.split(" ")[2]);
                                            
                                            // Shoot
                                            Shoot(Angle, Velocity, Wind);
                                            model.replayer = fileReader;
                                        }
                                        else {
                                            // If there are no more lines in file, 
                                            // Stop timeline and restore player control
                                            replay.stop();
                                            view.player1Controls.setDisable(!model.player1Turn);
                                            view.player2Controls.setDisable(model.player1Turn);
                                            // Wind
                                            model.wind.setWind();
                                            view.windLabel.setText(model.wind.windValue + " pixels pr. second");
                                            updateWind();

                                            setGameControls();
                                        }
                                    }
                                }
                        )
                    );

                    // Play the animation
                    replay.play();

                } catch(Exception e){
                    //e.printStackTrace();
                }
            }
        });
    }

    // ==========================================
    // Game controls, made by William Steffens
    // ==========================================
    public void setGameControls() {
        model.timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    model.secondsPassed++;
                    view.timer.setText(model.formatTime());
        }));
        model.timeline.setCycleCount(Timeline.INDEFINITE);
        model.timeline.play();

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

        view.gamePane.setOnMouseMoved(actionEvent -> {
            if (model.player1Turn) {
                // player1
                // Draw the previous throw and the mouse graphics on top
                model.drawGame(view.gc);

                if (!view.angle2Input.getText().isEmpty() && !view.velocity2Input.getText().isEmpty()) {
                    int angle = Integer.parseInt(view.angle2Input.getText());
                    int velocity = Integer.parseInt(view.velocity2Input.getText());

                    model.player2.throwBanana(view.gc, angle, velocity, model.gameHeight, model.wind.windValue);
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

                    model.player1.throwBanana(view.gc, angle, velocity, model.gameHeight, model.wind.windValue);
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

                Shoot(angle, velocity);

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

                Shoot(angle, velocity);

                // Change turn
                view.player1Controls.setDisable(false);
                view.player2Controls.setDisable(true);
            }
        });
    }

    // ======================================
    // End controls, made by Adam Fordsmand
    // ======================================
    public void setEndControls() {
        // Stop timer
        if (model.timeline != null) {
            model.timeline.stop();
        }

        // PLay Again
        view.replayButton.setOnAction(actionEvent -> {
            // Reset variables
            model.playerWin = 0;
            model.player1.score = 0;
            model.player2.score = 0;

            // Go to start menu
            view.setStartScene();
            setStartControls();
            stage.setTitle("SimpLauncher");
            stage.setScene(view.startScene);
            stage.centerOnScreen();
        });

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

    // ==================================================
    // Shoot Method essential to all features therefore,
    // Made by everyone combined.
    // ==================================================
    public void Shoot(int Angle, int Velocity, double... Wind) {
        double wind;

        if (Wind.length > 0) { 
            wind = Wind[0]; 
            model.wind.windValue = wind;
            view.windLabel.setText(Math.abs(model.wind.windValue) + " pixels pr. second");
            model.wind.setArrowDir();
            view.arrowLabel.setText(model.wind.arrowDir);
        }
        else { 
            wind = model.wind.windValue; 
        }

        // Save shot in the gameLog
        model.gameLog = model.gameLog.concat("\n" + Angle + " " + Velocity + " " + wind);

        // Clear map
        model.drawGame(view.gc);

        // Setup Parameters
        boolean Player1Turn = model.player1Turn;
        int lands;

        // Throw
        if (Player1Turn) {
            lands = model.player1.throwBanana(view.gc, Angle, Velocity, model.gameHeight, wind);
        }
        else {
            lands = model.player2.throwBanana(view.gc, Angle, Velocity, model.gameHeight, wind);
        }

        // Check for hit
        if (model.player2.isHit(lands, model.hitZone)) {
            model.player1.score++;
            view.player1Label.setText("Player 1 - Score: " + model.player1.score);
            if (Wind.length == 0) {
                updateWind();
            }
        } else if (model.player1.isHit(lands, model.hitZone)) {
            model.player2.score++;
            view.player2Label.setText("Player 2 - Score: " + model.player2.score);
            if (Wind.length == 0) {
                updateWind();
            }
        }

        // Change turn
        model.player1Turn = !Player1Turn;

        // Win Condition
        if (model.player1.score >= model.winScoreCondition) {
            model.playerWin = 1;

            view.setEndScene();
            setEndControls();
            model.player1Turn = true;

            stage.setTitle("SimpLauncher");
            stage.setScene(view.endScene);
            stage.centerOnScreen();
        }
        else if (model.player2.score >= model.winScoreCondition) {
            model.playerWin = 2;

            view.setEndScene();
            setEndControls();
            model.player1Turn = true;

            stage.setTitle("SimpLauncher");
            stage.setScene(view.endScene);
            stage.centerOnScreen();
        }
    }
}
