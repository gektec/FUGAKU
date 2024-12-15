package com.example.platformerplain.Controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.View.MenuScreen;
import com.example.platformerplain.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import static com.example.platformerplain.Assets.VICTORY_SOUND;

/**
 * This class serves as the controller for the Transition Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including four options appear when the player pass current level: Menu, Exit, Help
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
     * @param primaryStage the Stage instance to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void setKilled(int killed) {killedLabel.setText("Enemy killed:" + killed);}

    public void setTime(long time) {timeLabel.setText("Time: " + time + "s");}

    /**
     * Initializes the transition screen, loading the background image
     * and playing the victory sound.
     */
    @FXML
    private void initialize() {
        loadBackgroundImage();
        playBackgroundMusic();
    }
    private void loadBackgroundImage() {
        // Load background image
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    private void playBackgroundMusic(){
        // Load the victory sound
        VICTORY_SOUND.play();
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
        GameModel gameModel = GameModel.getInstance();
        if (gameModel != null) {
            GameModel.getInstance().startGame(primaryStage, LevelData.getLevelInformation.getLevelNumber()+1); // Transition to the next level
        }
    }

    /**
     * Restarts the game when the "Restart" button is clicked.
     */
    @FXML
    private void handleRestart() {
        GameModel gameModel = GameModel.getInstance();
        if (gameModel != null) {
            GameModel.getInstance().restartLevel(); // Transition to the next level
        }
    }
}
