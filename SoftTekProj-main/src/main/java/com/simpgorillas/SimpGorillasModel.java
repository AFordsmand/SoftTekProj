package com.simpgorillas;

import javafx.scene.canvas.GraphicsContext;

public class SimpGorillasModel {

    private int scoreP1 = 0;
    private int scoreP2 = 0;

    private SimpGorillasView view;
    private Gorilla player1;
    private Gorilla player2;

    public SimpGorillasModel() {
    }

    public void setup(GraphicsContext gc) {
        player1.draw(gc);
        player2.draw(gc);
    }

    public void throwBanana(Gorilla gorilla) {





        view.update();
    }










}
