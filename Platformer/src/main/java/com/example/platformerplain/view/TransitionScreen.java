package com.example.platformerplain.view;

import com.example.platformerplain.data.Constants;
import com.example.platformerplain.controller.TransitionScreenController;
import com.example.platformerplain.model.GameModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class shows the screen that transitions the player from one level to the next.
 *
 * @author Zelin Xia
 * @date 2024/12/15
 */
public class TransitionScreen implements Screen {

    /**
     * Loads and displays the transition screen on the specified stage.
     *
     * @param primaryStage The stage to display the transition screen on.
     */
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Transition.fxml"));
            Parent transitionScreen = loader.load();
            Scene transitionScene = new Scene(transitionScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            TransitionScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            controller.setScore(GameModel.getCurrentScore());
            controller.setKilled(GameModel.getCurrentKilled());
            controller.setTime(GameModel.getGameTime());


            primaryStage.setTitle("FUGAKU: Level " + GameModel.getCurrentLevel() + " Completed!");
            primaryStage.setScene(transitionScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
