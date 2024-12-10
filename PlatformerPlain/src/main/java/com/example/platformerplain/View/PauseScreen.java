package com.example.platformerplain.View;

import com.example.platformerplain.Controller.PauseScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class controls the pause screen, allowing the game to be paused and resumed.
 */
public class PauseScreen implements Screen {

    /**
     * Loads and displays the pause screen, adding it to the current scene graph.
     * @param primaryStage The stage to which the pause screen should be added.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Pause.fxml"));
            Parent pauseScreen = loader.load();

            PauseScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            Pane root = (Pane) primaryStage.getScene().getRoot();
            root.getChildren().add(pauseScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
