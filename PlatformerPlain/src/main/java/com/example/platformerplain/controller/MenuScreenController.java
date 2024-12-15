package com.example.platformerplain.controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.view.LevelSelectScreen;
import com.example.platformerplain.model.GameModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
     *
     * @param primaryStage the Stage instance to be used
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Loads the background image and initializes background music playback.
     * This method is called automatically after the FXML file is loaded,
     * setting up the initial state of the menu screen.
     */
    @FXML
    private void initialize() {
        loadBackgroundImage();
        playBackgroundMusic();
        updateDebugButtonText();
    }

    /**
     * Sets the background image of the root VBox using the Assets class.
     * This method enhances the visual appearance of the menu screen.
     */
    private void loadBackgroundImage() {
        root.setBackground(Assets.MENU_BACKGROUND);
    }

    /**
     * Loads and plays the background music indefinitely.
     * Currently set to play the victory sound, which may need to be replaced
     * with appropriate background music in the future.
     */
    private void playBackgroundMusic() {
        // TODO : replace it.
        VICTORY_SOUND.play();
    }

    /**
     * Handles the "Start Game" button click event. This method opens
     * the Level Select screen, allowing the user to choose a level to play.
     */
    @FXML
    private void handleStartGame() {
        LevelSelectScreen selectScreen = new LevelSelectScreen();
        selectScreen.show(primaryStage);
    }

    /**
     * Displays a dialog with instructions on how to play the game.
     * This method delegates the help display to the PauseScreenController.
     */
    @FXML
    public void handleHelp() {
        PauseScreenController pause = new PauseScreenController();
        pause.handleHelp();
    }

    /**
     * Exits the application when the exit button is clicked.
     * This method terminates the JavaFX application and the JVM.
     */
    @FXML
    void handleExitGame() {
        Platform.exit(); // Exit the JavaFX application
        System.exit(0); // Terminate the JVM
    }

    /**
     * Toggles debug mode on or off in the game.
     * This method updates the game model's debug mode state and refreshes the button text
     * to reflect the current status of debug mode.
     */
    @FXML
    public void handleDebugMode() {
        GameModel.setDebugMode(!GameModel.isDebugMode());
        updateDebugButtonText();
    }

    /**
     * Updates the text of the debug button to indicate whether debug mode is currently enabled or disabled.
     * If debug mode is on, the button shows "Debug Mode: ON"; otherwise, it shows "Debug Mode: OFF".
     */
    @FXML
    private void updateDebugButtonText() {
        if (GameModel.isDebugMode()) {
            debugButton.setText("Debug Mode: ON");
        } else {
            debugButton.setText("Debug Mode: OFF");
        }
    }
}