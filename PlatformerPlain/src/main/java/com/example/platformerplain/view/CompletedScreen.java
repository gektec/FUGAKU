package com.example.platformerplain.view;

import com.example.platformerplain.Constants;
import com.example.platformerplain.controller.CompletedScreenController;
import com.example.platformerplain.model.GameModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class represents the screen that is shown to the players when they have completed all levels of the game.
 */
public class CompletedScreen implements Screen {

    /**
     * Loads and displays the completion screen on the specified stage.
     * @param primaryStage The stage to display the completion screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Completed.fxml"));
            Parent completedScreen = loader.load();
            Scene completedScene = new Scene(completedScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            CompletedScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setScore(GameModel.getFinalScore());
            controller.setKilled(GameModel.getCurrentKilled());
            controller.setTime(GameModel.getTotalTime());

            primaryStage.setTitle("Congratulations!");
            primaryStage.setScene(completedScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
