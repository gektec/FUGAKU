module com.example.platformerplain {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.platformerplain to javafx.fxml;
    exports com.example.platformerplain;
    exports com.example.platformerplain.texture;
    opens com.example.platformerplain.texture to javafx.fxml;
    exports com.example.platformerplain.Controller;
    opens com.example.platformerplain.Controller to javafx.fxml;
}