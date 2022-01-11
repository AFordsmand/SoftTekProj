package com.simpgorillas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class SimpGorillasView extends Application {

    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 600;

    private static Scanner scanner = new Scanner(System.in);
    private SimpGorillasModel model;
    private GraphicsContext gc;
    private Canvas canvas;



    public static void main(String[] args) {
        System.out.println("Choose width and height of the game.");
        System.out.print("Width: ");
        SCREEN_WIDTH = scanner.nextInt();
        System.out.print("Height: ");
        SCREEN_HEIGHT = scanner.nextInt();
        System.out.println("The game has launched.");

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        model = new SimpGorillasModel();
        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new StackPane(canvas), Color.rgb(45, 118, 207));
        stage.setTitle("SimpGorillas");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }



    public void update() {
    }
}
