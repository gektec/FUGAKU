package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import com.example.platformerplain.ScreenManager;
import com.example.platformerplain.View.GameScreen;
import com.example.platformerplain.View.PauseScreen;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.MovePlayer;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.platformerplain.View.GameScreen.keys;

public class GameScreenController {

    //Main

    private static Main instance;
    private static Stage primaryStage;
    private ScreenManager screenManager;  // ScreenManager instance

    private Timeline gameLoop;
    private Button pauseMenu = new Button();
    private static boolean isPaused = false;

    private long startTime = 0;  // To store the start time
    private long elapsedTime = 0; // Used to store accumulated time
    private long lastUpdateTime = 0; // To keep track of the last update time


    public static void setKeys(Scene gameScene) {
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    }



    public static void togglePauseMenu() {
        if(!isPaused) {
            GameModel.stopGameLoop(); // stop game loop
            ScreenManager.getInstance(Main.primaryStage).showScreen(new PauseScreen());
            isPaused = true;
        }
    }
}
