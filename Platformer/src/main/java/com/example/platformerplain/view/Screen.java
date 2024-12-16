package com.example.platformerplain.view;

import javafx.stage.Stage;

/**
 * Interface representing a screen in the application.
 * Implementations of this interface define how various screens
 * are displayed within the application.
 */
public interface Screen {

    /**
     * Displays the screen on the specified primary stage.
     *
     * @param primaryStage the primary stage on which the screen will be shown
     */
    void show(Stage primaryStage);
}
