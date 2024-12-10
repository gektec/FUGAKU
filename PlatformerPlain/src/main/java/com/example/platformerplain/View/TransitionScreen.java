package com.example.platformerplain.View;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.TransitionScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class shows the screen that transitions the player from one level to the next.
 */
public class TransitionScreen implements Screen {

    /**
     * Loads and displays the transition screen on the specified stage.
     * @param primaryStage The stage to display the transition screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Transition.fxml"));
            Parent transitionScreen = loader.load();
            Scene transitionScene = new Scene(transitionScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);

            TransitionScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("Congratulations on passing!");
            primaryStage.setScene(transitionScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}