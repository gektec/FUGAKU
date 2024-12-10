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

    private boolean isDebugMode = true;

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

    private static Main instance;
    private Stage primaryStage;
    private ScreenManager screenManager;  // ScreenManager instance

    private Timeline gameLoop;
    private Button pauseMenu = new Button();
    private boolean isPaused = false;

    private long startTime = 0;  // To store the start time
    private long elapsedTime = 0; // Used to store accumulated time
    private long totalTime = 0; // Total time variable


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        instance = this;

        // Initialize ScreenManager
        screenManager = ScreenManager.getInstance(primaryStage);

        // Show the start screen
        screenManager.showScreen(new MenuScreen());

        primaryStage.setWidth(Constants.BACKGROUND_WIDTH);
        primaryStage.setHeight(Constants.BACKGROUND_HEIGHT);
        primaryStage.setResizable(false);

    }

    public void startGame(Stage primaryStage) {
        currentLevel = 1;
        isPaused = false;
        initContent();
        startLevel();
        primaryStage.setScene(gameScene);
        startGameLoop();
    }


    private void startGameLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60), event -> {
            update();
            if(isDebugMode) updateLables();


            enemyMap.removeAll(toRemove);
            collidableMap.removeAll(toRemove);
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

    private void updateLables() {
        updateFramerate();
        updatePlayerSpeed();
        updateMoveState();
        updateTime();
    }

    private void updateFramerate() {
        long currentTime = System.nanoTime();
        if (currentTime - lastTime >= 1_000_000_000) {
            framerateLabel.setText("FPS: " + frameCount);
            frameCount = 0;
            lastTime = currentTime;
        }
        frameCount++;
    }

    private void updateTime(){
        if (!isPaused) {
            elapsedTime = System.currentTimeMillis() - startTime; // Calculate elapsed time
            long seconds = (elapsedTime / 1000) % 60; // Calculate seconds
            long minutes = (elapsedTime / 1000) / 60; // Calculate minutes
            timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)); // update Label
        }
    }


    private void updateMoveState() {
        MoveState moveState = movePlayerLogic.getMoveStatus().moveState;
        moveStateLabel.setText("Move State: " + moveState);
    }

    private void updatePlayerSpeed() {
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

    private void initContent() {

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

        pauseMenu.setOnAction(event -> togglePauseMenu());
    }

    private void startLevel() {
        // Clear current game state
        keys.clear();
        collidableMap.clear();
        enemyMap.clear();
        toRemove.clear();

        // Initialize Panes for game layout
        appRoot = new Pane();
        gameRoot = new Pane();
        uiRoot = new Pane();
        backgroundRoot = new Pane();

        // Set up the background
        Image background0 = Assets.BACKGROUND_SKY;
        ImageView backgroundSky = new ImageView(background0);
        backgroundSky.setFitWidth(Constants.BACKGROUND_WIDTH * 5);
        backgroundSky.setFitHeight(Constants.BACKGROUND_HEIGHT * 5);

        Image background1 = Assets.BACKGROUND_CLOUD_1;
        ImageView backgroundCloud1 = new ImageView(ImageScaler.nearestNeighborScale(background1, 2));
        backgroundCloud1.setFitWidth(Constants.BACKGROUND_WIDTH * 1.2);
        backgroundCloud1.setFitHeight(Constants.BACKGROUND_HEIGHT * 1.2);

        Image background2 = Assets.BACKGROUND_CLOUD_2;
        ImageView backgroundCloud2 = new ImageView(ImageScaler.nearestNeighborScale(background2, 2));
        backgroundCloud2.setScaleX(-1);
        backgroundCloud2.setFitWidth(Constants.BACKGROUND_WIDTH * 1.2);
        backgroundCloud2.setFitHeight(Constants.BACKGROUND_HEIGHT * 1.2);

        Image background3 = Assets.BACKGROUND_CLOUD_3;
        ImageView backgroundCloud3 = new ImageView(ImageScaler.nearestNeighborScale(background3, 2));
        backgroundCloud3.setFitWidth(Constants.BACKGROUND_WIDTH * 1.2);
        backgroundCloud3.setFitHeight(Constants.BACKGROUND_HEIGHT * 1.2);

        Image background4 = Assets.BACKGROUND_MOON;
        ImageView backgroundMoon = new ImageView(ImageScaler.nearestNeighborScale(background4, 2));
        backgroundMoon.setFitWidth(Constants.BACKGROUND_WIDTH);
        backgroundMoon.setFitHeight(Constants.BACKGROUND_HEIGHT);

        backgroundRoot.getChildren().addAll(backgroundSky, backgroundCloud3, backgroundCloud2, backgroundMoon, backgroundCloud1);

        // Initialize level dimensions
        levelWidth = LevelData.getLevelInformation.getLevelWidth();
        levelHeight = LevelData.getLevelInformation.getLevelHeight();

        // Position the game view
        gameRoot.setLayoutY(-(levelHeight - Constants.BACKGROUND_HEIGHT));
        gameRoot.setLayoutX(0);
        for (Node background : backgroundRoot.getChildren()) {
            background.setLayoutY(-(levelHeight - Constants.BACKGROUND_HEIGHT));
        }

        // Add level indicator text based on level
        if (currentLevel == 1) {
            Text title = new Text("Try to get the goal");
            title.setFont(new Font(36));
            title.setFill(Color.YELLOW);
            double textWidth = title.getLayoutBounds().getWidth();
            title.setX((Constants.BACKGROUND_WIDTH - textWidth) / 2);
            title.setY(40);
            uiRoot.getChildren().add(title);
        } else if (currentLevel == 2) {
            Text title = new Text("Level 2: New Challenges Await!");
            title.setFont(new Font(36));
            title.setFill(Color.YELLOW);
            double textWidth = title.getLayoutBounds().getWidth();
            title.setX((Constants.BACKGROUND_WIDTH - textWidth) / 2);
            title.setY(40);
            uiRoot.getChildren().add(title);
        }

        // Use LevelInitializer to set up the level
        LevelInitializer levelInitializer = new LevelInitializer(keys, gameRoot, uiRoot, backgroundRoot, collidableMap, enemyMap, spikeMap, ladderMap);
        player = levelInitializer.generateLevel(currentLevel);

        // If player is not null, continue setup
        if (player != null) {
            movePlayerLogic = new MovePlayer(player, collidableMap, enemyMap, ladderMap, spikeMap, levelWidth, keys, this);

            // Camera's follow logic
            player.hitBox().translateXProperty().addListener((obs, old, newValue) -> {
                int offset = newValue.intValue();
                if (offset > Constants.BACKGROUND_WIDTH / 2 && offset < levelWidth - (Constants.BACKGROUND_WIDTH - Constants.PLAYER_SIZE) / 2) {
                    gameRoot.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2));
                    backgroundCloud1.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.4);
                    backgroundCloud2.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.3);
                    backgroundCloud3.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.2);
                    backgroundMoon.setLayoutX((-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.05));
                }
            });

            player.hitBox().translateYProperty().addListener((obs, old, newValue) -> {
                int offsetY = newValue.intValue();
                if (levelHeight - offsetY > Constants.BACKGROUND_HEIGHT / 2 && offsetY > Constants.BACKGROUND_HEIGHT / 4) {
                    gameRoot.setLayoutY(-(offsetY - Constants.BACKGROUND_HEIGHT / 2));
                    backgroundCloud1.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.4) - 50);
                    backgroundCloud2.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.3) - 50);
                    backgroundCloud3.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.2) - 50);
                    backgroundMoon.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.05) - 50);
                }
            });
        }

        // Add roots to the scene
        appRoot.getChildren().addAll(backgroundRoot, gameRoot, uiRoot);

        // Initialize the move logic
        move = new Move(collidableMap);

        // Create game scene
        gameScene = new Scene(appRoot);
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        // UI Elements
        uiRoot.getChildren().add(pauseMenu);
        uiRoot.getChildren().add(timeLabel);
        if (isDebugMode) {
            uiRoot.getChildren().add(framerateLabel);
            uiRoot.getChildren().add(playerSpeedLabel);
            uiRoot.getChildren().add(speedChart);
            uiRoot.getChildren().add(moveStateLabel);
        }

        startTime = System.currentTimeMillis(); // Recording start time
        elapsedTime = 0; // Reset elapsed time

    }


    public void transitionToNextLevel() {
        stopGameLoop();
        totalTime += elapsedTime; // Add to total time
        if(currentLevel == 1) {
            screenManager.showScreen(new TransitionScreen());
        }else{
            screenManager.showScreen(new CompletedScreen());
        }
    }

    public void startNextLevel(){
        startTime = System.currentTimeMillis(); // Reset start time
        elapsedTime = 0; // Reset elapsed time
        currentLevel = 2;
        startLevel();
        primaryStage.setScene(gameScene);
        startGameLoop();
    }

    public void restartLevel(){
        startLevel();
        primaryStage.setScene(gameScene);
        gameRoot.setLayoutY(-(levelHeight - Constants.BACKGROUND_HEIGHT));
        gameRoot.setLayoutX(0);
        startGameLoop();
    }

    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }

    }

    public void exitGame() {
        totalTime = 0;
        screenManager.showScreen(new FailScreen());
    }

    private void togglePauseMenu() {
        if(!isPaused) {
            stopGameLoop(); // stop game loop
            ScreenManager.getInstance(primaryStage).showScreen(new PauseScreen());
            isPaused = true;
        }else {
            resumeGame();
        }
    }

    public void resumeGame() {
        isPaused = false;
        startTime = System.currentTimeMillis() - elapsedTime;// Ensure time continuity
        startGameLoop();
    }

    public int currentScore(){
        return currentScore;
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
