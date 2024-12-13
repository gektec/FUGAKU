package com.example.platformerplain.Controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.View.MenuScreen;
import com.example.platformerplain.model.GameModel;
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

import static com.example.platformerplain.Assets.FAIL_SOUND;

/**
 * This class serves as the controller for the Fail Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including setting the background image and handling sound effects when the player fails.
 */
public class FailScreenController {

    public Button MenuButton;
    public Button ExitButton;
    public Button RestartButton;
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
        loadBackgroundImage();
        playBackgroundMusic();
    }

    private void loadBackgroundImage() {
        // Load the background image

        root.setBackground(Assets.LEVEL_FAILED_BACKGROUND);
    }

    /**
     * Loads and plays the background music indefinitely.
     * @throws URISyntaxException if there is an issue retrieving the URI of the music file
     */
    private void playBackgroundMusic() throws URISyntaxException {
        // Play the sound effect upon initialization
        FAIL_SOUND.play();
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

    /**
     * Handles the event for the restart button when clicked.
     * This method creates a new instance of Main and use it to call restart function.
     * It will restart current level.
     */
    @FXML
    private void handleRestart() {
        GameModel gameModel = GameModel.getInstance();
        if (gameModel != null) {
            GameModel.getInstance().restartLevel(); // Transition to the next level
        }
    }
}

