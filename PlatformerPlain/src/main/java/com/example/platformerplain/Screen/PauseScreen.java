package com.example.platformerplain.Screen;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.PauseScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PauseScreen implements Screen{
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Pause.fxml"));
            Parent pauseScreen = loader.load();
            Scene pauseScene = new Scene(pauseScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);

            PauseScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("Pause");
            primaryStage.setScene(pauseScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
