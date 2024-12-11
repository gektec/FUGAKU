package com.example.platformerplain.model;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Controller.GameScreenController;
import com.example.platformerplain.Main;
import com.example.platformerplain.ScreenManager;
import com.example.platformerplain.View.*;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.MovePlayer;
import com.example.platformerplain.move.MoveState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameModel {
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private static ArrayList<Entity> collidableMap = new ArrayList<>();
    private static ArrayList<Enemy> enemyMap = new ArrayList<>();
    private static ArrayList<Goal> goalMap = new ArrayList<>();
    private static ArrayList<Spike> spikeMap = new ArrayList<>();
    private static ArrayList<Ladder> ladderMap = new ArrayList<>();

    private static List<Entity> toRemove = new ArrayList<>();
    private Pane appRoot = new Pane();
    private static Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private Pane backgroundRoot = new Pane();

    private static Entity player;
    private static int levelWidth = -1;
    private static int levelHeight = -1;
    public static MovePlayer movePlayerLogic;
    private MoveEnemy moveEnemyLogic;
    private Move move;
    private static Scene gameScene;

    //Debug

    protected static boolean isDebugMode = true;

    private static Label framerateLabel = new Label();
    private static long lastTime = 0;
    private static int frameCount = 0;

    private static Label moveStateLabel = new Label();

    private static Label playerSpeedLabel = new Label();

    private static Label timeLabel = new Label();

    private static LineChart<Number, Number> speedChart;
    private static XYChart.Series<Number, Number> speedX;
    private static XYChart.Series<Number, Number> speedY;
    private static int timeStep = 0;

    //Main

    static int currentLevel = 0;
    public static int currentScore = 0;
    public static int finalScore = 0;
    public static int killedEnemy = 0;
    public static long totalTime = 0;

    private static Main instance;
    private static Stage primaryStage;
    private static ScreenManager screenManager;  // ScreenManager instance

    private static Timeline gameLoop;
    private static Button pauseMenu = new Button();
    private static boolean isPaused = false;

    private static long startTime = 0;  // To store the start time
    private static long elapsedTime = 0; // Used to store accumulated time
    private static long lastUpdateTime = 0; // To keep track of the last update time






    private static void initContent() {
        if(isDebugMode) {
            framerateLabel.setTextFill(Color.WHITE);
            framerateLabel.setFont(new Font(18));
            framerateLabel.setTranslateX(10);
            framerateLabel.setTranslateY(10);

            playerSpeedLabel.setTextFill(Color.WHITE);
            playerSpeedLabel.setFont(new Font(18));
            playerSpeedLabel.setTranslateX(10);
            playerSpeedLabel.setTranslateY(30);

            moveStateLabel.setTextFill(Color.WHITE);
            moveStateLabel.setFont(new Font(18));
            moveStateLabel.setTranslateX(10);
            moveStateLabel.setTranslateY(50);

            // Add a Time Label
            timeLabel.setTextFill(Color.WHITE);
            timeLabel.setFont(new Font(18));
            timeLabel.setTranslateX(10);
            timeLabel.setTranslateY(70);

            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Time");
            yAxis.setLabel("Speed");

            speedChart = new LineChart<>(xAxis, yAxis);
            speedChart.setTitle("Player Speed Over Time");
            speedX = new XYChart.Series<>();
            speedX.setName("Speed X");
            speedY = new XYChart.Series<>();
            speedY.setName("Speed Y");
            speedChart.getData().addAll(speedX, speedY);
            speedChart.setTranslateX(10);
            speedChart.setTranslateY(90);
            speedChart.setPrefSize(400, 300);

        }


        // Add a Pause Button
        pauseMenu.setTextFill(Color.BLACK);
        pauseMenu.setFont(new Font(18));
        pauseMenu.setTranslateX(Constants.BACKGROUND_WIDTH - 100);
        pauseMenu.setTranslateY(30);
        pauseMenu.setText("Pause");

        pauseMenu.setOnAction(event -> GameScreenController.togglePauseMenu());
    }



    public static void startGame(Stage primaryStage) {
        currentLevel = 1;
        isPaused = false;
        initContent();
        GameScreen.startLevel();
        primaryStage.setScene(gameScene);
        startGameLoop();
    }

    public static void startLevel2(Stage primaryStage) {
        currentLevel = 2;
        isPaused = false;
        initContent();
        GameScreen.startLevel();
        primaryStage.setScene(gameScene);
        startGameLoop();
    }


    private static void startGameLoop() {
        lastUpdateTime = System.currentTimeMillis(); // Initialize last update time
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60), event -> {
            long currentTime = System.currentTimeMillis();
            elapsedTime += currentTime - lastUpdateTime; // Update elapsed time based on current frame
            lastUpdateTime = currentTime; // Update last update time

            update();
            if (isDebugMode) updateLables();

            enemyMap.removeAll(toRemove);
            collidableMap.removeAll(toRemove);
        });

        gameLoop = new Timeline(frame);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }


    private static void update() {
        player.update();
        if (enemyMap != null) {
            for (Enemy enemy : enemyMap) {
                enemy.update();
            }
        }
    }

    private static void updateLables() {
        updateFramerate();
        updatePlayerSpeed();
        updateMoveState();
        updateTime();
    }

    private static void updateFramerate() {
        long currentTime = System.nanoTime();
        if (currentTime - lastTime >= 1_000_000_000) {
            framerateLabel.setText("FPS: " + frameCount);
            frameCount = 0;
            lastTime = currentTime;
        }
        frameCount++;
    }

    private static void updateTime() {
        if (!isPaused) {
            long seconds = (elapsedTime / 1000) % 60; // Convert milliseconds to seconds
            long minutes = (elapsedTime / 1000) / 60; // Calculate minutes
            timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)); // Update Label
        }
    }



    private static void updateMoveState() {
        MoveState moveState = movePlayerLogic.getMoveStatus().moveState;
        moveStateLabel.setText("Move State: " + moveState);
    }

    private static void updatePlayerSpeed() {
        float[] speed = movePlayerLogic.getMoveStatus().velocity.get();
        playerSpeedLabel.setText("Speed: " + Arrays.toString(speed));
        speedX.getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[0])));
        speedY.getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[1])));

        // Keep only the last 60 data points
        if (speedX.getData().size() > 60) {
            speedX.getData().removeFirst();
            speedY.getData().removeFirst();
        }

        // Update x-axis bounds
        NumberAxis xAxis = (NumberAxis) speedChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) speedChart.getYAxis();
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(timeStep - 60);
        xAxis.setUpperBound(timeStep);
        yAxis.setUpperBound(20);
    }


    public static void transitionToNextLevel() {
        stopGameLoop();
        if(currentLevel == 1) {
            currentScore(elapsedTime);
            screenManager.showScreen(new TransitionScreen());
        }else{
            currentScore(elapsedTime);
            screenManager.showScreen(new CompletedScreen());
        }
    }

    public static void startNextLevel(){
        startTime = System.currentTimeMillis(); // Reset start time
        elapsedTime = 0; // Reset elapsed time
        currentLevel = 2;
        GameScreen.startLevel();
        primaryStage.setScene(gameScene);
        startGameLoop();
    }

    public static void restartLevel(){
        isPaused = false;
        startTime = System.currentTimeMillis(); // Reset start time
        elapsedTime = 0; // Reset elapsed time
        GameScreen.startLevel();
        primaryStage.setScene(gameScene);
        gameRoot.setLayoutY(-(levelHeight - Constants.BACKGROUND_HEIGHT));
        gameRoot.setLayoutX(0);
        startGameLoop();
    }

    public static void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }

    }

    public static void exitGame() {
        screenManager.showScreen(new FailScreen());
    }



    public static void resumeGame() {
        isPaused = false;
        startTime = System.currentTimeMillis() - elapsedTime;// Ensure time continuity
        startGameLoop();
    }

    public static void currentScore(long elapsedTime) {
        int maxScore = 1000;
        int penaltyPerSecond = 10;

        // Calculate elapsed time in seconds for the score
        long secondsElapsed = elapsedTime / 1000;
        totalTime += secondsElapsed;

        currentScore = (int) (maxScore - (penaltyPerSecond * secondsElapsed) + (killedEnemy * 200));

        // Ensure currentScore does not drop below 0
        if (currentScore < 0) {
            currentScore = 0;
        }

        finalScore += currentScore;
    }

    public static ArrayList<Entity> getCollidableMap() {
        return collidableMap;
    }

    public static ArrayList<Enemy> getEnemyMap() {
        return enemyMap;
    }


    public static void removeEnemy(Enemy enemy) {
        toRemove.add(enemy);
        gameRoot.getChildren().remove(enemy.canvas());
        killedEnemy++;
    }

    public static long getTotalTime(){return totalTime;};

    public static int getCurrentScore() {
        return currentScore;
    }

    public static int getCurrentKilled() {
        return killedEnemy;
    }

    public static int getFinalScore() {
        return finalScore;
    }


    public static boolean getDebugMode() {
        return isDebugMode;
    }

    public static void setDebugMode(boolean b) {
        isDebugMode = b;
    }
}
