module com.example.platformerplain {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires javafx.media;


    opens com.example.platformerplain to javafx.fxml;
    exports com.example.platformerplain;
    exports com.example.platformerplain.texture;
    opens com.example.platformerplain.texture to javafx.fxml;
    exports com.example.platformerplain.controller;
    opens com.example.platformerplain.controller to javafx.fxml;
    exports com.example.platformerplain.entities;
    opens com.example.platformerplain.entities to javafx.fxml;
    exports com.example.platformerplain.move;
    opens com.example.platformerplain.move to javafx.fxml;
    exports com.example.platformerplain.move.command;
    opens com.example.platformerplain.move.command to javafx.fxml;
    exports com.example.platformerplain.entities.tile;
    opens com.example.platformerplain.entities.tile to javafx.fxml;
    exports com.example.platformerplain.entities.moveable;
    opens com.example.platformerplain.entities.moveable to javafx.fxml;
    exports com.example.platformerplain.move.data;
    opens com.example.platformerplain.move.data to javafx.fxml;
    exports com.example.platformerplain.move.data.state;
    opens com.example.platformerplain.move.data.state to javafx.fxml;
}