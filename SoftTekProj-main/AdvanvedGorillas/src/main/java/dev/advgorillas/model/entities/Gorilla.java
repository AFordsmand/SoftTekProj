package dev.advgorillas.model.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import dev.advgorillas.view.AdvView;

public class Gorilla {

    public int x, y, centerX, dir;
    public int score;
    public static final int WIDTH = 38;
    public static final int HEIGHT = 45;
    public Boolean facingRight;
    
    Image monkeynormal1 = new Image(getClass().getResourceAsStream("monkeynormal1.png")); 
    Image monkeynormal2 = new Image(getClass().getResourceAsStream("monkeynormal2.png")); 
    Image monkeythrow1 = new Image(getClass().getResourceAsStream("monkeythrow1.png")); 
    Image monkeythrow2 = new Image(getClass().getResourceAsStream("monkeythrow2.png")); 
    
    
    
    public Gorilla(int x, int y, boolean facingRight) {
        this.x = x;
        this.y = y;
        this.facingRight = facingRight;
        if (facingRight) {
            dir = 1;
        } else {
            dir = -1;
        }
        this.centerX = x + WIDTH / 2;
    }

    public void updatePlayerModel(GraphicsContext gc, Boolean playerTurn) {
    //update player models - true for player 1, false for player 2
    	
    	if(facingRight) {
    		if(playerTurn){
    		gc.drawImage(monkeythrow1,x,y);
    		} else {
    		gc.drawImage(monkeynormal1,x,y);
    		}
    	} else {
    		if(!playerTurn) {
    		gc.drawImage(monkeythrow2,x,y);
    		} else {
    		gc.drawImage(monkeynormal2,x,y);
    		}
        		
    	}
    	
    	
    	
    	
    }
    

    public int throwBanana(GraphicsContext gc, double angle, double velocity, int gameHeight, double wind) {
        Banana banana = new Banana(angle, velocity, dir, wind);

        double t = 0.01;
        while (true) {
            banana.update(x + WIDTH / 2, y, t);
            t += 0.001;

            // Check if the Banana is still in the air
            if (banana.getY() < gameHeight) {
                banana.render(gc);
                continue;
            }
            break;
        }

        return (int) banana.getX();
    }

    public boolean isHit(int lands, int hitZone) {
        if ((lands >= centerX - hitZone) && (centerX + hitZone >= lands)) {
            return true;
        }
        return false;
    }

}
