package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import com.example.platformerplain.Screen.MenuScreen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class PauseScreenController {

    @FXML
    private GridPane root;  // VBox in the FXML file

    @FXML
    private Button MenuButton;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer; // add MediaPlayer

    // Set the primaryStage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Initialize method for loading the background image and playing the fail sound
    @FXML
    private void initialize() throws URISyntaxException {
        // Load background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/backgrounds/Pause.png"));

        // Create a BackgroundImage object, ensuring the image adapts proportionally to the VBox size
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,  // Do not repeat the image
                BackgroundRepeat.NO_REPEAT,  // Do not repeat the image
                BackgroundPosition.CENTER,    // Center the image
                new BackgroundSize(200, 150,  // Set custom width and height
                        false,   // Do not stretch the width (set to true if you want to stretch)
                        false,   // Do not stretch the height (set to true if you want to stretch)
                        true,    // Preserve aspect ratio for the width
                        true));  // Preserve aspect ratio for the height


        // Set the VBox background
        root.setBackground(new Background(background));
    }

    // Handle the start game button click event
    @FXML
    void handleExitGame() {
        // Call Platform.exit() to close the application
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleMenu() {
        // Create a StartScreen instance and pass the main stage
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.show(primaryStage);  // deliver currentStage

    }

    @FXML
    private void handleResume() {
        // Create a StartScreen instance and pass the main stage
        Main main = Main.getInstance();
        if (main != null) {
            main.resumeGame();

        }
    }


}
