package dev.simpgorillas.model.entities;

public class Wind {
    public int isWind;
    public double windValue = 22;



    public void setWind() {
        if (isWind == 1){
            windValue = (int)(Math.random() * (200) - 100) / 100.0;
        }else{
            windValue = 0;
        }
    }
}
