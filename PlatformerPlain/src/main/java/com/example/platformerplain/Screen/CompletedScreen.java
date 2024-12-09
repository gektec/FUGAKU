package com.example.platformerplain.Screen;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.CompletedScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CompletedScreen implements Screen {

    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Completed.fxml"));
            Parent completedScreen = loader.load();
            Scene completedScene = new Scene(completedScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);

            CompletedScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("Congratulation!");
            primaryStage.setScene(completedScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
