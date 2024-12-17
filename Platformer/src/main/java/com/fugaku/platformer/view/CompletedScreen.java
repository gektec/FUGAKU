package com.fugaku.platformer.view;

import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.controller.CompletedScreenController;
import com.fugaku.platformer.model.GameModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class represents the screen that is shown to the players when they have completed all levels of the game.
 *
 * @author Zelin Xia
 * @date 2024/12/9
 */
public class CompletedScreen implements Screen {

    /**
     * Loads and displays the completion screen on the specified stage.
     * @param primaryStage The stage to display the completion screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fugaku/platformer/Completed.fxml"));
            Parent completedScreen = loader.load();
            Scene completedScene = new Scene(completedScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            CompletedScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setScore(GameModel.getFinalScore());
            controller.setScore(GameModel.getFinalScore());
            controller.setKilled(GameModel.getCurrentKilled());
            controller.setTime(GameModel.getTotalTime());

            primaryStage.setTitle("FUGAKU: All Levels Done!");
            primaryStage.setScene(completedScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
