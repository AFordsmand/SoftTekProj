module dev.simpgorillas {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.simpgorillas to javafx.fxml;
    exports dev.simpgorillas;
    exports dev.simpgorillas.view;
    opens dev.simpgorillas.view to javafx.fxml;
    exports dev.simpgorillas.control;
    opens dev.simpgorillas.control to javafx.fxml;
    exports dev.simpgorillas.model;
    opens dev.simpgorillas.model to javafx.fxml;
    exports dev.simpgorillas.model.entities;
    opens dev.simpgorillas.model.entities to javafx.fxml;
}