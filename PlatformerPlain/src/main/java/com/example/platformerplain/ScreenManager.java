package com.example.platformerplain;
import com.example.platformerplain.View.Screen;
import javafx.stage.Stage;

public class ScreenManager {

    private static ScreenManager instance;  // Singleton instance
    private static Stage primaryStage;

    private ScreenManager(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    // Singleton pattern
    public static ScreenManager getInstance(Stage primaryStage) {
        if (instance == null) {
            instance = new ScreenManager(primaryStage);
        }
        return instance;
    }

    public static void showScreen(Screen screen) {
        screen.show(Main.primaryStage);
    }
}
