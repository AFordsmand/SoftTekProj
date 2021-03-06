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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

public class SimpView {

    // Fields for StartScene
    public Scene startScene;
    public Label widthAndHeightText;
    public Label widthLabel, heightLabel;
    public TextField widthInput, heightInput;
    public Button playButton;
    public HBox widthNode, heightNode;
    public VBox startPane;

    // Fields for GameScene
    public Scene gameScene;
    public Canvas canvas;
    public Group gamePane;
    public GraphicsContext gc;
    public VBox player1Controls, player2Controls;
    public Button throwBtn1, throwBtn2;
    public HBox angle1Node, angle2Node, velocity1Node, velocity2Node;
    public Label player2Label, player1Label, angle1Label, velocity1Label, angle2Label, velocity2Label;
    public TextField angle1Input, angle2Input, velocity1Input, velocity2Input;
    public Border greenBorder = new Border(new BorderStroke(
            Color.GREEN,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT));
    public Border redBorder = new Border(new BorderStroke(
            Color.RED,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT));
    public Border normalBorder = new Border(new BorderStroke(
            Color.GRAY,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT));

    public SimpModel model;

    public SimpView(SimpModel model) {
        this.model = model;
    }


    public void setStartScene() {
        widthAndHeightText = new Label("Set the parameters of the game to start playing SimpGorillas! \n " +
                "(width must be between 500 and " + (int) Screen.getPrimary().getVisualBounds().getWidth()
                + " and height must be between 400 and " + (int) Screen.getPrimary().getVisualBounds().getHeight() + ")");
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
        startPane.getChildren().addAll(
                widthAndHeightText,
                widthNode,
                heightNode,
                playButton);

        startScene = new Scene(startPane, 350, 300);
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
        angle1Input.setBorder(normalBorder);
        angle1Input.setMaxWidth(75);
        angle1Node.getChildren().addAll(angle1Label, angle1Input);
        angle1Node.setSpacing(11);
        velocity1Node = new HBox();
        velocity1Label = new Label("Velocity: ");
        velocity1Input = new TextField();
        velocity1Input.setBorder(normalBorder);
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
        angle2Input.setBorder(normalBorder);
        angle2Input.setMaxWidth(75);
        angle2Node.getChildren().addAll(angle2Label, angle2Input);
        angle2Node.setSpacing(11);
        velocity2Node = new HBox();
        velocity2Label = new Label("Velocity: ");
        velocity2Input = new TextField();
        velocity2Input.setBorder(normalBorder);
        velocity2Input.setMaxWidth(75);
        velocity2Node.getChildren().addAll(velocity2Label, velocity2Input);
        throwBtn2 = new Button("Throw!");
        player2Controls.getChildren().addAll(player2Label, angle2Node, velocity2Node, throwBtn2);
        player2Controls.setPadding(new Insets(5));
        player2Controls.setSpacing(5);

        player1Controls.setLayoutX(0);
        player1Controls.setLayoutY(0);

        player2Controls.setLayoutX(model.gameWidth - 135);
        player2Controls.setLayoutY(0);
        player2Controls.setDisable(true);

        gamePane.getChildren().addAll(player1Controls, player2Controls);

        gameScene = new Scene(gamePane);
    }
}
