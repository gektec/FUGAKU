package com.example.platformerplain;

import com.example.platformerplain.view.Screen;
import javafx.stage.Stage;

public class ScreenManager {

    private static ScreenManager instance;  // Singleton instance
    private static Stage primaryStage;

    private ScreenManager(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    // Singleton pattern
    /**
     * Gets the singleton instance of ScreenManager.
     * @param primaryStage the primary stage to be managed.
     * @return the singleton instance of ScreenManager.
     */
    public static ScreenManager getInstance(Stage primaryStage) {
        if (instance == null) {
            instance = new ScreenManager(primaryStage);
        }
        return instance;
    }

    /**
     * Displays the specified screen on the primary stage.
     * @param screen the screen to be shown.
     */
    public static void showScreen(Screen screen) {
        screen.show(Main.primaryStage);
    }
}
