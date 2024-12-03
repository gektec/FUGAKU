package com.example.platformerplain.Screen;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.FailScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FailScreen implements Screen {

    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Fail.fxml"));
            Parent failScreen = loader.load();
            Scene failScene = new Scene(failScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);

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
