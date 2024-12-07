package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import com.example.platformerplain.Screen.MenuScreen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PauseScreenController {

    @FXML
    private GridPane root;  // GridPane in the FXML file

    @FXML
    private Button MenuButton;
    private Stage primaryStage;

    // Set the primaryStage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleResume() {
        Main main = Main.getInstance();
        if (main != null) {
            main.resumeGame();  // Restore game logic

            // Remove the pause menu from the stack
            ((Pane) primaryStage.getScene().getRoot()).getChildren().remove(root);
        }
    }

    @FXML
    private void handleMenu() {
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.show(primaryStage);  // Return to main menu
    }

    @FXML
    void handleExitGame() {
        Platform.exit();
        System.exit(0);
    }
}
