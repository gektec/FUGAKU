package com.fugaku.platformer.model;

import com.fugaku.platformer.Main;
import com.fugaku.platformer.data.LevelData;
import com.fugaku.platformer.entities.Entity;
import com.fugaku.platformer.entities.moveable.Enemy;
import com.fugaku.platformer.entities.tile.Coin;
import com.fugaku.platformer.entities.tile.Goal;
import com.fugaku.platformer.entities.tile.Ladder;
import com.fugaku.platformer.entities.tile.Spike;
import com.fugaku.platformer.model.Interpreter.ScoreContext;
import com.fugaku.platformer.model.Interpreter.ScoreInterpreter;
import com.fugaku.platformer.move.Move;
import com.fugaku.platformer.move.MovePlayer;
import com.fugaku.platformer.move.state.MoveState;
import com.fugaku.platformer.view.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The GameModel class represents the core logic of the game, managing game state,
 * player actions, and interactions within the game environment. It follows the
 * singleton pattern to ensure only one instance is used throughout the game.
 *
 * @author Changyu Li
 * @date 2024/12/11
 */
public class GameModel {

    // Singleton instance of GameModel
    private static GameModel instance;

    // List of observers for the game model
    private static final List<GameModelObserver> observers = new CopyOnWriteArrayList<>();

    // List of entities scheduled for removal
    private static final List<Entity> toRemove = new ArrayList<>();

    // Map to track key states (pressed/released)
    public static HashMap<KeyCode, Boolean> keys = new HashMap<>();

    // Lists to store game entities
    private static final ArrayList<Entity> collidableMap = new ArrayList<>();
    private static final ArrayList<Enemy> enemyMap = new ArrayList<>();
    private static final ArrayList<Goal> goalMap = new ArrayList<>();
    private static final ArrayList<Spike> spikeMap = new ArrayList<>();
    private static final ArrayList<Ladder> ladderMap = new ArrayList<>();
    private static final ArrayList<Coin> coinMap = new ArrayList<>();

    // Player entity
    public static Entity player;

    // Logic for moving the player
    private static MovePlayer movePlayerLogic;

    // Debug mode flag
    private static boolean isDebugMode = false;

    // Timing and scoring variables
    private static long lastTime = 0;
    private static int frameCount = 0;
    private static int timeStep = 0;
    private static int currentLevel = 0;
    private static int currentScore = 0;
    private static int finalScore = 0;
    public static int killedEnemy = 0;
    private static long totalTime = 0;
    private static int baseScore = 1000;

    // Game loop timeline
    private static Timeline gameLoop;
    private static boolean isPaused = false;

    // Time tracking variables
    public static long elapsedTime = 0;
    private static long lastUpdateTime = 0;

    // Color option
    private static char Color;

    // Score interpreter instance
    private static final ScoreInterpreter scoreInterpreter = new ScoreInterpreter();

    /**
     * Private constructor to prevent instantiation.
     */
    private GameModel() {
    }

    /**
     * Retrieves the singleton instance of GameModel.
     *
     * @return the singleton instance of GameModel
     */
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

