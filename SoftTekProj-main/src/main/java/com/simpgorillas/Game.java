package com.simpgorillas;

public class Game {

    GameState player1Turn;
    GameState player2Turn;

    GameState currentState;

    public Game() {
        player1Turn = new Player1Turn(this);
        player2Turn = new Player2Turn(this);

        currentState = player1Turn;
    }

    void setGameState(GameState newGameState) {

    }
}
