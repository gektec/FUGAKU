package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import com.example.platformerplain.View.MenuScreen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.example.platformerplain.Assets.VICTORY_SOUND;

/**
 * This class serves as the controller for the Transition Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including four options appear when the player pass current level: Menu, Exit, Help
 */

public class TransitionScreenController {

    public Button MenuButton;
    public Button NextLevelButton;
    public Button ExitButton;
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
     * @throws URISyntaxException if the URI for the media file is malformed
     */
    @FXML
    private void initialize() throws URISyntaxException {
        loadBackgroundImage();
        playBackgroundMusic();
    }
    private void loadBackgroundImage() {
        // Load background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backgrounds/Transition.png")));

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
    }

    private void playBackgroundMusic(){
        // Load the victory sound
        VICTORY_SOUND.play();
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

    /**
     * Restarts the game when the "Restart" button is clicked.
     */
    @FXML
    private void handleRestart() {
        Main main = Main.getInstance();
        if (main != null) {
            main.restartLevel(); // Transition to the next level
        }
    }
}
