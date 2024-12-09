package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import com.example.platformerplain.Screen.MenuScreen;
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
        Main main = Main.getInstance();
        if (main != null) {
            main.resumeGame();  // Restore the game's logic
            // Remove the pause menu from the scene
            ((Pane) primaryStage.getScene().getRoot()).getChildren().remove(root);
        }
    }

    /**
     * Displays a dialog with instructions on how to play the game.
     */
    @FXML
    public void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("How to Play");

        TextArea helpTextArea = new TextArea();
        helpTextArea.setText("""
            Controls:
            - Press 'A' to move left and explore.
            - Press 'D' to dash right into action!
            - Press 'J' to jump high and reach new heights.
            - Press 'K' to dash and swiftly dodge obstacles.
            - Press 'W' to climb up ladders and discover what's above.
            - Press 'S' to descend down ladders and uncover hidden secrets.

            Tips for Success:
            1. Use 'J' to interact with objects in your environment.
            2. Keep an eye out for enemies and avoid them to stay in the game.
            3. You can defeat enemies by jumping on them—just be cautious!
            4. Collect power-ups to boost your abilities and gain an edge.
            5. Race against time to reach the goal quickly and claim victory!

            Awesome Moves to Master:
            1. Slide Jump: Press 'J' while running for an extra boost in height!
            2. Dash: Tap 'K' while moving to zip past hazards with style.
            3. Mix and match your moves for exciting strategies and combos!

            Good luck out there! Have a blast and don’t hesitate to reach out if you have any questions!""");

        helpTextArea.setEditable(false);
        helpTextArea.setWrapText(true);
        helpTextArea.setPrefSize(400, 300);

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
     * This method terminates the JavaFX application and the JVM.
     */
    @FXML
    void handleExitGame() {
        Platform.exit(); // Exit the JavaFX application gracefully
        System.exit(0); // Terminate the Java Virtual Machine
    }
}
