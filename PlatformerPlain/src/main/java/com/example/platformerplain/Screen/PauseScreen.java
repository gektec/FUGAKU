package com.example.platformerplain.Screen;

import com.example.platformerplain.Controller.PauseScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class PauseScreen implements Screen {
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Pause.fxml"));
            Parent pauseScreen = loader.load();

            // set PauseScreenController
            PauseScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            // Add the pause interface to the root layout instead of replacing the entire scene
            Pane root = (Pane) primaryStage.getScene().getRoot();
            root.getChildren().add(pauseScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removePauseScreen(StackPane root) {
        // 可以使用此方法在需要的时候移除暂停菜单
        root.getChildren().removeIf(node -> node instanceof Parent);  // 这里可以根据需要指定具体类型
    }
}
