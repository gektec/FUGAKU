package com.example.platformerplain.model;

import com.example.platformerplain.Constants;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.Main;
import com.example.platformerplain.ScreenManager;
import com.example.platformerplain.View.*;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.move.MoveState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModel {
    private static GameModel instance;

    private static List<Entity> toRemove = new ArrayList<>();
    // Debug
    protected static boolean isDebugMode = true;

    private static long lastTime = 0;
    private static int frameCount = 0;

    private static int timeStep = 0;

    // Main

    public static int currentLevel = 0;
    public static int currentScore = 0;
    public static int finalScore = 0;
    public static int killedEnemy = 0;
    public static long totalTime = 0;

    private static Timeline gameLoop;
    private static Button pauseMenu = new Button();
    private static boolean isPaused = false;

    public static long startTime = 0;  // To store the start time
    public static long elapsedTime = 0; // Used to store accumulated time
    private static long lastUpdateTime = 0; // To keep track of the last update time

    // Adding an Interpreter
    private static ScoreInterpreter scoreInterpreter = new ScoreInterpreter();

    public static GameModel getInstance() {
        if (instance == null) {
            synchronized (GameModel.class) {
                if (instance == null) {
                    instance = new GameModel();
                }
            }
        }
        return instance;
    }

    public static void startGame(Stage primaryStage, int level) {
        currentLevel = level;
        isPaused = false;
        GameScreen.initContent();
        GameScreen.startLevel();
        primaryStage.setScene(GameScreen.gameScene);
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

            GameScreen.enemyMap.removeAll(toRemove);
            GameScreen.collidableMap.removeAll(toRemove);
        });

        gameLoop = new Timeline(frame);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private static void update() {
        GameScreen.player.update();
        if (GameScreen.enemyMap != null) {
            for (Enemy enemy : GameScreen.enemyMap) {
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
            GameScreen.framerateLabel.setText("FPS: " + frameCount);
            frameCount = 0;
            lastTime = currentTime;
        }
        frameCount++;
    }

    private static void updateTime() {
        if (!isPaused) {
            long seconds = (elapsedTime / 1000) % 60; // Convert milliseconds to seconds
            long minutes = (elapsedTime / 1000) / 60; // Calculate minutes
            GameScreen.timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)); // Update Label
        }
    }

    private static void updateMoveState() {
        MoveState moveState = GameScreen.movePlayerLogic.getMoveStatus().moveState;
        GameScreen.moveStateLabel.setText("Move State: " + moveState);
    }

    private static void updatePlayerSpeed() {
        float[] speed = GameScreen.movePlayerLogic.getMoveStatus().velocity.get();
        GameScreen.playerSpeedLabel.setText("Speed: " + Arrays.toString(speed));
        GameScreen.speedX.getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[0])));
        GameScreen.speedY.getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[1])));

        // Keep only the last 60 data points
        if (GameScreen.speedX.getData().size() > 60) {
            GameScreen.speedX.getData().removeFirst();
            GameScreen.speedY.getData().removeFirst();
        }

        // Update x-axis bounds
        NumberAxis xAxis = (NumberAxis) GameScreen.speedChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) GameScreen.speedChart.getYAxis();
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(timeStep - 60);
        xAxis.setUpperBound(timeStep);
        yAxis.setUpperBound(20);
    }

    public static void transitionToNextLevel() {
        stopGameLoop();
        if(currentLevel == 1) {
            calculateCurrentScore();
            ScreenManager.showScreen(new TransitionScreen());
        } else{
            calculateCurrentScore();
            ScreenManager.showScreen(new CompletedScreen());
        }
    }

    public static void startNextLevel(){
        startTime = System.currentTimeMillis(); // Reset start time
        elapsedTime = 0; // Reset elapsed time
        currentLevel ++;
        GameScreen.startLevel();
        Main.primaryStage.setScene(GameScreen.gameScene);
        startGameLoop();
    }

    public static void restartLevel(){
        isPaused = false;
        startTime = System.currentTimeMillis(); // Reset start time
        elapsedTime = 0; // Reset elapsed time
        GameScreen.startLevel();
        Main.primaryStage.setScene(GameScreen.gameScene);
        GameScreen.gameRoot.setLayoutY(-(LevelData.getLevelInformation.getLevelHeight() - Constants.WINDOW_HEIGHT));
        GameScreen.gameRoot.setLayoutX(0);
        startGameLoop();
    }

    public static void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    public static void exitGame() {
        ScreenManager.showScreen(new FailScreen());
    }

    public static void resumeGame() {
        isPaused = false;
        startTime = System.currentTimeMillis() - elapsedTime; // Ensure time continuity
        startGameLoop();
    }

    // Rewrite the currentScore method and use the interpreter mode
    public static void calculateCurrentScore() {
        int maxScore = 1000;
        int penaltyPerSecond = 10;
        long elapsedTimeSeconds = elapsedTime / 1000;

        // create context
        ScoreContext context = new ScoreContext(maxScore, penaltyPerSecond, elapsedTime, killedEnemy);

        // Interpret and calculate scores
        currentScore = scoreInterpreter.interpret(context);

        // Make sure currentScore is not less than 0
        if (currentScore < 0) {
            currentScore = 0;
        }

        finalScore += currentScore;
        totalTime += elapsedTimeSeconds;
    }

    public static ArrayList<Entity> getCollidableMap() {
        return GameScreen.collidableMap;
    }

    public static ArrayList<Enemy> getEnemyMap() {
        return GameScreen.enemyMap;
    }

    public static void removeEnemy(Enemy enemy) {
        toRemove.add(enemy);
        GameScreen.gameRoot.getChildren().remove(enemy.canvas());
        killedEnemy++;
    }

    public static long getTotalTime(){
        return totalTime;
    }

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

    // Toggle Pause Menu method to ensure that it cannot be clicked again when paused
    public static void togglePauseMenu() {
        if (!isPaused) {
            isPaused = true; // Set the pause flag to true
            GameModel.stopGameLoop(); // stop game loop
            ScreenManager.getInstance(Main.primaryStage).showScreen(new PauseScreen());
        }
    }
}
