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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class SimpView {

    // Fields for StartScene
    public Scene startScene;
    public Label widthAndHeightText;
    public Label widthLabel;
    public Label heightLabel;
    public TextField widthInput, heightInput, windInput;
    public Label windText;
    public Label windOpText;
    public Button playButton;
    public Button fileButton;
    public HBox widthNode;
    public HBox heightNode;
    public HBox windNode;
    public VBox startPane;

    // Fields for GameScene
    public Scene gameScene;
    public Canvas canvas;
    public Group gamePane;
    public GraphicsContext gc;
    public VBox player1Controls, player2Controls, windControls;
    public Button throwBtn1, throwBtn2;
    public HBox angle1Node, angle2Node, velocity1Node, velocity2Node;
    public Label player2Label, player1Label, angle1Label, velocity1Label, angle2Label, velocity2Label, windLabel;
    public TextField angle1Input, angle2Input, velocity1Input, velocity2Input;
    public Label timer;

    // Fields for EndScene
    public Scene endScene;
    public VBox endPane;
    public Label winLabel;
    public Button saveButton;
    public Button replayButton;

    public SimpModel model;

    public SimpView(SimpModel model) {
        this.model = model;
    }


    public void setStartScene() {
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

        windText = new Label("Would you like to play with wind?");
        windOpText = new Label("1 = Yes & 0 = No");
        windInput = new TextField();
        windInput.setPromptText("input 1 or 0");
        windInput.setMaxWidth(100);

        playButton = new Button("Play");
        fileButton = new Button("Replay a Game");

        widthNode = new HBox(widthLabel, widthInput);
        widthNode.setSpacing(5);
        widthNode.setAlignment(Pos.CENTER);
        heightNode = new HBox(heightLabel, heightInput);
        heightNode.setSpacing(5);
        heightNode.setAlignment(Pos.CENTER);
        windNode = new HBox(windInput);
        windNode.setSpacing(5);
        windNode.setAlignment(Pos.CENTER);

        startPane = new VBox();
        startPane.setSpacing(20);
        startPane.setAlignment(Pos.CENTER);
        startPane.setPadding(new Insets(10));
        startPane.getChildren().addAll(widthAndHeightText, widthNode, heightNode, windText, windOpText, windNode, playButton, fileButton);

        startScene = new Scene(startPane, 300, 350);
    }

    public void setGameScene() {
        gamePane = new Group();
        canvas = new Canvas(model.gameWidth, model.gameHeight);
        gc = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);

        player1Controls = new VBox();
        player1Label = new Label("Player 1 - Score: " + 0);
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
        player2Label = new Label("Player 2 - Score: " + 0);
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

        windControls = new VBox();
        windLabel = new Label(model.wind.windValue + " pixels pr. second");
        windControls.getChildren().add(windLabel);
        windControls.setPadding(new Insets(5));
        windControls.setSpacing(5);

        player1Controls.setLayoutX(0);
        player1Controls.setLayoutY(0);

        player2Controls.setLayoutX(model.gameWidth - 135);
        player2Controls.setLayoutY(0);
        player2Controls.setDisable(true);

        windControls.setLayoutX(model.gameWidth - (model.gameWidth/2)-70);
        windControls.setLayoutY(25);

        // Timer
        timer = new Label("00:00");
        timer.setFont(Font.font(16));
        timer.setLayoutX(model.gameWidth / 2f - 32);

        gamePane.getChildren().addAll(player1Controls, windControls, player2Controls, timer);

        gameScene = new Scene(gamePane);
    }

    public void setEndScene() {
        winLabel = new Label("Player " + model.playerWin + " won!");
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
