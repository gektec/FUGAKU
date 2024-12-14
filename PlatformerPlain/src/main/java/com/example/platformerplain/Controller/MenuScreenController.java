package com.example.platformerplain.Controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.View.LevelSelectScreen;
import com.example.platformerplain.model.GameModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
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
            
            Scoring System:
            - Each level starts with 1000 points.
            - You lose 10 points for every second that passes.
            - Defeating an enemy earns you an additional 200 points.
            - Aim for high scores by defeating enemies quickly and completing levels efficiently!

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
     * Exits the application when the game is closed.
     */
    @FXML
    void handleExitGame() {
        Platform.exit(); // Exit the JavaFX application
        System.exit(0); // Terminate the JVM
    }

    @FXML
    public void handleDebugMode() {
        GameModel.getInstance().setDebugMode(!GameModel.getInstance().isDebugMode());
        updateDebugButtonText();
    }

    @FXML
    private void updateDebugButtonText() {
        if (GameModel.getInstance().isDebugMode()){
            debugButton.setText("Debug Mode: ON");
        } else {
            debugButton.setText("Debug Mode: OFF");
        }
    }
}