    /**
     * Adds an observer to the game model to listen for updates.
     *
     * @param observer the observer to add
     */
    public static void addObserver(GameModelObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public static char setColor(char colorCode) {
        return colorCode;
    }


    /**
     * Removes an observer from the game model.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(GameModelObserver observer) {
        observers.remove(observer);
    }

    /**
     * Starts the game at the specified level and initializes game state.
     *
     * @param primaryStage the primary stage for the game
     * @param level        the level to start the game at
     */
    public static void startGame(Stage primaryStage, int level) {
        clearData();
        elapsedTime = 0;
        currentLevel = level;
        isPaused = false;
        baseScore = 1000;
        GameScreen.initContent();
        initLevel();
        GameScreen.startLevel();
        primaryStage.setScene(GameScreen.getGameScene());

        new Move(collidableMap);
        primaryStage.setTitle("FUGAKU: Level " + level);
        startGameLoop();
    }

    /**
     * Initializes the current level by setting up game entities and the player.
     */
    private static void initLevel() {
        LevelInitializer levelInitializer = new LevelInitializer(
                keys,
                GameScreen.getGameRoot(),
                GameScreen.getUIRoot(),
                GameScreen.getBackgroundRoot(),
                collidableMap,
                enemyMap,
                spikeMap,
                ladderMap,
                coinMap
        );
        player = levelInitializer.generateLevel(currentLevel);

        if (player != null) {
            movePlayerLogic = new MovePlayer(
                    player,
                    collidableMap,
                    enemyMap,
                    ladderMap,
                    spikeMap,
                    coinMap,
                    LevelData.getLevelInformation.getLevelWidth(),
                    keys
            );
        } else {
            System.err.println("Cannot generate player!");
        }
    }

    /**
     * Clears all game data and resets relevant lists and maps.
     */
    public static void clearData() {
        keys.clear();
        collidableMap.clear();
        enemyMap.clear();
        spikeMap.clear();
        coinMap.clear();
        ladderMap.clear();
        toRemove.clear();
    }

    /**
     * Starts the game loop for continuous updates and rendering.
     */
    private static void startGameLoop() {
        lastUpdateTime = System.currentTimeMillis();
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60), event -> {
            long currentTime = System.currentTimeMillis();
            elapsedTime += currentTime - lastUpdateTime;
            lastUpdateTime = currentTime;

            update();

            if (isDebugMode) updateLabels();

            enemyMap.removeAll(toRemove);
            collidableMap.removeAll(toRemove);
            coinMap.removeAll(toRemove);
            toRemove.clear();
        });

        gameLoop = new Timeline(frame);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    /**
     * Updates the game state, including player and enemy movements.
     */
    private static void update() {
        player.update();
        for (Enemy enemy : enemyMap) {
            enemy.update();
        }
        for (Coin coin : coinMap) {
            coin.update();
        }
    }

    /**
     * Updates debug labels to display game state information.
     */
    private static void updateLabels() {
        updateFramerate();
        updateMoveState();
        updateTime();
        updatePosition();
        updatePlayerSpeed();
    }

    /**
     * Updates the framerate label in the game UI.
     */
    private static void updateFramerate() {
        long currentTime = System.nanoTime();
        if (currentTime - lastTime >= 1_000_000_000) {
            GameScreen.getFramerateLabel().setText("FPS: " + frameCount);
            frameCount = 0;
            lastTime = currentTime;
        }
        frameCount++;
    }

    /**
     * Updates the time label in the game UI based on elapsed time.
     */
    private static void updateTime() {
        if (!isPaused) {
            long seconds = (elapsedTime / 1000) % 60;
            long minutes = (elapsedTime / 1000) / 60;
            GameScreen.getTimeLabel().setText(String.format("Time: %02d:%02d", minutes, seconds));
        }
    }

    /**
     * Updates the move state label based on player movement.
     */
    private static void updateMoveState() {
        MoveState moveState = MovePlayer.getMoveData().getState();
        GameScreen.getMoveStateLabel().setText("Move State: " + moveState);
    }

    /**
     * Updates the player position label in the game UI.
     */
    private static void updatePosition() {
        double x = player.hitBox().getTranslateX();
        double y = player.hitBox().getTranslateY();
        GameScreen.getPositionLabel().setText("Position: (" + x + ", " + y + ")");
    }

