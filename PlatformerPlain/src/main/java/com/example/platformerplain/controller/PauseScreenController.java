package com.example.platformerplain.controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.view.GameScreen;
import com.example.platformerplain.view.MenuScreen;
import com.example.platformerplain.model.GameModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * This class serves as the controller for the Pause Screen in the game.
 * It manages the initialization and configuration of UI components,
 * including setting the background image and three options appear when the player presses the pause button: Menu, Exit, Help, Resume
 */

public class PauseScreenController {

    public Button MenuButton;
    public Button ExitButton;
    public Button ResumeButton;
    public Button helpButton;
    public Button RestartButton;
    @FXML
    private GridPane root;  // Root layout for the pause menu
    private Stage primaryStage;  // The main application stage

    /**
     * Assigns the primary stage to this controller.
     * @param primaryStage the Stage instance to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Resumes the game when the "Resume" button is clicked.
     * This method restores the game state and removes the pause menu from the display.
     */
    @FXML
    private void handleResume() {

        GameModel.resumeGame();  // Restore the game's logic
        // Remove the pause menu from the scene
        ((Pane) primaryStage.getScene().getRoot()).getChildren().remove(root);
    }

    /**
     * Displays a dialog with instructions on how to play the game.
     */
    @FXML
    public static void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("How to Play");

        TextArea helpTextArea = new TextArea();
        helpTextArea.setText("""
            Controls:
            - Press 'A' and 'D' to explore.
            - Press 'J' to jump.
            - Press 'K' with 'W/A/S/D' to dash in 8 directions.
            - Press 'W' and 'S' to climb ladders.

            Tips:
            - Wall Jump: Press 'J' while sliding on walls.
            - Step on enemies to get extra height.""");

        helpTextArea.setEditable(false);
        helpTextArea.setWrapText(true);
        helpTextArea.setPrefSize(360, 280);
        helpTextArea.setFont(Assets.baseFont);

        alert.getDialogPane().setContent(helpTextArea);

        // Customize the button text
        ButtonType gotItButtonType = new ButtonType("Got it");
        alert.getButtonTypes().setAll(gotItButtonType); // Replace default buttons with our custom button

        alert.showAndWait();
    }


    /**
     * Navigates the user back to the main menu when the "Menu" button is clicked.
     */
    @FXML
    private void handleMenu() {
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.show(primaryStage);  // Display the main menu
    }

    /**
     * Exits the game when the "Exit" button is clicked.
     */
    @FXML
    void handleExitGame() {
        Platform.exit(); // Exit the JavaFX application gracefully
        System.exit(0); // Terminate the Java Virtual Machine
    }

    /**
     * Restarts the game when the "Restart" button is clicked.
     */
    @FXML
    private void handleRestart() {
        GameModel.startGame(primaryStage, GameModel.getCurrentLevel()); // Transition to the next level
    }
}
