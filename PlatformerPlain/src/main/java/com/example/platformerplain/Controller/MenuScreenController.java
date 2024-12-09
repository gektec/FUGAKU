package com.example.platformerplain.Controller;

import com.example.platformerplain.Assets;
import com.example.platformerplain.Main;
import com.example.platformerplain.Screen.LevelSelectScreen;
import com.example.platformerplain.Screen.MenuScreen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class MenuScreenController {

    @FXML
    private VBox root;  // VBox in the FXML file

    @FXML
    private Button debugButton;

    private Stage primaryStage;
    private MediaPlayer mediaPlayer; // 添加 MediaPlayer

    // Set the primaryStage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Initialize method for loading the background image and playing the background music
    @FXML
    private void initialize() throws URISyntaxException {
        // Load background image
        Image backgroundImage = Assets.MENU_BACKGROUND;

        // Create a BackgroundImage object, ensuring the image adapts proportionally to the VBox size
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,  // Do not repeat the image
                BackgroundRepeat.NO_REPEAT,  // Do not repeat the image
                BackgroundPosition.CENTER,    // Center the image
                new BackgroundSize(200, 150,  // Set custom width and height
                        false,   // Do not stretch the width (set to true if you want to stretch)
                        false,   // Do not stretch the height (set to true if you want to stretch)
                        true,    // Preserve aspect ratio for the width
                        true));  // Preserve aspect ratio for the height

        // Set the VBox background
        root.setBackground(new Background(background));

        // Load the background music
        String backgroundMusicFile = "/sounds/victory.mp3"; // Make sure the path is correct
        Media backgroundMusic = new Media(getClass().getResource(backgroundMusicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set to infinite loop

        // Play background music
        mediaPlayer.play();

        updateDebugButtonText();

    }

    // Handle the start game button click event
    @FXML
    private void handleStartGame() {
        // Create a StartScreen instance and pass the main stage
        LevelSelectScreen selectScreen = new LevelSelectScreen();
        selectScreen.show(primaryStage);  // deliver currentStage
    }

    @FXML
    public void handleHelp() {
        // Create a new Alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("How to Play");

        // Create a TextArea to display help content
        TextArea helpTextArea = new TextArea();
        helpTextArea.setText("Controls:\n" +
                "Press 'A' to move left.\n" +
                "Press 'D' to move right.\n" +
                "Press 'J' to jump.\n" +
                "Press 'K' to dash.\n" +
                "Press 'W' to climb up the ladder.\n" +
                "Press 'S' to climb down the ladder.\n\n" +
                "Tips:\n" +
                "1. Use 'J' to interact with objects.\n" +
                "2. Avoid enemies to prevent damage.\n" +
                "3. Defeat enemies by jumping on them (be careful!).\n" +
                "4. Collect power-ups to enhance your abilities.\n" +
                "5. Reach the goal as soon as possible to win!\n\n" +
                "Special Moves:\n" +
                "1. Slide Jump (Press 'J' while running): Use it for extra height!\n" +
                "2. Dash (Press 'K' while moving): Quickly evade obstacles or rush forward.\n" +
                "3. Mix and match moves for advanced strategies!\n\n" +
                "Good luck and have fun! If you have any questions, feel free to ask!");

        helpTextArea.setEditable(false); // Make the text area non-editable
        helpTextArea.setWrapText(true); // Enable text wrapping
        helpTextArea.setPrefSize(400, 300); // Adjust preferred size for better readability

        // Add the text area to the dialog's content
        alert.getDialogPane().setContent(helpTextArea);

        // Show the dialog and wait for user action
        alert.showAndWait();
    }

    @FXML
    void handleExitGame() {
        // Call Platform.exit() to close the application
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void handleDebugMode(ActionEvent actionEvent) {
        Main mainInstance = Main.getInstance();
        mainInstance.setDebugMode(!mainInstance.getDebugMode());
        updateDebugButtonText();
    }

    @FXML
    private void updateDebugButtonText() {
        if (Main.getInstance().getDebugMode()) {
            debugButton.setText("Debug Mode: ON");
        } else {
            debugButton.setText("Debug Mode: OFF");
        }
    }
}
