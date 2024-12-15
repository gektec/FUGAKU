package com.example.platformerplain.controller;

import javafx.scene.Scene;

import static com.example.platformerplain.model.GameModel.keys;

public class GameScreenController {

    public static void setKeys(Scene gameScene) {
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    }

}
