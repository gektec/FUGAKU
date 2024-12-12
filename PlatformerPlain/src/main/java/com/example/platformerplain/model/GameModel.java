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

    private List<Entity> toRemove = new ArrayList<>();

    // Debug
    private boolean isDebugMode = true;

    private long lastTime = 0;
    private int frameCount = 0;

    private int timeStep = 0;

    // Main

    private int currentLevel = 0;
    private int currentScore = 0;
    private int finalScore = 0;
    private int killedEnemy = 0;
    private long totalTime = 0;

    private Timeline gameLoop;
    private Button pauseMenu = new Button();
    private boolean isPaused = false;

    public static long startTime = 0;  // To store the start time
    public static long elapsedTime = 0; // Used to store accumulated time
    private long lastUpdateTime = 0; // To keep track of the last update time

    // Adding an Interpreter
    private ScoreInterpreter scoreInterpreter = new ScoreInterpreter();

    private GameModel() {
        // Private constructor to prevent instantiation
    }

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

    public void startGame(Stage primaryStage, int level) {
        this.currentLevel = level;
        this.isPaused = false;
        GameScreen.initContent();
        GameScreen.startLevel();
        primaryStage.setScene(GameScreen.getGameScene());
        startGameLoop();
    }

    private void startGameLoop() {
        lastUpdateTime = System.currentTimeMillis(); // Initialize last update time
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60), event -> {
            long currentTime = System.currentTimeMillis();
            elapsedTime += currentTime - lastUpdateTime; // Update elapsed time based on current frame
            lastUpdateTime = currentTime; // Update last update time

            update();

            if (isDebugMode) updateLabels();

            GameScreen.getEnemyMap().removeAll(toRemove);
            GameScreen.getCollidableMap().removeAll(toRemove);
        });

        gameLoop = new Timeline(frame);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void update() {
        GameScreen.getPlayer().update();
        if (GameScreen.getEnemyMap() != null) {
            for (Enemy enemy : GameScreen.getEnemyMap()) {
                enemy.update();
            }
        }
    }

    private void updateLabels() {
        updateFramerate();
        updatePlayerSpeed();
        updateMoveState();
        updateTime();
    }

    private void updateFramerate() {
        long currentTime = System.nanoTime();
        if (currentTime - lastTime >= 1_000_000_000) {
            GameScreen.getFramerateLabel().setText("FPS: " + frameCount);
            frameCount = 0;
            lastTime = currentTime;
        }
        frameCount++;
    }

    private void updateTime() {
        if (!isPaused) {
            long seconds = (elapsedTime / 1000) % 60; // Convert milliseconds to seconds
            long minutes = (elapsedTime / 1000) / 60; // Calculate minutes
            GameScreen.getTimeLabel().setText(String.format("Time: %02d:%02d", minutes, seconds)); // Update Label
        }
    }

    private void updateMoveState() {
        MoveState moveState = GameScreen.getMovePlayerLogic().getMoveStatus().moveState;
        GameScreen.getMoveStateLabel().setText("Move State: " + moveState);
    }

    private void updatePlayerSpeed() {
        float[] speed = GameScreen.getMovePlayerLogic().getMoveStatus().velocity.get();
        GameScreen.getPlayerSpeedLabel().setText("Speed: " + Arrays.toString(speed));
        GameScreen.getSpeedX().getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[0])));
        GameScreen.getSpeedY().getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[1])));

        // Keep only the last 60 data points
        if (GameScreen.getSpeedX().getData().size() > 60) {
            GameScreen.getSpeedX().getData().remove(0);
            GameScreen.getSpeedY().getData().remove(0);
        }

        // Update x-axis bounds
        NumberAxis xAxis = (NumberAxis) GameScreen.getSpeedChart().getXAxis();
        NumberAxis yAxis = (NumberAxis) GameScreen.getSpeedChart().getYAxis();
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(timeStep - 60);
        xAxis.setUpperBound(timeStep);
        yAxis.setUpperBound(20);
    }

    public void transitionToNextLevel() {
        stopGameLoop();
        if(currentLevel == 1) {
            calculateCurrentScore();
            ScreenManager.showScreen(new TransitionScreen());
        } else{
            calculateCurrentScore();
            ScreenManager.showScreen(new CompletedScreen());
        }
    }

    public void startNextLevel(){
        startTime = System.currentTimeMillis(); // Reset start time
        elapsedTime = 0; // Reset elapsed time
        currentLevel++;
        GameScreen.startLevel();
        Main.getPrimaryStage().setScene(GameScreen.getGameScene());
        startGameLoop();
    }

    public void restartLevel(){
        isPaused = false;
        startTime = System.currentTimeMillis(); // Reset start time
        elapsedTime = 0; // Reset elapsed time
        GameScreen.startLevel();
        Main.getPrimaryStage().setScene(GameScreen.getGameScene());
        GameScreen.getGameRoot().setLayoutY(-(LevelData.getLevelInformation.getLevelHeight() - Constants.WINDOW_HEIGHT));
        GameScreen.getGameRoot().setLayoutX(0);
        startGameLoop();
    }

    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    public void exitGame() {
        ScreenManager.showScreen(new FailScreen());
    }

    public void resumeGame() {
        isPaused = false;
        startTime = System.currentTimeMillis() - elapsedTime; // Ensure time continuity
        startGameLoop();
    }

    // Rewrite the currentScore method and use the interpreter mode
    public void calculateCurrentScore() {
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


    public List<Entity> getCollidableMap() {
        return GameScreen.getCollidableMap();
    }


    public List<Enemy> getEnemyMap() {
        return GameScreen.getEnemyMap();
    }


    public void removeEnemy(Enemy enemy) {
        toRemove.add(enemy);
        GameScreen.getGameRoot().getChildren().remove(enemy.canvas());
        killedEnemy++;
    }


    public long getTotalTime(){
        return totalTime;
    }


    public int getCurrentScore() {
        return currentScore;
    }


    public int getCurrentKilled() {
        return killedEnemy;
    }


    public int getFinalScore() {
        return finalScore;
    }

    public int getCurrentLevel(){
        return currentLevel;
    }



    public boolean isDebugMode() {
        return isDebugMode;
    }


    public void setDebugMode(boolean debugMode) {
        this.isDebugMode = debugMode;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getElapsedTime(){
        return elapsedTime;
    }

    public void togglePauseMenu() {
        if (!isPaused) {
            isPaused = true; // Set the pause flag to true
            stopGameLoop(); // stop game loop
            ScreenManager.showScreen(new PauseScreen());
        }
    }
}
