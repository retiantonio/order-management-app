module com.example.ordermanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.ordermanagement to javafx.fxml;
    exports com.example.ordermanagement;
}