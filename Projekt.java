import java.util.Timer; 
import java.util.TimerTask; 


public class Projekt {
    public static void main(String[] args) {
        

        



        // Start Physics engine
        Timer timer = new Timer();
        TimerTask task = new Physics();
        timer.schedule(task, 0, 1);

    }
}


// Physics engine
class Physics extends TimerTask {
    public static int i = 0;
    
    public void run(){

        // TODO: Calculate physics

        System.out.print("Test");
    }
}
