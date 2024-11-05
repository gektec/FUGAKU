module com.example.platformerplain {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.platformerplain to javafx.fxml;
    exports com.example.platformerplain;
}