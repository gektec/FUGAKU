package com.fugaku.platformer.view;

import com.fugaku.platformer.controller.OptionScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

            OptionScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            Pane root = (Pane) primaryStage.getScene().getRoot();
            root.getChildren().add(optionScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
