module com.example.serverlab03 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.serverlab03 to javafx.fxml;
    exports com.example.serverlab03;
}