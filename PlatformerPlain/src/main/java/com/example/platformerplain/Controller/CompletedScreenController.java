package com.example.platformerplain.Controller;

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
import java.util.Objects;

/**
 * This class serves as the controller for the Completed Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including setting the background image and handling sound effects when the player completed all levels.
 */
public class CompletedScreenController {

    public Button MenuButton;
    public Button ExitButton;
    @FXML
    private GridPane root;  // The root layout for the Fail Screen

    @FXML
    private Stage primaryStage;  // The main application stage
    private MediaPlayer mediaPlayer;  // Media player for sound effects

    /**
     * Sets the primary stage for this controller.
     * @param primaryStage the main stage to be associated with this controller
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Initializes the Fail Screen by loading the background image and
     * playing the associated fail sound.
     * @throws URISyntaxException if an error occurs while retrieving the URI of the sound file
     */
    @FXML
    private void initialize() throws URISyntaxException {
        // Load the background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backgrounds/Completed.png")));

        // Create a BackgroundImage object, setting it to fit the GridPane appropriately
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,  // Prevent image repetition
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(200, 150,  // Customized width and height
                        false,  // Do not stretch width
                        false,  // Do not stretch height
                        true,   // Maintain aspect ratio for width
                        true));  // Maintain aspect ratio for height

        // Apply the background to the GridPane
        root.setBackground(new Background(background));

        // Load the fail sound effect
        String completedSoundFile = "/sounds/completed.mp3"; // Verify the file path is correct
        Media completedSound = new Media(Objects.requireNonNull(getClass().getResource(completedSoundFile)).toURI().toString());
        mediaPlayer = new MediaPlayer(completedSound);

        // Play the sound effect upon initialization
        mediaPlayer.play();
    }

    /**
     * Handles the event for exiting the game when the exit button is clicked.
     * This method shuts down the application.
     */
    @FXML
    void handleExitGame() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Handles the event for the menu button when clicked.
     * This method creates a new instance of MenuScreen and displays it,
     * while cleaning up resources held by the media player.
     */
    @FXML
    private void handleMenu() {
        // Instantiate the MenuScreen and pass the main stage
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.show(primaryStage);  // Forward the current stage

        // Cleanup media player resources
        releaseMediaPlayer();
    }

    /**
     * Releases resources held by the media player.
     * It stops playback and cleans up the media player instance.
     */
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose(); // Free up memory
            mediaPlayer = null;
        }
    }
}

