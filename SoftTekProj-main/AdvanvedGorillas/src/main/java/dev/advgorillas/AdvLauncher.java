package dev.advgorillas;

import dev.advgorillas.control.AdvController;
import dev.advgorillas.model.AdvModel;
import dev.advgorillas.view.AdvView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

// ============================================
// AdvLauncher class, made by William Steffens
// ============================================
public class AdvLauncher extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AdvModel model = new AdvModel();
        AdvView view = new AdvView(model);
        new AdvController(model, view, stage);

        stage.setResizable(false);
        stage.setTitle("SimpLauncher");
        stage.setScene(view.startScene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
