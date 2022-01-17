package dev.simpgorillas;

import dev.simpgorillas.control.SimpController;
import dev.simpgorillas.model.SimpModel;
import dev.simpgorillas.view.SimpView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class SimpLauncher extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        /*SimpModel model = new SimpModel();

        SimpView view = new SimpView();

        SimpController controller = new SimpController(model, view, stage);*/


        SimpView.setStartScene();
        SimpController.setStartControls(stage);

        stage.setResizable(false);
        stage.setTitle("SimpLauncher");
        stage.setScene(SimpView.startScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
