package com.example.platformerplain;

import com.example.platformerplain.View.*;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private static Main instance;
    static Stage primaryStage;
    public static Font baseFont;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        instance = this;

        AssetManager.preloadAssets();

        // Show the start screen
        ScreenManager.showScreen(new MenuScreen());

        primaryStage.setWidth(Constants.WINDOW_WIDTH);
        primaryStage.setHeight(Constants.WINDOW_HEIGHT);
        primaryStage.setResizable(false);
    }

    public static Main getInstance() {
        return instance;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
