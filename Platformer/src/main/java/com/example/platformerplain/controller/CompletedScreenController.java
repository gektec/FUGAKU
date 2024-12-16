package com.example.platformerplain.controller;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.view.MenuScreen;
import com.example.platformerplain.view.RankScreen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;


/**
 * This class serves as the controller for the Completed Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including setting the background image and handling sound effects when the player completed all levels.
 *
 * @author Zelin Xia
 * @date 2024/12/9
 */
public class CompletedScreenController {

    public Button MenuButton;
    public Button ExitButton;
    public Label scoreLabel;
    public Label killedLabel;
    public Label timeLabel;
    public Button RankingButton;
    @FXML
    private GridPane root;  // The root layout for the Fail Screen

    @FXML
    private Stage primaryStage;  // The main application stage
    private MediaPlayer mediaPlayer;  // Media player for sound effects

    /**
     * Sets the primary stage for this controller.
     *
     * @param primaryStage the main stage to be associated with this controller
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Updates the score label to display the player's final score.
     *
     * @param score the final score to be displayed
     */
    public void setScore(int score) {
        scoreLabel.setText("Your Final Score is: " + score);
    }

    /**
     * Updates the killed label based on the number of enemies killed by the player.
     *
     * @param killed the number of enemies killed
     */
    public void setKilled(int killed) {
        if (killed == 0) {
            killedLabel.setText("No bloodshed! You are truly a harmless pursuer");
        } else if (killed == 1) {
            killedLabel.setText("You only killed one person. You are such a merciful Lord!");
        } else if (killed >= 1) {
            killedLabel.setText("You have killed" + " " + killed + " " + "enemies!");
        }
    }

    /**
     * Updates the time label to display the total time spent in the game.
     *
     * @param time the total time in seconds
     */
    public void setTime(long time) {
        timeLabel.setText("Total time: " + time + "s");
    }

    /**
     * Initializes the Fail Screen by loading the background image and
     * playing the associated fail sound.
     *
     * @throws URISyntaxException if an error occurs while retrieving the URI of the sound file
     */
    @FXML
    private void initialize() throws URISyntaxException {
        loadBackgroundImage();
        playBackgroundMusic();
    }

    /**
     * Loads and sets a pure color background for the completed screen.
     */
    private void loadBackgroundImage() {
        char colorCode = GameModel.getColor();
        switch (colorCode) {
            case 'K':
                root.setStyle("-fx-background-color: #000000;");
                break;
            case 'O':
                root.setStyle("-fx-background-color: #FFA500;");
                break;
            case 'Y':
                root.setStyle("-fx-background-color: #FFFF00;");
                break;
            case 'B':
                root.setStyle("-fx-background-color: #87CEEB;");
                break;
            case 'P':
                root.setStyle("-fx-background-color: #800080;");
                break;
            case 'G':
                root.setStyle("-fx-background-color: #008000;");
                break;
            case 'R':
                root.setStyle("-fx-background-color: #FF0000;");
                break;
            case 'N': // Assuming 'P' is used for both Purple and Pink, using 'N' for Pink to differentiate.
                root.setStyle("-fx-background-color: #FFC0CB;");
                break;
            default:
                root.setStyle("-fx-background-color: #FFFFFF;");
                break;
        }
    }



    /**
     * Plays the background music associated with the completed screen.
     * @throws URISyntaxException if an error occurs while retrieving the URI of the sound file
     */
    private void playBackgroundMusic() throws URISyntaxException {
        // Load the fail sound effect

        //todo: Play the sound effect upon initialization
    }

    /**
     * Handles the event for exiting the game when the exit button is clicked.
     * This method shuts down the application and ensures all resources are released.
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
