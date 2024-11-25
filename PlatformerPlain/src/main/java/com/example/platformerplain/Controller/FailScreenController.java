package com.example.platformerplain.Controller;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URISyntaxException;

public class FailScreenController {

    @FXML
    private VBox root;  // VBox in the FXML file

    @FXML
    private Button MenuButton;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer; // 添加 MediaPlayer

    // Set the primaryStage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Initialize method for loading the background image and playing the fail sound
    @FXML
    private void initialize() throws URISyntaxException {
        // Load background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/defeat.png"));

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

        // Load the fail sound
        String failSoundFile = "/sounds/defeat.mp3"; // Make sure the path is correct
        Media failSound = new Media(getClass().getResource(failSoundFile).toURI().toString());
        mediaPlayer = new MediaPlayer(failSound);

        // 播放失败声音
        mediaPlayer.play(); // Play sound effects on initialization
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/start_screen.fxml"));
            Parent startScreen = loader.load();
            Scene startScene = new Scene(startScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);
            Stage currentStage = (Stage) root.getScene().getWindow();
            currentStage.setScene(startScene);

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

        } catch (IOException e) {
            System.err.println("Error loading MenuScreen.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
