import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.shape.Line; 
import javafx.stage.Stage;  
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;

public class init extends Application { 

    @Override 
    public void start(Stage stage) throws Exception{ 

        AnchorPane root = FXMLLoader.load(getClass().getResource("JavaFXTest.fxml"));
        Scene scene = new Scene(root, 727, 389);


        //Creating a line object 
        Line line = new Line(); 

        //Setting the properties to a line 
        line.setStartX(100.0); 
        line.setStartY(100.0); 
        line.setEndX(500.0); 
        line.setEndY(100.0); 

        root.getChildren().add(line);


        stage.setTitle("Januar Projekt");

        stage.setScene(scene);
        stage.show();


        //Creating a line object 
        //Line line = new Line(); 

        //Setting the properties to a line 
        //line.setStartX(100.0); 
        //line.setStartY(100.0); 
        //line.setEndX(500.0); 
        //line.setEndY(100.0); 


        ////Creating a Group 
        //Group root = new Group(line); 

        ////Creating a Scene 
        //Scene scene = new Scene(root, 600, 300); 

        ////Setting title to the scene 
        //stage.setTitle("Sample application"); 

        ////Adding the scene to the stage 
        //stage.setScene(scene); 

        ////Displaying the contents of a scene 
        //stage.show(); 
    }      

    public static void main(String[] args) {
        launch(args);
    }

}
