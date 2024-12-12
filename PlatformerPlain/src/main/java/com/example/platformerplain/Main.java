package com.example.platformerplain;

import com.example.platformerplain.View.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static Main instance;
    static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        instance = this;

        // Initialize
        // ScreenManager instance

        // Show the start screen
        ScreenManager.showScreen(new MenuScreen());

        primaryStage.setWidth(Constants.WINDOW_WIDTH);
        primaryStage.setHeight(Constants.WINDOW_HEIGHT);
        primaryStage.setResizable(false);

        AssetManager.preloadAssets();

    }

    public static Main getInstance() {
        return instance;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
