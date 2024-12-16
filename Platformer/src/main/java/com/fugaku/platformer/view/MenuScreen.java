package com.fugaku.platformer.view;

import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.controller.MenuScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class manages the main menu screen of the game.
 *
 * @author Zelin Xia
 * @date 2024/12/9
 */
public class MenuScreen implements Screen {

    /**
     * Loads and displays the main menu screen on the specified stage.
     * @param primaryStage The stage to display the main menu screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fugaku/platformer/Menu.fxml"));
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
