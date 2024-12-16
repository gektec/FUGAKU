module com.fugaku.platformer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires javafx.media;


    opens com.fugaku.platformer to javafx.fxml;
    exports com.fugaku.platformer;
    exports com.fugaku.platformer.texture;
    opens com.fugaku.platformer.texture to javafx.fxml;
    exports com.fugaku.platformer.controller;
    opens com.fugaku.platformer.controller to javafx.fxml;
    exports com.fugaku.platformer.entities;
    opens com.fugaku.platformer.entities to javafx.fxml;
    exports com.fugaku.platformer.move;
    opens com.fugaku.platformer.move to javafx.fxml;
    exports com.fugaku.platformer.move.command;
    opens com.fugaku.platformer.move.command to javafx.fxml;
    exports com.fugaku.platformer.entities.tile;
    opens com.fugaku.platformer.entities.tile to javafx.fxml;
    exports com.fugaku.platformer.entities.moveable;
    opens com.fugaku.platformer.entities.moveable to javafx.fxml;
    exports com.fugaku.platformer.move.state;
    opens com.fugaku.platformer.move.state to javafx.fxml;
    exports com.fugaku.platformer.data;
    opens com.fugaku.platformer.data to javafx.fxml;
    exports com.fugaku.platformer.model;
    opens com.fugaku.platformer.model to javafx.fxml;
}