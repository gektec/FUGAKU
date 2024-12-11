package com.example.platformerplain.View;

import com.example.platformerplain.*;
import com.example.platformerplain.Controller.GameScreenController;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.MovePlayer;
import com.example.platformerplain.texture.ImageScaler;
import javafx.animation.Timeline;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.platformerplain.model.GameModel.togglePauseMenu;

public class GameScreen implements Screen {
    public static HashMap<KeyCode, Boolean> keys = new HashMap<>();
    public static ArrayList<Entity> collidableMap = new ArrayList<>();
    public static ArrayList<Enemy> enemyMap = new ArrayList<>();
    private static ArrayList<Goal> goalMap = new ArrayList<>();
    private static ArrayList<Spike> spikeMap = new ArrayList<>();
    private static ArrayList<Ladder> ladderMap = new ArrayList<>();

    private static List<Entity> toRemove = new ArrayList<>();
    private static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    private static Pane uiRoot = new Pane();
    private static Pane backgroundRoot = new Pane();

    public static Entity player;
    private static int levelWidth = -1;
    private static int levelHeight = -1;
    public static MovePlayer movePlayerLogic;
    private static int level;
    private MoveEnemy moveEnemyLogic;
    private static Move move;
    public static Scene gameScene;

    //Debug

    public static Label framerateLabel = new Label();

    public static Label moveStateLabel = new Label();

    public static Label playerSpeedLabel = new Label();

    public static Label timeLabel = new Label();

    public static LineChart<Number, Number> speedChart;
    public static XYChart.Series<Number, Number> speedX;
    public static XYChart.Series<Number, Number> speedY;

    //Main

    private Timeline gameLoop;
    private static Button pauseMenu = new Button();

    public GameScreen(int level) {
        GameScreen.level = level;
    }

    @Override
    public void show(Stage primaryStage) {
        GameModel.startGame(primaryStage, level);
    }


