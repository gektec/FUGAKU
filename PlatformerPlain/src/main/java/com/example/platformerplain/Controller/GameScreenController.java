package com.example.platformerplain.Controller;

import com.example.platformerplain.Main;
import com.example.platformerplain.ScreenManager;
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

public class GameScreenController {
    private static HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private static ArrayList<Entity> collidableMap = new ArrayList<>();
    private static ArrayList<Enemy> enemyMap = new ArrayList<>();
    private static ArrayList<Goal> goalMap = new ArrayList<>();
    private static ArrayList<Spike> spikeMap = new ArrayList<>();
    private static ArrayList<Ladder> ladderMap = new ArrayList<>();

    private List<Entity> toRemove = new ArrayList<>();
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private Pane backgroundRoot = new Pane();

    private Entity player;
    private static int levelWidth = -1;
    private static int levelHeight = -1;
    public MovePlayer movePlayerLogic;
    private MoveEnemy moveEnemyLogic;
    private Move move;
    private Scene gameScene;

    //Debug

    protected boolean isDebugMode = true;

    private Label framerateLabel = new Label();
    private long lastTime = 0;
    private int frameCount = 0;

    private Label moveStateLabel = new Label();

    private Label playerSpeedLabel = new Label();

    private Label timeLabel = new Label();

    private LineChart<Number, Number> speedChart;
    private XYChart.Series<Number, Number> speedX;
    private XYChart.Series<Number, Number> speedY;
    private int timeStep = 0;

    //Main

    static int currentLevel = 0;
    public static int currentScore = 0;
    public static int finalScore = 0;
    public static int killedEnemy = 0;
    public static long totalTime = 0;

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
            ScreenManager.getInstance(primaryStage).showScreen(new PauseScreen());
            isPaused = true;
        }
    }
}
