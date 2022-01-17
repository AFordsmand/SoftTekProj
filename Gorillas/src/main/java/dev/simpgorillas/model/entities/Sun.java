package dev.simpgorillas.model.entities;

import dev.simpgorillas.model.entities.Banana;

public class Sun {

    int x, y;
    boolean hit = false;

    public Sun() {

    }


    public void onHit(Banana banana) {
        if (banana.getX() == x && banana.getY() == y) {
            hit = true;
        }
    }
}
