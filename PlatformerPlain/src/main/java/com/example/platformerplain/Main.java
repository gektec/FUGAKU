package com.example.platformerplain;

import com.example.platformerplain.Screen.*;
import com.example.platformerplain.entities.Enemy;
import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityFactory;

import com.example.platformerplain.entities.*;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.MovePlayer;
import com.example.platformerplain.move.MoveState;
import com.example.platformerplain.texture.ImageScaler;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
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

    private boolean isDebugMode = false;

    private Label framerateLabel = new Label();
    private long lastTime = 0;
    private int frameCount = 0;

    private Label moveStateLabel = new Label();

    private Label playerSpeedLabel = new Label();

    private LineChart<Number, Number> speedChart;
    private XYChart.Series<Number, Number> speedSeries;
    private int timeStep = 0;

    //Main

    static int currentLevel = 0;

    private static Main instance;
    private Stage primaryStage;
    private ScreenManager screenManager;  // ScreenManager instance

    private Timeline gameLoop;
    private Button pauseMenu = new Button();
    private boolean isPaused = false;


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
        initContent();
        startLevel();
        primaryStage.setScene(gameScene);
        gameRoot.setLayoutY(-(levelHeight - Constants.BACKGROUND_HEIGHT));
        for (Node background : backgroundRoot.getChildren()) {
            background.setLayoutY(-(levelHeight - Constants.BACKGROUND_HEIGHT));
        }
        //todo: set background layout
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

    private void updateMoveState() {
        MoveState moveState = movePlayerLogic.getMoveStatus().moveState;
        moveStateLabel.setText("Move State: " + moveState);
    }

    private void updatePlayerSpeed() {
        int[] speed = movePlayerLogic.getMoveStatus().velocity.get();
        playerSpeedLabel.setText("Speed: " + Arrays.toString(speed));
        speedSeries.getData().add(new XYChart.Data<>(timeStep++, Math.sqrt(speed[0] * speed[0] + speed[1] * speed[1])));

        // Keep only the last 50 data points
        if (speedSeries.getData().size() > 50) {
            speedSeries.getData().remove(0);
        }

        // Update x-axis bounds
        NumberAxis xAxis = (NumberAxis) speedChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) speedChart.getYAxis();
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(timeStep - 50);
        xAxis.setUpperBound(timeStep);
        yAxis.setUpperBound(32);
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

            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Time");
            yAxis.setLabel("Speed");

            speedChart = new LineChart<>(xAxis, yAxis);
            speedChart.setTitle("Player Speed Over Time");
            speedSeries = new XYChart.Series<>();
            speedChart.getData().add(speedSeries);
            speedChart.setTranslateX(10);
            speedChart.setTranslateY(70);
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
        keys.clear();
        collidableMap.clear();
        enemyMap.clear();
        toRemove.clear();

        appRoot = new Pane();
        gameRoot = new Pane();
        uiRoot = new Pane();
        backgroundRoot = new Pane();

        Image background0 = Assets.BACKGROUND_SKY;
        ImageView backgroundSky = new ImageView(background0);
        backgroundSky.setFitWidth(Constants.BACKGROUND_WIDTH*5);
        backgroundSky.setFitHeight(Constants.BACKGROUND_HEIGHT*5);

        Image background1 = Assets.BACKGROUND_CLOUD_1;
        ImageView backgroundCloud1 = new ImageView(ImageScaler.nearestNeighborScale(background1,2));
        backgroundCloud1.setFitWidth(Constants.BACKGROUND_WIDTH*1.2);
        backgroundCloud1.setFitHeight(Constants.BACKGROUND_HEIGHT*1.2);

        Image background2 = Assets.BACKGROUND_CLOUD_2;
        ImageView backgroundCloud2 = new ImageView(ImageScaler.nearestNeighborScale(background2,2));
        backgroundCloud2.setScaleX(-1);
        backgroundCloud2.setFitWidth(Constants.BACKGROUND_WIDTH*1.2);
        backgroundCloud2.setFitHeight(Constants.BACKGROUND_HEIGHT*1.2);

        Image background3 = Assets.BACKGROUND_CLOUD_3;
        ImageView backgroundCloud3 = new ImageView(ImageScaler.nearestNeighborScale(background3,2));
        backgroundCloud3.setFitWidth(Constants.BACKGROUND_WIDTH*1.2);
        backgroundCloud3.setFitHeight(Constants.BACKGROUND_HEIGHT*1.2);

        Image background4 = Assets.BACKGROUND_MOON;
        ImageView backgroundMoon = new ImageView(ImageScaler.nearestNeighborScale(background4,2));
        backgroundMoon.setFitWidth(Constants.BACKGROUND_WIDTH);
        backgroundMoon.setFitHeight(Constants.BACKGROUND_HEIGHT);

        backgroundRoot.getChildren().addAll(backgroundSky, backgroundCloud3, backgroundCloud2, backgroundMoon, backgroundCloud1);

        levelWidth = LevelData.getLevelInformation.getLevelWidth();
        levelHeight = LevelData.getLevelInformation.getLevelHeight();


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

        int adjacencyCode = 0;
        for (int i = 0; i < LevelData.Levels[currentLevel].length; i++) {
            String line = LevelData.Levels[currentLevel][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case 'P':
                        player = createEntity(Constants.EntityType.PLAYER, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, 0);
                        movePlayerLogic = new MovePlayer(player, collidableMap, enemyMap, ladderMap, spikeMap, levelWidth, keys, this);
                        break;
                    case 'M':
                        adjacencyCode = calculateAdjacencyCode(LevelData.Levels[currentLevel], i, j, 'M');
                        Entity platform = createEntity(Constants.EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        collidableMap.add(platform);
                        break;
                    case 'H':
                        if (i > 0 && LevelData.Levels[currentLevel][i - 1].charAt(j) == 'H' && LevelData.Levels[currentLevel][i + 1].charAt(j) == 'H') {
                            adjacencyCode = (int)(Math.random() * 2) + 1; // 1 or 2
                        }
                        else if (i < LevelData.Levels[currentLevel].length - 1 && LevelData.Levels[currentLevel][i + 1].charAt(j) == 'H') {
                            adjacencyCode = 0;
                        }
                        else adjacencyCode = 3;
                        Ladder ladder = (Ladder) createEntity(Constants.EntityType.LADDER, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        ladderMap.add(ladder);
                        break;
                    case 'G':
                        Entity goal = createEntity(Constants.EntityType.GOAL, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 50);
                        collidableMap.add(goal);
                        break;
                    case 'E':
                        Enemy enemy = (Enemy) createEntity(Constants.EntityType.ENEMY, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE, 70);
                        enemyMap.add(enemy);
                        break;
                    case 's':
                        adjacencyCode = calculateAdjacencyCode(LevelData.Levels[currentLevel], i, j, 'S');
                        Spike spike = (Spike) createEntity(Constants.EntityType.SPIKE, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        spikeMap.add(spike);
                        break;
                    case 'S':
                        adjacencyCode = calculateAdjacencyCode(LevelData.Levels[currentLevel], i, j, 's') + 16;
                        Spike spikeBody = (Spike) createEntity(Constants.EntityType.SPIKE, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        spikeMap.add(spikeBody);
                        break;
                }
            }
        }

        player.hitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > Constants.BACKGROUND_WIDTH / 2 && offset < levelWidth - Constants.BACKGROUND_WIDTH / 2) {

                //todo: encapsulate this into a method
                gameRoot.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2));
                backgroundCloud1.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.4); // Layer 1 moves at half speed
                backgroundCloud2.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.3); // Layer 2 moves at 30% speed
                backgroundCloud3.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.2); // Layer 2 moves at 30% speed
                backgroundMoon.setLayoutX((-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.05)); // Layer 2 moves at 30% speed

            }
        });
        player.hitBox().translateYProperty().addListener((obs, old, newValue) -> {
            int offsetY = newValue.intValue();
            //test
            //System.out.println(offsetY);
            if (levelHeight - offsetY > Constants.BACKGROUND_HEIGHT / 2 && offsetY > Constants.BACKGROUND_HEIGHT / 4) {
                gameRoot.setLayoutY(-(offsetY - Constants.BACKGROUND_HEIGHT / 2));
                backgroundCloud1.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.4) - 50); // Layer 1 moves at half speed
                backgroundCloud2.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.3) - 50); // Layer 2 moves at 30% speed
                backgroundCloud3.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.2) - 50); // Layer 2 moves at 30% speed
                backgroundMoon.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.05) - 50); // Layer 2 moves at 30% speed
            }
        });

        appRoot.getChildren().addAll(backgroundRoot, gameRoot, uiRoot);

        move = new Move(collidableMap);

        gameScene = new Scene(appRoot);
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        uiRoot.getChildren().add(pauseMenu);
        if (isDebugMode) {
            uiRoot.getChildren().add(framerateLabel);
            uiRoot.getChildren().add(playerSpeedLabel);
            uiRoot.getChildren().add(speedChart);
            uiRoot.getChildren().add(moveStateLabel);
        }
    }

    private int calculateAdjacencyCode(String[] level, int i, int j, char target) {
        int adjacencyCode = 0;
        if (i > 0 && level[i - 1].charAt(j) == target) {
            adjacencyCode += 1; // got a neighbor above
        }
        if (j < level[i].length() - 1 && level[i].charAt(j + 1) == target) {
            adjacencyCode += 2; // got a neighbor to the right
        }
        if (i < level.length - 1 && level[i + 1].charAt(j) == target) {
            adjacencyCode += 4; // got a neighbor below
        }
        if (j > 0 && level[i].charAt(j - 1) == target) {
            adjacencyCode += 8; // got a neighbor to the left
        }
        return adjacencyCode;
    }

    public void transitionToNextLevel() {
        stopGameLoop();
        if(currentLevel == 1) {
            screenManager.showScreen(new TransitionScreen());
        }else{
            screenManager.showScreen(new CompletedScreen());
        }
    }

    public void startNextLevel(){
        currentLevel = 2;
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
        screenManager.showScreen(new FailScreen());
    }

    private void togglePauseMenu() {
            stopGameLoop(); // stop game loop
            ScreenManager.getInstance(primaryStage).showScreen(new PauseScreen());
    }

    public void resumeGame() {
        isPaused = false;
        startGameLoop();
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


    private Entity createEntity(Constants.EntityType type, int x, int y, int w, int h, int index) {
        Entity entity = EntityFactory.createEntity(type, x, y, w, h, index);
        gameRoot.getChildren().add(entity.canvas());
        return entity;
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
