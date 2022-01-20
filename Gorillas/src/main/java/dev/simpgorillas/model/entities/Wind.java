package dev.simpgorillas.model.entities;

public class Wind {
    public int isWind;
    public double windValue = 22;
    public String arrowDir = "-";



    public void setWind() {
        if (isWind == 1){
            windValue = (int)(Math.random() * (200) - 100) / 100.0;
        }else{
            windValue = 0;
        }
        System.out.println(windValue + "\n");
    }
    public void setArrowDir(){
        if (windValue > 0){
            arrowDir = "->";
        } else if (windValue < 0){
            arrowDir = "<-";
        } else if (windValue == 0){
            arrowDir = "0";
        }
    }
    
}
