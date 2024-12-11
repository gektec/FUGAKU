package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.View.GameScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

import static com.example.platformerplain.Assets.BACKGROUND_MOON;

/**
 * This class serves as the controller for the Level Select Screen in the game.
 * It manages the initialization and configuration of UI components.
 * including setting the background image and buttons to select a level.
 */

public class LevelSelectScreenController {

    public Button FirstLevel;
    public Button SecondLevel;
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
        // Load the background image from resources
        Image backgroundImage = BACKGROUND_MOON;

        // Set up the BackgroundImage to fit the GridPane
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,  // Don't repeat the image
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(200, 150,  // Define custom dimensions
                        false,   // Don't stretch width
                        false,   // Don't stretch height
                        true,    // Keep aspect ratio for width
                        true));  // Keep aspect ratio for height

        // Apply the generated background to the GridPane
        root.setBackground(new Background(background));
    }

    @FXML
    void handleFirstLevel() {
        startLevel(1);
    }

    @FXML
    private void handleSecondLevel() {
        startLevel(2);
    }

    /**
     * Triggered when the button to start the first level is clicked.
     * It starts the game if the primary stage is defined.
     */
    private void startLevel(int level) {
        if (primaryStage != null) {
            GameScreen gameScreen = new GameScreen(level);
            gameScreen.show(primaryStage);
        } else {
            System.err.println("Primary stage is not set.");
        }
    }
}
