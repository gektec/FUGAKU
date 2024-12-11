package com.example.platformerplain;

import com.example.platformerplain.View.*;
import com.example.platformerplain.entities.Enemy;
import com.example.platformerplain.entities.Entity;

import com.example.platformerplain.entities.*;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.MovePlayer;
import com.example.platformerplain.move.MoveState;
import com.example.platformerplain.texture.ImageScaler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    private Pane gameRoot = new Pane();
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

        primaryStage.setWidth(Constants.BACKGROUND_WIDTH);
        primaryStage.setHeight(Constants.BACKGROUND_HEIGHT);
        primaryStage.setResizable(false);

    }


    public static Main getInstance() {
        return instance;
    }



    public Pane getGameRoot() {
        return gameRoot;
    }
}
