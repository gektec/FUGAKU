package com.example.platformerplain.controller;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.model.RankModel;
import com.example.platformerplain.view.LevelSelectScreen;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.view.OptionScreen;
import com.example.platformerplain.view.RankScreen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * This class serves as the controller for the Menu Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including setting the background image and handling different buttons.
 *
 * @author Zelin Xia
 * @date 2024/11/13
 */
public class MenuScreenController {

    public Button startButton;
    public Button helpButton;
    public Button exitButton;
    public Button RankButton;
    public Button optionButton;
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

    // Make the updateBackgroundColor method public
    public void updateBackgroundColor(char colorCode) {
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
            case 'N':
                root.setStyle("-fx-background-color: #FFC0CB;");
                break;
            default:
                root.setStyle("-fx-background-color: #FFFFFF;");
                break;
        }
    }

    /**
     * Loads and plays the background music indefinitely.
     * Currently set to play the victory sound, which may need to be replaced
     * with appropriate background music in the future.
     */
    private void playBackgroundMusic() {
        // TODO
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
            debugButton.setStyle("-fx-text-fill: red;");
        } else {
            debugButton.setText("Debug Mode: OFF");
            debugButton.setStyle("-fx-text-fill: #ECF0F1;");
        }
    }

    /**
     * Handles the event for the rank button when clicked.
     * This method creates a new instance of RankScreen and displays it,
     */
    @FXML
    private void handleRanking() {
        RankModel.highestScore();
        RankScreen rankScreen = new RankScreen();
        rankScreen.show(primaryStage);  // Forward the current stage
    }

    public void handleOption() {
        OptionScreen optionScreen = new OptionScreen();
        optionScreen.show(primaryStage);
    }
}
