package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

public class StartScreenController {

    @FXML
    private VBox root;  // VBox in the FXML file

    @FXML
    private Button startButton;

    private Stage primaryStage;
    private MediaPlayer mediaPlayer; // 添加 MediaPlayer

    // Set the primaryStage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Initialize method for loading the background image and playing the background music
    @FXML
    private void initialize() throws URISyntaxException {
        // Load background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/background.png"));

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

        // Load the background music
        String backgroundMusicFile = "/sounds/victory.mp3"; // 确保路径正确
        Media backgroundMusic = new Media(getClass().getResource(backgroundMusicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 设置为无限循环

        // 播放背景音乐
        mediaPlayer.play();
    }

    // Handle the start game button click event
    @FXML
    void handleStartGame() {

        if (primaryStage != null) {

            Main.getInstance().startGame(primaryStage);

        } else {
            System.err.println("Primary stage is not set.");
        }
    }

    @FXML
    public void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("How to Play");
        alert.setContentText("Press 'A' to move left,\nPress 'D' to move right,\nPress 'J' to jump.");
        alert.showAndWait();
    }

    @FXML
    void handleExitGame() {
        // Call Platform.exit() to close the application
        Platform.exit();
        System.exit(0);
    }
}
