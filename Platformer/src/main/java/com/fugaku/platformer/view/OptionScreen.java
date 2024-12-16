package com.fugaku.platformer.view;

import com.fugaku.platformer.controller.OptionScreenController;
import com.fugaku.platformer.data.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class controls the option screen, allowing change background color
 *
 * @author Zelin Xia
 * @date 2024/12/16
 */
public class OptionScreen implements Screen {

    /**
     * Loads and displays the pause screen, adding it to the current scene graph.
     * @param primaryStage The stage to which the pause screen should be added.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fugaku/platformer/Option.fxml"));
            Parent optionScreen = loader.load();
            Scene optionScene = new Scene(optionScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            OptionScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("FUGAKU: Option");
            primaryStage.setScene(optionScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
