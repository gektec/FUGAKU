package com.example.platformerplain.Screen;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.StartScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartScreen implements Screen {

    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/start_screen.fxml"));
            Parent startScreen = loader.load();
            Scene startScene = new Scene(startScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);

            StartScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("Platformer Game");
            primaryStage.setScene(startScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}