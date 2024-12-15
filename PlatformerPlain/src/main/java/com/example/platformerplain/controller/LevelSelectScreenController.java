package com.example.platformerplain.controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * This class serves as the controller for the Level Select Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including setting the background image and buttons to select a level.
 */
public class LevelSelectScreenController {

    public Button FirstLevel;
    public Button SecondLevel;
    public Button ThirdLevel;
    @FXML
    private GridPane root;  // Root layout for the Level Select screen

    @FXML
    private Stage primaryStage;  // Reference to the main application stage

    /**
     * Sets the primary stage for this controller.
     *
     * @param primaryStage The main stage to be used by this controller
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Initializes the Level Select screen by loading the background image.
     * This method is called automatically when the FXML file is loaded,
     * and it sets up the visual elements for the level selection.
     */
    @FXML
    private void initialize() {
        loadBackgroundImage();
    }

    /**
     * Loads and sets the background image for the Level Select screen
     * using the Assets class. This method configures the visual appearance
     * of the screen to enhance the user experience.
     */
    private void loadBackgroundImage() {
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    /**
     * Handles the event when the first level button is clicked.
     * This method initiates the game starting procedure for the first level.
     */
    @FXML
    void handleFirstLevel() {
        GameModel.startGame(primaryStage, 1);
    }

    /**
     * Handles the event when the second level button is clicked.
     * This method initiates the game starting procedure for the second level.
     */
    @FXML
    private void handleSecondLevel() {
        GameModel.startGame(primaryStage, 2);
    }

    /**
     * Handles the event when the third level button is clicked.
     * This method initiates the game starting procedure for the third level.
     */
    @FXML
    private void handleThirdLevel() {
        GameModel.startGame(primaryStage, 3);
    }
}