package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class StartScreenController {

    @FXML
    private VBox root;  // VBox in the FXML file

    @FXML
    private Button startButton;

    private Stage primaryStage;

    // Set the primaryStage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Initialize method for loading the background image
    @FXML
    private void initialize() {
        // Load background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/background.png"));

        // Create a BackgroundImage object, ensuring the image adapts proportionally to the VBox size
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,  // Do not repeat the image
                BackgroundRepeat.NO_REPEAT,  // Do not repeat the image
                BackgroundPosition.CENTER,   // Center the image
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, false)); // Adaptive size

        // Set the VBox background
        root.setBackground(new Background(background));
    }

    // Handle the start game button click event
    @FXML
    void handleStartGame() {
        // Call the startGame method of the Main class, passing the primaryStage
        Main.getInstance().startGame(primaryStage);
    }
}
