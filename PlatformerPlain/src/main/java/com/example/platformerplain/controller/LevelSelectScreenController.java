package com.example.platformerplain.controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * This class serves as the controller for the Level Select Screen in the game.
 * It manages the initialization and configuration of UI components.
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
     *@param primaryStage The main stage to be used by this controller
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Initializes the Level Select screen by loading the background image.
     */
    @FXML
    private void initialize() {
        loadBackgroundImage();
    }

    private void loadBackgroundImage() {
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    @FXML
    void handleFirstLevel() {

            GameModel.startGame(primaryStage, 1);
    }

    @FXML
    private void handleSecondLevel() {

            GameModel.startGame(primaryStage, 2);
    }

    @FXML
    private void handleThirdLevel() {

            GameModel.startGame(primaryStage, 3);
    }

}
