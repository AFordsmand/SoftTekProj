package com.simpgorillas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Scanner;

enum State {player1turn, player2turn}

public class Main extends Application {
    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 600;
    Gorilla player1;
    Gorilla player2;
    int angle1, velocity1, angle2, velocity2;
    double lands;
    double hitZone = SCREEN_WIDTH/50.;

    Scanner sc = new Scanner(System.in);
    private GraphicsContext gc;
    private Canvas canvas;
    private Scene scene;
    public State state; 


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), e -> run()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();

        stage.setTitle("SimpGorillas!");
        stage.setResizable(false);
        setup();
        scene = new Scene(new StackPane(canvas), Color.rgb(45, 118, 207));
        stage.setScene(scene);
        stage.show();

        angle1 = 10;
        velocity1 = 100;
        run();
    }

    /*
    @Override
    public void init() {
        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        do {
            setup();

            System.out.println("score1: " + player1.score + " | " + "score2: " + player2.score);
            System.out.print("angle1: ");
            angle1 = sc.nextInt();
            System.out.print("velocity1: ");
            velocity1 = sc.nextInt();
            lands = player1.throwBanana(angle1, velocity1, gc);
            if (lands >= player2.xCenter - hitZone && lands <= player2.xCenter + hitZone) {
                System.out.println("hit");
                player1.score++;
            }
            System.out.println();

            System.out.println("score1: " + player1.score + " | " + "score2: " + player2.score);
            System.out.print("angle2: ");
            angle2 = sc.nextInt();
            System.out.print("velocity2: ");
            velocity2 = sc.nextInt();
            lands = player2.throwBanana(angle2, velocity2, gc);
            if (lands >= player1.xCenter - hitZone && lands <= player1.xCenter + hitZone) {
                System.out.println("hit");
                player2.score++;
            }
        } while (true);
    }
    */

    public void setup() {
        player1 = new Gorilla(0, SCREEN_HEIGHT - Gorilla.HEIGHT, 1);
        player2 = new Gorilla(SCREEN_WIDTH - Gorilla.WIDTH, SCREEN_HEIGHT - Gorilla.HEIGHT,-1);
        player1.draw(gc);
        player2.draw(gc);
    }


    public void run() {
        player1.throwBanana(angle1, velocity1, gc);
        angle1++;



        /*
        System.out.println("score1: " + player1.score + " | " + "score2: " + player2.score);
        System.out.print("angle1: ");
        angle1 = sc.nextInt();
        System.out.print("velocity1: ");
        velocity1 = sc.nextInt();
        lands = player1.throwBanana(angle1, velocity1, gc);
        if (lands >= player2.xCenter - hitZone && lands <= player2.xCenter + hitZone) {
            System.out.println("hit");
            player1.score++;
        }
        System.out.println();

        System.out.println("score1: " + player1.score + " | " + "score2: " + player2.score);
        System.out.print("angle2: ");
        angle2 = sc.nextInt();
        System.out.print("velocity2: ");
        velocity2 = sc.nextInt();
        lands = player2.throwBanana(angle2, velocity2, gc);
        if (lands >= player1.xCenter - hitZone && lands <= player1.xCenter + hitZone) {
            System.out.println("hit");
            player2.score++;
        }
        System.out.println();
        */

    }
}