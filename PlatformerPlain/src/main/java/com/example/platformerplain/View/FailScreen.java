package com.example.platformerplain.View;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.FailScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class represents the screen that is shown to players when they fail a level.
 */
public class FailScreen implements Screen {

    /**
     * Loads and displays the fail screen on the specified stage.
     * @param primaryStage The stage to display the fail screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Fail.fxml"));
            Parent failScreen = loader.load();
            Scene failScene = new Scene(failScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            FailScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("EXIT AND TRY AGAIN!");
            primaryStage.setScene(failScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
