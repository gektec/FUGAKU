package com.example.platformerplain.view;

import com.example.platformerplain.Constants;
import com.example.platformerplain.controller.MenuScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class manages the main menu screen of the game.
 */
public class MenuScreen implements Screen {

    /**
     * Loads and displays the main menu screen on the specified stage.
     * @param primaryStage The stage to display the main menu screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Menu.fxml"));
            Parent startScreen = loader.load();
            Scene startScene = new Scene(startScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            MenuScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("FUGAKU");
            primaryStage.setScene(startScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
