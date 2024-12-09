package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import com.example.platformerplain.Screen.MenuScreen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * This class serves as the controller for the Transition Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including four options appear when the player pass current level: Menu, Exit, Help
 */

public class TransitionScreenController {

    public Button MenuButton;
    public Button NextLevelButton;
    public Button ExitButton;
    @FXML
    private GridPane root;  // Root layout in the FXML file
    private Stage primaryStage;  // Main application stage
    private MediaPlayer mediaPlayer; // MediaPlayer instance for sound playback

    /**
     * Sets the primary stage for this controller.
     * @param primaryStage the Stage instance to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Initializes the transition screen, loading the background image
     * and playing the victory sound.
     * @throws URISyntaxException if the URI for the media file is malformed
     */
    @FXML
    private void initialize() throws URISyntaxException {
        // Load background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backgrounds/Victory.png")));

        // Create a BackgroundImage object with specified properties
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,  // Do not repeat the image
                BackgroundRepeat.NO_REPEAT,  // Do not repeat the image
                BackgroundPosition.CENTER,    // Center the image
                new BackgroundSize(200, 150,  // Custom width and height
                        false,   // Do not stretch the width
                        false,   // Do not stretch the height
                        true,    // Preserve aspect ratio for the width
                        true));  // Preserve aspect ratio for the height

        // Set the GridPane background
        root.setBackground(new Background(background));

        // Load the victory sound
        String victorySoundFile = "/sounds/victory.mp3"; // Ensure path correctness
        Media victorySound = new Media(Objects.requireNonNull(getClass().getResource(victorySoundFile)).toURI().toString());
        mediaPlayer = new MediaPlayer(victorySound);

        // Play the victory sound on initialization
        mediaPlayer.play();
    }

    /**
     * Exits the game application.
     * This method closes the platform and terminates the JVM.
     */
    @FXML
    public void handleExitGame() {
        Platform.exit(); // Close the JavaFX application
        System.exit(0); // Terminate the Java Virtual Machine
    }

    /**
     * Navigates back to the main menu when the menu button is pressed.
     * Releases resources used by the media player.
     */
    @FXML
    public void handleMenu() {
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.show(primaryStage);  // Show the main menu

        // Release media player resources
        releaseMediaPlayer();
    }

    /**
     * Stops the media player and releases its resources.
     */
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop playback
            mediaPlayer.dispose(); // Release memory
            mediaPlayer = null; // Clear reference
        }
    }

    /**
     * Proceeds to the next level.
     * This method calls the main instance to start the next game level.
     */
    @FXML
    public void handleNextLevel() {
        Main main = Main.getInstance();
        if (main != null) {
            main.startNextLevel(); // Transition to the next level
        }
    }
}
