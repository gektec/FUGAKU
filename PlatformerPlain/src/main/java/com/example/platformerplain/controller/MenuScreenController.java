package com.example.platformerplain.controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.view.LevelSelectScreen;
import com.example.platformerplain.model.GameModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static com.example.platformerplain.Assets.VICTORY_SOUND;

/**
 * This class serves as the controller for the Menu Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including setting the background image and handling different buttons.
 */
public class MenuScreenController {

    public Button startButton;
    public Button helpButton;
    public Button exitButton;
    @FXML
    private VBox root;  // VBox in the FXML file
    public Button debugButton;
    private Stage primaryStage;

    /**
     * Assigns the primary stage to this controller.
     * @param primaryStage the Stage instance to be used
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Loads the background image and initializes background music playback.
     */
    @FXML
    private void initialize() {
        //loadBackgroundImage();
        loadBackgroundImage();
        playBackgroundMusic();
        updateDebugButtonText();
    }

    /**
     * Sets the background image of the root VBox.
     */
    private void loadBackgroundImage() {
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    /**
     * Loads and plays the background music indefinitely.
     */
    private void playBackgroundMusic() {
        //TODO : replace it.
        VICTORY_SOUND.play();
    }

    /**
     * Handles the "Start Game" button click event, opening the Level Select screen.
     */
    @FXML
    private void handleStartGame() {
        LevelSelectScreen selectScreen = new LevelSelectScreen();
        selectScreen.show(primaryStage);
    }

    /**
     * Displays a dialog with instructions on how to play the game.
     */
    @FXML
    public void handleHelp() {
        PauseScreenController pause = new PauseScreenController();
        pause.handleHelp();
    }


    /**
     * Exits the application when the game is closed.
     */
    @FXML
    void handleExitGame() {
        Platform.exit(); // Exit the JavaFX application
        System.exit(0); // Terminate the JVM
    }

    @FXML
    public void handleDebugMode() {
        GameModel.setDebugMode(!GameModel.isDebugMode());
        updateDebugButtonText();
    }

    @FXML
    private void updateDebugButtonText() {
        if (GameModel.isDebugMode()){
            debugButton.setText("Debug Mode: ON");
        } else {
            debugButton.setText("Debug Mode: OFF");
        }
    }
}
