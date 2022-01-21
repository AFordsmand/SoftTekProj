package dev.simpgorillas.model.entities;

// ===================================
// Wind function by Caroline Blixt
// ===================================

public class Wind {
    public int isWind;
    public double windValue;
    public String arrowDir = "-";


    // Check if the user has chosen to play with wind
    public void setWind() {
        if (isWind == 1){
            windValue = (int)(Math.random() * (200) - 100) / 100.0;
        }else{
            windValue = 0;
        }
    }
    
    // Making arrow depending on direction of wind
    public void setArrowDir(){
        if (windValue > 0){
            arrowDir = "->";
        } else if (windValue < 0){
            arrowDir = "<-";
        } else if (windValue == 0){
            arrowDir = "-";
        }
    }
    
}
