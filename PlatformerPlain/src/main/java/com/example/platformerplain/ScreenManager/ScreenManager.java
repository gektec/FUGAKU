package com.example.platformerplain.ScreenManager;

import com.example.platformerplain.Constants; // Import the Constants class
import com.example.platformerplain.Controller.FailScreenController;
import com.example.platformerplain.Controller.StartScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {

    private static ScreenManager instance;  // for store singleton instance
    private Stage primaryStage;

    // Private Constructor
    private ScreenManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //Static method to obtain a singleton instance
    public static ScreenManager getInstance(Stage primaryStage) {
        if (instance == null) {
            instance = new ScreenManager(primaryStage);
        }
        return instance;
    }

    public void showStartScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/start_screen.fxml"));
            Parent startScreen = loader.load();
            Scene startScene = new Scene(startScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT); // Use Constants

            StartScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("Platformer Game");
            primaryStage.setScene(startScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFailScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/fail_screen.fxml"));
            Parent failScreen = loader.load();
            Scene failScene = new Scene(failScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT); // Use Constants

            FailScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("EXIT AND TRY AGAIN!");
            primaryStage.setScene(failScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
