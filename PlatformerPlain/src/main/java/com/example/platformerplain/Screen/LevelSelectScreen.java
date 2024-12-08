package com.example.platformerplain.Screen;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.LevelSelectScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LevelSelectScreen implements Screen{
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/LevelSelect.fxml"));
            Parent selectScreen = loader.load();
            Scene selectScene = new Scene(selectScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);

            LevelSelectScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("Select Level");
            primaryStage.setScene(selectScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
