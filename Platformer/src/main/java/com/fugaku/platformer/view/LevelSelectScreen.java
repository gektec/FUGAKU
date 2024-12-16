package com.fugaku.platformer.view;

import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.controller.LevelSelectScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class represents the screen where players can select the level they want to play.
 *
 * @author Zelin Xia
 * @date 2024/12/9
 */
public class LevelSelectScreen implements Screen {

    /**
     * Loads and displays the level selection screen on the specified stage.
     * @param primaryStage The stage to display the level selection screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fugaku/platformer/LevelSelect.fxml"));
            Parent selectScreen = loader.load();
            Scene selectScene = new Scene(selectScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            LevelSelectScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("FUGAKU: Select Level");
            primaryStage.setScene(selectScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
