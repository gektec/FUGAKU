package com.example.platformerplain.controller;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.data.LevelData;
import com.example.platformerplain.view.MenuScreen;
import com.example.platformerplain.model.GameModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;


/**
 * This class serves as the controller for the Fail Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including setting the background image and handling sound effects when the player fails.
 *
 * @author Zelin Xia
 * @date 2024/11/13
 */
public class FailScreenController {

    public Button MenuButton;
    public Button ExitButton;
    public Button RestartButton;
    @FXML
    private StackPane root;  // The root layout for the Fail Screen

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

    /**
     * Loads and sets the background image for the fail screen using the Assets class.
     */
    private void loadBackgroundImage() {
        // Load the background image
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    /**
     * Plays the background music associated with the fail screen.
     * This method plays the fail sound effect, which is usually played when the player fails.
     *
     * @throws URISyntaxException if there is an issue retrieving the URI of the music file
     */
    private void playBackgroundMusic() throws URISyntaxException {
        // TODO
    }

    /**
     * Handles the event for exiting the game when the exit button is clicked.
     * This method shuts down the application, ensuring proper termination of resources.
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

        GameModel.stopGameLoop();
        // Instantiate the MenuScreen and pass the main stage
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.show(primaryStage);  // Forward the current stage

        // Cleanup media player resources
        releaseMediaPlayer();
    }

    /**
     * Releases resources held by the media player.
     * It stops playback and cleans up the media player instance to free up memory.
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
     * This method restarts the current game level by invoking the startGame method
     * of the GameModel class, using the current level number.
     */
    @FXML
    private void handleRestart() {
        GameModel.startGame(primaryStage, LevelData.getLevelInformation.getLevelNumber());
    }
}
