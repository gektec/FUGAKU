package com.example.platformerplain.Screen;
import javafx.stage.Stage;

public class ScreenManager {

    private static ScreenManager instance;  // Singleton instance
    private Stage primaryStage;

    private ScreenManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Singleton pattern
    public static ScreenManager getInstance(Stage primaryStage) {
        if (instance == null) {
            instance = new ScreenManager(primaryStage);
        }
        return instance;
    }

    public void showScreen(Screen screen) {
        screen.show(primaryStage);
    }
}
