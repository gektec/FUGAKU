package com.example.platformerplain.controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.view.MenuScreen;
import com.example.platformerplain.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


/**
 * This class serves as the controller for the Transition Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including options that appear when the player completes the current level:
 * Menu, Next Level, and Restart.
 */
public class TransitionScreenController {

    public Button MenuButton;
    public Button NextLevelButton;
    public Button RestartButton;

    @FXML
    private Label scoreLabel;
    public Label killedLabel;
    public Label timeLabel;

    @FXML
    private GridPane root;  // Root layout in the FXML file
    private Stage primaryStage;  // Main application stage
    private MediaPlayer mediaPlayer; // MediaPlayer instance for sound playback

    /**
     * Sets the primary stage for this controller.
     *
     * @param primaryStage the Stage instance to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Updates the score label on the transition screen.
     *
     * @param score the new score to display
     */
    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    /**
     * Updates the killed enemies label on the transition screen.
     *
     * @param killed the number of enemies killed to display
     */
    public void setKilled(int killed) {
        killedLabel.setText("Enemy killed: " + killed);
    }

    /**
     * Updates the time label on the transition screen.
     *
     * @param time the time taken to complete the level, in seconds
     */
    public void setTime(long time) {
        timeLabel.setText("Time: " + time + "s");
    }

    /**
     * Initializes the transition screen, loading the background image
     * and playing the victory sound. This method is called automatically
     * after the FXML file is loaded.
     */
    @FXML
    private void initialize() {
        loadBackgroundImage();
        playBackgroundMusic();
    }

    /**
     * Loads the background image for the transition screen.
     * This method enhances the visual appearance of the screen.
     */
    private void loadBackgroundImage() {
        // Load background image
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    /**
     * Plays the victory sound when the transition screen is displayed.
     * This method loads and plays the victory sound associated with level completion.
     */
    private void playBackgroundMusic() {
        // TODO
    }

    /**
     * Navigates back to the main menu when the menu button is pressed.
     * Releases resources used by the media player to prevent memory leaks.
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
     * Clears the reference to the media player to avoid memory leaks.
     */
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop playback
            mediaPlayer.dispose(); // Release memory
            mediaPlayer = null; // Clear reference
        }
    }

    /**
     * Proceeds to the next level when the "Next Level" button is clicked.
     * This method retrieves the current level number and starts the next level.
     */
    @FXML
    public void handleNextLevel() {
        GameModel.startGame(primaryStage, LevelData.getLevelInformation.getLevelNumber() + 1); // Transition to the next level
    }

    /**
     * Restarts the game when the "Restart" button is clicked.
     * This method starts the current level again, allowing the player to retry it.
     */
    @FXML
    private void handleRestart() {
        GameModel.startGame(primaryStage, LevelData.getLevelInformation.getLevelNumber());
    }
}
