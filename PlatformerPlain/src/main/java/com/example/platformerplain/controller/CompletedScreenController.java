package com.example.platformerplain.controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.view.MenuScreen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;

import static com.example.platformerplain.Assets.COMPLETE_SOUND;

/**
 * This class serves as the controller for the Completed Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including setting the background image and handling sound effects when the player completed all levels.
 */
public class CompletedScreenController {

    public Button MenuButton;
    public Button ExitButton;
    public Label scoreLabel;
    public Label killedLabel;
    public Label timeLabel;
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

    public void setScore(int score) {
        scoreLabel.setText("Your Final Score is: " + score);
    }

    public void setKilled(int killed) {
        if (killed == 0) {
            killedLabel.setText("No bloodshed! You are truly a harmless pursuer");
        } else if (killed == 1) {
            killedLabel.setText("You only killed one person. You are such a merciful Lord!");
        } else if (killed >= 1) {
            killedLabel.setText("You have killed" + " "+killed +" "+  "enemies!");
        }
    }

    public void setTime(long time) {timeLabel.setText("Total time: " + time + "s");}

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
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    private void playBackgroundMusic() throws URISyntaxException {
        // Load the fail sound effect

        // Play the sound effect upon initialization
        COMPLETE_SOUND.play();
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

