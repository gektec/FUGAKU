package com.example.platformerplain.model;

import com.example.platformerplain.*;
import com.example.platformerplain.View.*;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.model.Interpreter.ScoreContext;
import com.example.platformerplain.model.Interpreter.ScoreInterpreter;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.move.MovePlayer;
import com.example.platformerplain.move.MoveState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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

    private List<GameModelObserver> observers = new CopyOnWriteArrayList<>();

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

    private long lastTime = 0;
    private int frameCount = 0;
    private int timeStep = 0;

    // Main
    private static int currentLevel = 0;
    private int currentScore = 0;
    private int finalScore = 0;
    private int killedEnemy = 0;
    private long totalTime = 0;
    private int baseScore = 1000;

    private Timeline gameLoop;
    private boolean isPaused = false;

    public static long elapsedTime = 0; // Used to store accumulated time
    private static long lastUpdateTime = 0; // To keep track of the last update time

    // Adding an Interpreter
    private ScoreInterpreter scoreInterpreter = new ScoreInterpreter();

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
    public void addObserver(GameModelObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    // Unregistering Observers
    public void removeObserver(GameModelObserver observer) {
        observers.remove(observer);
    }

    public void startGame(Stage primaryStage, int level) {
        clearData();
        elapsedTime = 0;
        currentLevel = level;
        this.isPaused = false;
        GameScreen.initContent();
        initLevel();
        GameScreen.startLevel();
        primaryStage.setScene(GameScreen.getGameScene());

        Move move = new Move(collidableMap);
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

    private void startGameLoop() {
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

    private void update() {
        player.update();
        if (enemyMap != null) {
            for (Enemy enemy : enemyMap) {
                enemy.update();
            }
        }
    }

    private void updateLabels() {
        updateFramerate();
        updateMoveState();
        updateTime();
        updatePosition();
        updatePlayerSpeed();
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
        MoveState moveState = getMovePlayerLogic().getMoveStatus().moveState;
        GameScreen.getMoveStateLabel().setText("Move State: " + moveState);
    }

    private void updatePosition() {
        double x = player.hitBox().getTranslateX();
        double y = player.hitBox().getTranslateY();
        GameScreen.getPositionLabel().setText("Position: (" + x + ", " + y + ")");
    }

    private void updatePlayerSpeed() {
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

    public void transitionToNextLevel() {
        stopGameLoop();
        if(currentLevel <= 2) {
            calculateCurrentScore();
            ScreenManager.showScreen(new TransitionScreen());
        } else{
            calculateCurrentScore();
            ScreenManager.showScreen(new CompletedScreen());
        }
    }



    public void restartLevel(){
        isPaused = false;
        elapsedTime = 0; // Reset elapsed time
        killedEnemy = 0;
        baseScore = 1000;
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
        killedEnemy = 0;
        ScreenManager.showScreen(new FailScreen());
    }

    public void resumeGame() {
        isPaused = false;
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

        // Notification of score changes
        notifyScoreChanged(currentScore);
    }


    public List<Entity> getCollidableMap() {
        return collidableMap;
    }


    public List<Enemy> getEnemyMap() {
        return enemyMap;
    }


    public void removeEnemy(Enemy enemy) {
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



    public static boolean isDebugMode() {
        return isDebugMode;
    }


    public void setDebugMode(boolean debugMode) {
        isDebugMode = debugMode;
    }


    public long getGameTime(){
        return (int)(elapsedTime/1000);
    }

    private void notifyScoreChanged(int newScore) {
        for (GameModelObserver observer : observers) {
            observer.onScoreChanged(newScore);
        }
    }

    private void notifyEnemyKilled(int totalKilled) {
        for (GameModelObserver observer : observers) {
            observer.onEnemyKilled(totalKilled);
        }
    }

    public void togglePauseMenu() {
        if (!isPaused) {
            isPaused = true; // Set the pause flag to true
            stopGameLoop(); // stop game loop
            ScreenManager.showScreen(new PauseScreen());
        }
    }
}
