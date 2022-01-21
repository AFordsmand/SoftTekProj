module dev.advgorillas {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.advgorillas to javafx.fxml;
    exports dev.advgorillas;
}