package dev.simpgorillas.view;

import dev.simpgorillas.model.SimpModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SimpView {

    // Fields for StartScene
    public static Scene startScene;
    public static Label widthAndHeightText;
    public static Label widthLabel;
    public static Label heightLabel;
    public static Label fileLabel;
    public static TextField widthInput, heightInput;
    public static Button playButton;
    public static Button fileButton;
    public static HBox widthNode;
    public static HBox heightNode;
    public static VBox startPane;

    // Fields for GameScene
    public static Scene gameScene;
    public static Canvas canvas;
    public static Group gamePane;
    public static GraphicsContext gc;
    public static VBox player1Controls, player2Controls;
    public static Button throwBtn1, throwBtn2;
    public static HBox angle1Node, angle2Node, velocity1Node, velocity2Node;
    public static Label player2Label, player1Label, angle1Label, velocity1Label, angle2Label, velocity2Label;
    public static TextField angle1Input, angle2Input, velocity1Input, velocity2Input;

    // Fields for EndScene
    public static Scene endScene;
    public static VBox endPane;
    public static Label winLabel;
    public static Button saveButton;
    public static Button replayButton;


    public static void setStartScene() {
        widthAndHeightText = new Label("Set the width and height of the game to start playing SimpGorillas!");
        widthAndHeightText.setWrapText(true);
        widthAndHeightText.setFont(Font.font(14));
        widthAndHeightText.setTextAlignment(TextAlignment.CENTER);
        widthLabel = new Label("Width : ");
        heightLabel = new Label("Height : ");

        widthInput = new TextField();
        widthInput.setPromptText("input width");
        widthInput.setMaxWidth(100);
        heightInput = new TextField();
        heightInput.setPromptText("input height");
        heightInput.setMaxWidth(100);
        playButton = new Button("Play");
        fileButton = new Button("Replay a Game");

        widthNode = new HBox(widthLabel, widthInput);
        widthNode.setSpacing(5);
        widthNode.setAlignment(Pos.CENTER);
        heightNode = new HBox(heightLabel, heightInput);
        heightNode.setSpacing(5);
        heightNode.setAlignment(Pos.CENTER);

        startPane = new VBox();
        startPane.setSpacing(20);
        startPane.setAlignment(Pos.CENTER);
        startPane.setPadding(new Insets(10));
        startPane.getChildren().addAll(widthAndHeightText, widthNode, heightNode, playButton, fileButton);

        startScene = new Scene(startPane, 300, 250);
    }

    public static void setGameScene() {
        gamePane = new Group();
        canvas = new Canvas(SimpModel.gameWidth, SimpModel.gameHeight);
        gc = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);

        player1Controls = new VBox();
        player1Label = new Label("Player 1 - Score: " + SimpModel.player1.score);
        angle1Node = new HBox();
        angle1Label = new Label("Angle: ");
        angle1Input = new TextField();
        angle1Input.setMaxWidth(75);
        angle1Node.getChildren().addAll(angle1Label, angle1Input);
        angle1Node.setSpacing(11);
        velocity1Node = new HBox();
        velocity1Label = new Label("Velocity: ");
        velocity1Input = new TextField();
        velocity1Input.setMaxWidth(75);
        velocity1Node.getChildren().addAll(velocity1Label, velocity1Input);
        throwBtn1 = new Button("Throw!");
        player1Controls.getChildren().addAll(player1Label, angle1Node, velocity1Node, throwBtn1);
        player1Controls.setPadding(new Insets(5));
        player1Controls.setSpacing(5);

        player2Controls = new VBox();
        player2Label = new Label("Player 2 - Score: " + SimpModel.player2.score);
        angle2Node = new HBox();
        angle2Label = new Label("Angle: ");
        angle2Input = new TextField();
        angle2Input.setMaxWidth(75);
        angle2Node.getChildren().addAll(angle2Label, angle2Input);
        angle2Node.setSpacing(11);
        velocity2Node = new HBox();
        velocity2Label = new Label("Velocity: ");
        velocity2Input = new TextField();
        velocity2Input.setMaxWidth(75);
        velocity2Node.getChildren().addAll(velocity2Label, velocity2Input);
        throwBtn2 = new Button("Throw!");
        player2Controls.getChildren().addAll(player2Label, angle2Node, velocity2Node, throwBtn2);
        player2Controls.setPadding(new Insets(5));
        player2Controls.setSpacing(5);


        player1Controls.setLayoutX(0);
        player1Controls.setLayoutY(0);

        player2Controls.setLayoutX(SimpModel.gameWidth - 135);
        player2Controls.setLayoutY(0);
        player2Controls.setDisable(true);

        gamePane.getChildren().addAll(player1Controls, player2Controls);

        // Går det i controls
        /*if (SimpModel.player1Turn) {
            player2Controls.setDisable(true);
        } else {
            player1Controls.setDisable(true);
        }*/

        SimpModel.drawGame(gc);

        gameScene = new Scene(gamePane);
    }

    public static void setEndScene() {
        winLabel = new Label("Player " + SimpModel.playerWin + " Won!");
        winLabel.setWrapText(true);
        winLabel.setFont(Font.font(35));
        winLabel.setAlignment(Pos.CENTER);

        replayButton = new Button("Play Again");
        saveButton = new Button("Save Round");

        endPane = new VBox();
        endPane.setSpacing(20);
        endPane.setAlignment(Pos.CENTER);
        endPane.setPadding(new Insets(10));
        endPane.getChildren().addAll(winLabel, replayButton, saveButton);

        endScene = new Scene(endPane, 300, 250);
    }

}