    public static void initContent() {

        if(GameModel.getDebugMode()) {

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
        pauseMenu.setTranslateX(Constants.WINDOW_WIDTH - 100);
        pauseMenu.setTranslateY(30);
        pauseMenu.setText("Pause");

        pauseMenu.setOnAction(event -> togglePauseMenu());
    }


    public static void startLevel() {
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
        backgroundSky.setFitWidth(Constants.WINDOW_WIDTH * 5);
        backgroundSky.setFitHeight(Constants.WINDOW_HEIGHT * 5);

        Image background1 = Assets.BACKGROUND_CLOUD_1;
        ImageView backgroundCloud1 = new ImageView(ImageScaler.nearestNeighborScale(background1, 2));
        backgroundCloud1.setFitWidth(Constants.WINDOW_WIDTH * 1.2);
        backgroundCloud1.setFitHeight(Constants.WINDOW_HEIGHT * 1.2);

        Image background2 = Assets.BACKGROUND_CLOUD_2;
        ImageView backgroundCloud2 = new ImageView(ImageScaler.nearestNeighborScale(background2, 2));
        backgroundCloud2.setScaleX(-1);
        backgroundCloud2.setFitWidth(Constants.WINDOW_WIDTH * 1.2);
        backgroundCloud2.setFitHeight(Constants.WINDOW_HEIGHT * 1.2);

        Image background3 = Assets.BACKGROUND_CLOUD_3;
        ImageView backgroundCloud3 = new ImageView(ImageScaler.nearestNeighborScale(background3, 2));
        backgroundCloud3.setFitWidth(Constants.WINDOW_WIDTH * 1.2);
        backgroundCloud3.setFitHeight(Constants.WINDOW_HEIGHT * 1.2);

        Image background4 = Assets.BACKGROUND_MOON;
        ImageView backgroundMoon = new ImageView(ImageScaler.nearestNeighborScale(background4, 2));
        backgroundMoon.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundMoon.setFitHeight(Constants.WINDOW_HEIGHT);

        backgroundRoot.getChildren().addAll(backgroundSky, backgroundCloud3, backgroundCloud2, backgroundMoon, backgroundCloud1);

        // Initialize level dimensions
        levelWidth = LevelData.getLevelInformation.getLevelWidth();
        levelHeight = LevelData.getLevelInformation.getLevelHeight();

        // Position the game view
        gameRoot.setLayoutY(-(levelHeight - Constants.WINDOW_HEIGHT));
        gameRoot.setLayoutX(0);
        for (Node background : backgroundRoot.getChildren()) {
            background.setLayoutY(-(levelHeight - Constants.WINDOW_HEIGHT));
        }

        // Add level indicator text based on level
        if (LevelData.getLevelInformation.getLevelNumber() == 1) {
            Text title = new Text("Try to get the goal");
            title.setFont(new Font(36));
            title.setFill(Color.YELLOW);
            double textWidth = title.getLayoutBounds().getWidth();
            title.setX((Constants.WINDOW_WIDTH - textWidth) / 2);
            title.setY(40);
            uiRoot.getChildren().add(title);
        } else if (LevelData.getLevelInformation.getLevelNumber() == 2) {
            Text title = new Text("Level 2: New Challenges Await!");
            title.setFont(new Font(36));
            title.setFill(Color.YELLOW);
            double textWidth = title.getLayoutBounds().getWidth();
            title.setX((Constants.WINDOW_WIDTH - textWidth) / 2);
            title.setY(40);
            uiRoot.getChildren().add(title);
        }

        // Use LevelInitializer to set up the level
        LevelInitializer levelInitializer = new LevelInitializer(keys, gameRoot, uiRoot, backgroundRoot, collidableMap, enemyMap, spikeMap, ladderMap);
        player = levelInitializer.generateLevel(LevelData.getLevelInformation.getLevelNumber());

        // If player is not null, continue setup
        if (player != null) {
            movePlayerLogic = new MovePlayer(player, collidableMap, enemyMap, ladderMap, spikeMap, levelWidth, keys);

            // Camera's follow logic
            player.hitBox().translateXProperty().addListener((obs, old, newValue) -> {
                int offset = newValue.intValue();
                if (offset > Constants.WINDOW_WIDTH / 2 && offset < levelWidth - (Constants.WINDOW_WIDTH - Constants.PLAYER_SIZE) / 2) {
                    gameRoot.setLayoutX(-(offset - Constants.WINDOW_WIDTH / 2));
                    backgroundCloud1.setLayoutX(-(offset - Constants.WINDOW_WIDTH / 2) * 0.4);
                    backgroundCloud2.setLayoutX(-(offset - Constants.WINDOW_WIDTH / 2) * 0.3);
                    backgroundCloud3.setLayoutX(-(offset - Constants.WINDOW_WIDTH / 2) * 0.2);
                    backgroundMoon.setLayoutX((-(offset - Constants.WINDOW_WIDTH / 2) * 0.05));
                }
            });

            player.hitBox().translateYProperty().addListener((obs, old, newValue) -> {
                int offsetY = newValue.intValue();
                if (levelHeight - offsetY > Constants.WINDOW_HEIGHT / 2 && offsetY > Constants.WINDOW_HEIGHT / 4) {
                    gameRoot.setLayoutY(-(offsetY - Constants.WINDOW_HEIGHT / 2));
                    backgroundCloud1.setLayoutY((-(offsetY - Constants.WINDOW_HEIGHT / 2) * 0.4) - 50);
                    backgroundCloud2.setLayoutY((-(offsetY - Constants.WINDOW_HEIGHT / 2) * 0.3) - 50);
                    backgroundCloud3.setLayoutY((-(offsetY - Constants.WINDOW_HEIGHT / 2) * 0.2) - 50);
                    backgroundMoon.setLayoutY((-(offsetY - Constants.WINDOW_HEIGHT / 2) * 0.05) - 50);
                }
            });
        }

        // Add roots to the scene
        appRoot.getChildren().addAll(backgroundRoot, gameRoot, uiRoot);

        // Initialize the move logic
        move = new Move(collidableMap);

        // Create game scene
        gameScene = new Scene(appRoot);
        GameScreenController.setKeys(gameScene);

        // UI Elements
        uiRoot.getChildren().add(pauseMenu);
        uiRoot.getChildren().add(timeLabel);
        if (GameModel.getDebugMode()) {
            uiRoot.getChildren().add(framerateLabel);
            uiRoot.getChildren().add(playerSpeedLabel);
            uiRoot.getChildren().add(speedChart);
            uiRoot.getChildren().add(moveStateLabel);
        }

        GameModel.startTime = System.currentTimeMillis(); // Recording start time
        GameModel.elapsedTime = 0; // Reset elapsed time

    }

    public static Pane getGameRoot() {
        return gameRoot;
    }
}
