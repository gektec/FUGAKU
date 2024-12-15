package com.example.platformerplain.model;

import com.example.platformerplain.*;
import com.example.platformerplain.entities.moveable.Enemy;
import com.example.platformerplain.entities.tile.Goal;
import com.example.platformerplain.entities.tile.Ladder;
import com.example.platformerplain.entities.tile.Spike;
import com.example.platformerplain.view.*;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.model.Interpreter.ScoreContext;
import com.example.platformerplain.model.Interpreter.ScoreInterpreter;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.move.MovePlayer;
import com.example.platformerplain.move.data.MoveState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameModel {
    private static GameModel instance;

    private static List<GameModelObserver> observers = new CopyOnWriteArrayList<>();

    private static List<Entity> toRemove = new ArrayList<>();

    public static HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private static ArrayList<Entity> collidableMap = new ArrayList<>();
    private static ArrayList<Enemy> enemyMap = new ArrayList<>();
    private static ArrayList<Goal> goalMap = new ArrayList<>();
    private static ArrayList<Spike> spikeMap = new ArrayList<>();
    private static ArrayList<Ladder> ladderMap = new ArrayList<>();

    public static Entity player;
    private static MovePlayer movePlayerLogic;

    // Debug
    private static boolean isDebugMode = true;

    private static long lastTime = 0;
    private static int frameCount = 0;
    private static int timeStep = 0;

    // Main
    private static int currentLevel = 0;
    private static int currentScore = 0;
    private static int finalScore = 0;
    private static int killedEnemy = 0;
    private static long totalTime = 0;
    private static int baseScore = 1000;

    private static Timeline gameLoop;
    private static boolean isPaused = false;

    public static long elapsedTime = 0; // Used to store accumulated time
    private static long lastUpdateTime = 0; // To keep track of the last update time

    // Adding an Interpreter
    private static ScoreInterpreter scoreInterpreter = new ScoreInterpreter();

    private GameModel() {
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

    // Registering Observers
    public static void addObserver(GameModelObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    // Unregistering Observers
    public void removeObserver(GameModelObserver observer) {
        observers.remove(observer);
    }

    public static void startGame(Stage primaryStage, int level) {
        clearData();
        elapsedTime = 0;
        currentLevel = level;
        isPaused = false;
        GameScreen.initContent();
        initLevel();
        GameScreen.startLevel();
        primaryStage.setScene(GameScreen.getGameScene());

        Move move = new Move(collidableMap);
        primaryStage.setTitle("FUGAKU: Level " + level);
        startGameLoop();
    }

    private static void initLevel() {

        // Use LevelInitializer to set up the level
        LevelInitializer levelInitializer = new LevelInitializer(
                keys,
                GameScreen.getGameRoot(),
                GameScreen.getUIRoot(),
                GameScreen.getBackgroundRoot(),
                collidableMap,
                enemyMap,
                spikeMap,
                ladderMap
        );
        player = levelInitializer.generateLevel(currentLevel);

        // If player is not null, continue setup
        if (player != null) {
            movePlayerLogic = new MovePlayer(
                    player,
                    collidableMap,
                    enemyMap,
                    ladderMap,
                    spikeMap,
                    LevelData.getLevelInformation.getLevelWidth(),
                    keys
            );
        }
        else System.err.println("Cannot generate player!");
    }

    public static void clearData(){
        keys.clear();
        collidableMap.clear();
        enemyMap.clear();
        toRemove.clear();
    }

    private static void startGameLoop() {
        lastUpdateTime = System.currentTimeMillis(); // Initialize last update time
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60), event -> {
            long currentTime = System.currentTimeMillis();
            elapsedTime += currentTime - lastUpdateTime; // Update elapsed time based on current frame
            lastUpdateTime = currentTime; // Update last update time

            update();

            if (isDebugMode) updateLabels();

            enemyMap.removeAll(toRemove);
            collidableMap.removeAll(toRemove);
            toRemove.clear(); // Clear the pending removal list to avoid duplicate removals
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

    private static void updateLabels() {
        updateFramerate();
        updateMoveState();
        updateTime();
        updatePosition();
        updatePlayerSpeed();
    }

    private static void updateFramerate() {
        long currentTime = System.nanoTime();
        if (currentTime - lastTime >= 1_000_000_000) {
            GameScreen.getFramerateLabel().setText("FPS: " + frameCount);
            frameCount = 0;
            lastTime = currentTime;
        }
        frameCount++;
    }

    private static void updateTime() {
        if (!isPaused) {
            long seconds = (elapsedTime / 1000) % 60; // Convert milliseconds to seconds
            long minutes = (elapsedTime / 1000) / 60; // Calculate minutes
            GameScreen.getTimeLabel().setText(String.format("Time: %02d:%02d", minutes, seconds)); // Update Label
        }
    }

    private static void updateMoveState() {
        MoveState moveState = getMovePlayerLogic().getMoveStatus().getState();
        GameScreen.getMoveStateLabel().setText("Move State: " + moveState);
    }

    private static void updatePosition() {
        double x = player.hitBox().getTranslateX();
        double y = player.hitBox().getTranslateY();
        GameScreen.getPositionLabel().setText("Position: (" + x + ", " + y + ")");
    }

    private static void updatePlayerSpeed() {
        double[] speed = getMovePlayerLogic().getMoveStatus().velocity.get();
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

    public static void transitionToNextLevel() {
        stopGameLoop();
        if(currentLevel <= 2) {
            calculateCurrentScore();
            ScreenManager.showScreen(new TransitionScreen());
        } else{
            calculateCurrentScore();
            ScreenManager.showScreen(new CompletedScreen());
        }
    }

    public static void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    public static void exitGame() {
        killedEnemy = 0;
        ScreenManager.showScreen(new FailScreen());
    }

    public static void resumeGame() {
        isPaused = false;
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

        // Notification of score changes
        notifyScoreChanged(currentScore);
    }


    public static List<Entity> getCollidableMap() {
        return collidableMap;
    }


    public static List<Enemy> getEnemyMap() {
        return enemyMap;
    }


    public static void removeEnemy(Enemy enemy) {
        toRemove.add(enemy);
        GameScreen.getGameRoot().getChildren().remove(enemy.canvas());
        killedEnemy++;
        baseScore += 200;
        notifyScoreChanged(baseScore);
        // Notify of changes in the number of enemies killed
        notifyEnemyKilled(killedEnemy);
    }

    public static MovePlayer getMovePlayerLogic() {
        return movePlayerLogic;
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

    public static int getCurrentLevel(){
        return currentLevel;
    }



    public static boolean isDebugMode() {
        return isDebugMode;
    }


    public static void setDebugMode(boolean debugMode) {
        isDebugMode = debugMode;
    }


    public static long getGameTime(){
        return (int)(elapsedTime/1000);
    }

    private static void notifyScoreChanged(int newScore) {
        for (GameModelObserver observer : observers) {
            observer.onScoreChanged(newScore);
        }
    }

    private static void notifyEnemyKilled(int totalKilled) {
        for (GameModelObserver observer : observers) {
            observer.onEnemyKilled(totalKilled);
        }
    }

    public static void togglePauseMenu() {
        if (!isPaused) {
            isPaused = true; // Set the pause flag to true
            stopGameLoop(); // stop game loop
            ScreenManager.showScreen(new PauseScreen());
        }
    }
}
