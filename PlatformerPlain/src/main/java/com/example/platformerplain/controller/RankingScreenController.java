package com.example.platformerplain.controller;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.view.MenuScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URISyntaxException;

import static com.example.platformerplain.data.Assets.COMPLETE_SOUND;

public class RankingScreenController {
    public Button MenuButton;

    public Label highScore1Label;
    public Label highScore2Label;
    public Label highScore3Label;
    public Label yourScoreLabel;
    @FXML
    private VBox root;

    private Stage primaryStage;


    /**
     * Sets the primary stage for this controller.
     * @param primaryStage the main stage to be associated with this controller
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setHighScore1Label(int score1) {
        highScore1Label.setText("1st score: " + score1);
    }
    public void setHighScore2Label(int score2) {
        highScore2Label.setText("2nd Highest score: " + score2);
    }
    public void setHighScore3Label(int score3) {
        highScore3Label.setText("3rd Highest score: " + score3);
    }
    public void setYourScore(int score) {
        yourScoreLabel.setText("Latest Score: " + score);
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

    private void playBackgroundMusic() throws URISyntaxException {
        // TODO
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
