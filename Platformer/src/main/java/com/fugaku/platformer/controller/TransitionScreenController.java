package com.fugaku.platformer.controller;

import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.data.LevelData;
import com.fugaku.platformer.view.MenuScreen;
import com.fugaku.platformer.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * This class serves as the controller for the Transition Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including options that appear when the player completes the current level:
 * Menu, Next Level, and Restart.
 *
 * @author Changyu Li
 * @date 2024/12/3
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
    }

    /**
     * Loads and sets a pure color background for the completed screen.
     */
    private void loadBackgroundImage() {
        // Load the background image
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    /**
     * Navigates back to the main menu when the menu button is pressed.
     */
    @FXML
    public void handleMenu() {
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.show(primaryStage);  // Show the main menu
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
