package com.example.platformerplain.View;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.CompletedScreenController;
import com.example.platformerplain.Main;
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
            Scene completedScene = new Scene(completedScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);

            CompletedScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            Main main = Main.getInstance();
            if (main != null) {
                controller.setScore(main.getFinalScore());
                controller.setKilled(main.getCurrentKilled());
                controller.setTime(main.getTotalTime());
            }

            primaryStage.setTitle("Congratulations!");
            primaryStage.setScene(completedScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
