package com.example.platformerplain.controller;

import javafx.scene.Scene;

import static com.example.platformerplain.model.GameModel.keys;

/**
 * This class serves as the controller for the game screen.
 * It manages the input handling related to key events to facilitate player interactions.
 *
 * @author Changyu Li
 * @date 2024/12/14
 */
public class GameScreenController {

    /**
     * Sets up key event handlers for the specified game scene.
     *
     * @param gameScene the Scene object representing the game screen
     *                  where key events are to be captured
     */
    public static void setKeys(Scene gameScene) {
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    }



}
