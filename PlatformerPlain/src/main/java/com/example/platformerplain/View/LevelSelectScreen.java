package com.example.platformerplain.View;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.LevelSelectScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class represents the screen where players can select the level they want to play.
 */
public class LevelSelectScreen implements Screen {

    /**
     * Loads and displays the level selection screen on the specified stage.
     * @param primaryStage The stage to display the level selection screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/LevelSelect.fxml"));
            Parent selectScreen = loader.load();
            Scene selectScene = new Scene(selectScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

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
