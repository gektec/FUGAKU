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

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
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
    private Stage primaryStage;
    private ScreenManager screenManager;  // ScreenManager instance

    private Timeline gameLoop;
    private Button pauseMenu = new Button();
    private boolean isPaused = false;

    private long startTime = 0;  // To store the start time
    private long elapsedTime = 0; // Used to store accumulated time
    private long lastUpdateTime = 0; // To keep track of the last update time



    public static void main(String[] args) {
        launch(args);
    }



    private void togglePauseMenu() {
        if(!isPaused) {
            stopGameLoop(); // stop game loop
            ScreenManager.getInstance(primaryStage).showScreen(new PauseScreen());
            isPaused = true;
        }
    }

    public long getTotalTime(){return totalTime;};

    public int getCurrentScore() {
        return currentScore;
    }

    public int getCurrentKilled() {
        return killedEnemy;
    }

    public int getFinalScore() {
        return finalScore;
    }


    public static Main getInstance() {
        return instance;
    }

    public static ArrayList<Entity> getCollidableMap() {
        return collidableMap;
    }

    public static ArrayList<Enemy> getEnemyMap() {
        return enemyMap;
    }


    public void removeEnemy(Enemy enemy) {
        toRemove.add(enemy);
        gameRoot.getChildren().remove(enemy.canvas());
        killedEnemy++;
    }


    public boolean getDebugMode() {
        return isDebugMode;
    }

    public void setDebugMode(boolean b) {
        isDebugMode = b;
    }

    public Pane getGameRoot() {
        return gameRoot;
    }
}
