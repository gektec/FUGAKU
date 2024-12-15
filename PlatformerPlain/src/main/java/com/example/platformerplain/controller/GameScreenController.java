package com.example.platformerplain.controller;

import javafx.scene.Scene;

import static com.example.platformerplain.model.GameModel.keys;

/**
 * This class serves as the controller for the game screen in the platformer game.
 * It manages the input handling related to key events to facilitate player interactions.
 */
public class GameScreenController {

    /**
     * Sets up key event handlers for the specified game scene.
     * This method registers key pressed and key released events to track the
     * state of keys being pressed. The states are stored in a static map
     * defined in the GameModel class.
     *
     * @param gameScene the Scene object representing the game screen
     *                  where key events are to be captured
     */
    public static void setKeys(Scene gameScene) {
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    }

}
