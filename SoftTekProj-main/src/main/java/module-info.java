module com.simpgorillas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.simpgorillas to javafx.fxml;
    exports com.simpgorillas;
}