package com.example.platformerplain.Controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.View.GameScreen;
import com.example.platformerplain.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.example.platformerplain.Assets.BACKGROUND_MOON;

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
        GameModel gameModel = GameModel.getInstance();
        if (gameModel != null) {
            GameScreen.clearPane();
            gameModel.startGame(primaryStage, 1);
        }
    }

    @FXML
    private void handleSecondLevel() {
        GameModel gameModel = GameModel.getInstance();
        if (gameModel != null) {
            GameScreen.clearPane();
            gameModel.startGame(primaryStage, 2);
        }
    }

    @FXML
    private void handleThirdLevel() {
        GameModel gameModel = GameModel.getInstance();
        if (gameModel != null) {
            GameScreen.clearPane();
            gameModel.startGame(primaryStage, 3);
        }
    }

}