    /**
     * Updates the player speed label in the game UI.
     */
    private static void updatePlayerSpeed() {
        double[] speed = MovePlayer.getMoveData().velocity.get();
        GameScreen.getPlayerSpeedLabel().setText("Speed: " + Arrays.toString(speed));
        GameScreen.getSpeedX().getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[0])));
        GameScreen.getSpeedY().getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[1])));

        if (GameScreen.getSpeedX().getData().size() > 60) {
            GameScreen.getSpeedX().getData().removeFirst();
            GameScreen.getSpeedY().getData().removeFirst();
        }

        NumberAxis xAxis = (NumberAxis) GameScreen.getSpeedChart().getXAxis();
        NumberAxis yAxis = (NumberAxis) GameScreen.getSpeedChart().getYAxis();
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(timeStep - 60);
        xAxis.setUpperBound(timeStep);
        yAxis.setUpperBound(20);
    }

    /**
     * Transitions to the next level or completes the game based on the current level.
     */
    public static void transitionToNextLevel() {
        stopGameLoop();

        if (currentLevel <= 2) {
            calculateCurrentScore();
            new TransitionScreen().show(Main.getPrimaryStage());
        } else {
            calculateCurrentScore();
            RankModel.saveAndMaintainTopScores(finalScore);
            new CompletedScreen().show(Main.getPrimaryStage());
        }
    }

    /**
     * Stops the game loop.
     */
    public static void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    /**
     * Exits the game and shows the failure screen.
     */
    public static void exitGame() {
        killedEnemy = 0;
        new FailScreen().show(Main.getPrimaryStage());
    }

    /**
     * Resumes the game from a paused state.
     */
    public static void resumeGame() {
        isPaused = false;
        startGameLoop();
    }

    /**
     * Calculates the current score based on elapsed time and enemies killed.
     */
    public static void calculateCurrentScore() {
        int maxScore = 1000;
        int penaltyPerSecond = 10;
        long elapsedTimeSeconds = elapsedTime / 1000;

        // Create context for score calculation
        ScoreContext context = new ScoreContext(maxScore, penaltyPerSecond, elapsedTime, killedEnemy);

        // Interpret and calculate scores
        currentScore = scoreInterpreter.interpret(context);

        // Ensure currentScore does not fall below zero
        if (currentScore < 0) {
            currentScore = 0;
        }

        finalScore += currentScore;
        totalTime += elapsedTimeSeconds;

        // Notify observers of score changes
        notifyScoreChanged(currentScore);
    }


    public static List<Entity> getCollidableMap() {
        return collidableMap;
    }

    public static char setBlackColor() {
        Color = 'K';
        return Color;
    }

    public static char setOrangeColor() {
        Color = 'O';
        return Color;
    }

    public static char setYellowColor() {
        Color = 'Y';
        return Color;
    }

    public static char setBlueColor() {
        Color = 'B';
        return Color;
    }

    public static char setPurpleColor() {
        Color = 'P';
        return Color;
    }

    public static char setGreenColor() {
        Color = 'G';
        return Color;
    }

    public static char setRedColor() {
        Color = 'R';
        return Color;
    }

    public static char setPinkColor() {
        Color = 'P';
        return Color;
    }

    public static char getColor() {
        return Color;
    }


    public static List<Enemy> getEnemyMap() {
        return enemyMap;
    }


    public static void removeEntity(Entity entity) {
        toRemove.add(entity);
        GameScreen.getGameRoot().getChildren().remove(entity.canvas());
        GameScreen.getGameRoot().getChildren().remove(entity.hitBox());
    }

    public static void killedEnemy() {
        killedEnemy++;
        baseScore += 200;
        notifyScoreChanged(baseScore);
        // Notify of changes in the number of enemies killed
        notifyEnemyKilled(killedEnemy);
    }

    public static void collectedCoin() {
        baseScore += 300;
        notifyScoreChanged(baseScore);
    }

    public static MovePlayer getMovePlayerLogic() {
        return movePlayerLogic;
    }

    public static long getTotalTime() {
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


    public static int getCurrentLevel() {
        return currentLevel;
    }


    public static boolean isDebugMode() {
        return isDebugMode;
    }


    public static void setDebugMode(boolean debugMode) {
        isDebugMode = debugMode;
    }


    public static long getGameTime() {
        return (int) (elapsedTime / 1000);
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
            new PauseScreen().show(Main.getPrimaryStage());
        }
    }

}