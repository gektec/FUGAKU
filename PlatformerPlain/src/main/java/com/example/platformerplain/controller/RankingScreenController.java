package com.example.platformerplain.controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.view.MenuScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URISyntaxException;

import static com.example.platformerplain.Assets.COMPLETE_SOUND;

public class RankingScreenController {
    public Button MenuButton;
    public Label rankingTitleLabel;

    public Label highScore2Label;
    public Label highScore3Label;
    public Label yourScoreLabel;
    @FXML
    private VBox root;
    public Label highScore1Label;
    private Stage primaryStage;


    /**
     * Sets the primary stage for this controller.
     * @param primaryStage the main stage to be associated with this controller
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setHighScore1Label(int score1) {
        yourScoreLabel.setText("Highest score is: " + score1);
    }
    public void setHighScore2Label(int score2) {
        yourScoreLabel.setText("Second Highest score is: " + score2);
    }
    public void setHighScore3Label(int score3) {
        yourScoreLabel.setText("Third Highest score is: " + score3);
    }

    public void setYourScore(int score) {
        yourScoreLabel.setText("Your Score is: " + score);
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
     * Plays the background music associated with the completed screen.
     * @throws URISyntaxException if an error occurs while retrieving the URI of the sound file
     */
    private void playBackgroundMusic() throws URISyntaxException {
        // Load the fail sound effect
        // Play the sound effect upon initialization
        COMPLETE_SOUND.play();
    }

    /**
     * Loads and sets the background image for the completed screen.
     */
    private void loadBackgroundImage() {
        // Load the background image
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    @FXML
    private void handleMenu() {
        // Instantiate the MenuScreen and pass the main stage
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.show(primaryStage);  // Forward the current stage
    }

}
