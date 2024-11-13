package com.example.platformerplain;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StartScreenController {

    @FXML
    private Button startButton;

    @FXML
    void handleStartGame() {
        Main.getInstance().startGame(); // Call the method to start the game
    }
}
