module dev.simpgorillas {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.simpgorillas to javafx.fxml;
    exports dev.simpgorillas;
}