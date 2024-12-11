package com.example.platformerplain;

import com.example.platformerplain.View.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static Main instance;
    public static Stage primaryStage;
    private ScreenManager screenManager;  // ScreenManager instance


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;

        instance = this;

        // Initialize ScreenManager
        screenManager = ScreenManager.getInstance(primaryStage);

        // Show the start screen
        screenManager.showScreen(new MenuScreen());

        primaryStage.setWidth(Constants.WINDOW_WIDTH);
        primaryStage.setHeight(Constants.WINDOW_HEIGHT);
        primaryStage.setResizable(false);

    }


    public static Main getInstance() {
        return instance;
    }
}
