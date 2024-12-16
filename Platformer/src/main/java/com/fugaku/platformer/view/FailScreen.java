package com.fugaku.platformer.view;

import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.controller.FailScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class represents the screen that is shown to players when they fail a level.
 *
 * @author Zelin Xia
 * @date 2024/12/9
 */
public class FailScreen implements Screen {

    /**
     * Loads and displays the fail screen on the specified stage.
     * @param primaryStage The stage to display the fail screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fugaku/platformer/Fail.fxml"));
            Parent failScreen = loader.load();
            Scene failScene = new Scene(failScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            FailScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("FUGAKU: Failed!");
            primaryStage.setScene(failScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
