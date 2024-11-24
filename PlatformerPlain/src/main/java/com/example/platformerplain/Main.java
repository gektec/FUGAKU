package com.example.platformerplain;

import com.example.platformerplain.ScreenManager.ScreenManager;
import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityFactory;

import com.example.platformerplain.move.Move;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.MovePlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private ArrayList<Entity> collidableMap = new ArrayList<>();
    private ArrayList<Entity> enemyMap = new ArrayList<>();
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Entity player;
    private int levelWidth;
    private MovePlayer movePlayerLogic;
    private MoveEnemy moveEnemyLogic;
    private Move move;
    private Scene gameScene;

    private static long startTime;

    private Label framerateLabel = new Label();
    private long lastTime = 0;
    private int frameCount = 0;
    private static Main instance;
    private Stage primaryStage;
    private ScreenManager screenManager;  // ScreenManager instance

    private Timeline gameLoop;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        startTime = System.currentTimeMillis();
        instance = this;

        // Initialize the ScreenManager with primaryStage
        screenManager = new ScreenManager(primaryStage);
        screenManager.showStartScreen();
    }

    public void startGame(Stage primaryStage) {
        initContent();
        primaryStage.setScene(gameScene);

        // Start the game loop
        startGameLoop();
    }

    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    public void exitGame() {
        screenManager.showFailScreen();
    }

    public static Main getInstance() {
        return instance;
    }

    private void startGameLoop() {
        // 设置每帧的持续时间为约16.67毫秒，即1秒/60
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60), event -> {
            update();
            updateFramerate();
        });

        // 创建时间线并添加帧
        gameLoop = new Timeline(frame);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void initContent() {
        appRoot.getChildren().clear();
        gameRoot.getChildren().clear();
        uiRoot.getChildren().clear();

        Rectangle bg = new Rectangle(Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);
        levelWidth = LevelData.Level1[0].length() * Constants.TILE_SIZE;

        Text title = new Text("Try to get the goal");
        title.setFont(new Font(36));
        title.setFill(Color.YELLOW);
        double textWidth = title.getLayoutBounds().getWidth();
        title.setX((Constants.BACKGROUND_WIDTH - textWidth) / 2);
        title.setY(40);
        uiRoot.getChildren().add(title);

        for (int i = 0; i < LevelData.Level1.length; i++) {
            String line = LevelData.Level1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case 'M':
                        int adjacencyCode = 0;
                        if (i > 0 && LevelData.Level1[i - 1].charAt(j) == 'M') {
                            adjacencyCode += 1;
                        }
                        if (j < line.length() - 1 && LevelData.Level1[i].charAt(j + 1) == 'M') {
                            adjacencyCode += 2;
                        }
                        if (i < LevelData.Level1.length - 1 && LevelData.Level1[i + 1].charAt(j) == 'M') {
                            adjacencyCode += 4;
                        }
                        if (j > 0 && LevelData.Level1[i].charAt(j - 1) == 'M') {
                            adjacencyCode += 8;
                        }
                        Entity platform = createEntity(Constants.EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        collidableMap.add(platform);
                        break;
                    case 'G':
                        Entity goal = createEntity(Constants.EntityType.GOAL, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE,50);
                        collidableMap.add(goal);
                        break;
                    case 'E':
                        Entity enemy = createEntity(Constants.EntityType.ENEMY, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE,70);
                        moveEnemyLogic = new MoveEnemy(enemy, collidableMap, levelWidth, keys);
                        enemyMap.add(enemy);
                        break;
                }
            }
        }

        player = createEntity(Constants.EntityType.PLAYER, Constants.PLAYER_START_X, Constants.PLAYER_START_Y, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE,0);
        player.hitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > Constants.BACKGROUND_WIDTH / 2 && offset < levelWidth - Constants.BACKGROUND_WIDTH / 2) {
                gameRoot.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2));
            }
        });

        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);
        movePlayerLogic = new MovePlayer(player, collidableMap, enemyMap, levelWidth, keys, this);

        move = new Move(collidableMap);

        gameScene = new Scene(appRoot);
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        framerateLabel.setTextFill(Color.WHITE);
        framerateLabel.setFont(new Font(18));
        framerateLabel.setTranslateX(10);
        framerateLabel.setTranslateY(10);
        uiRoot.getChildren().add(framerateLabel);
    }

    private void update() {
        movePlayerLogic.update();
        for (Entity enemy : enemyMap) {
            if (moveEnemyLogic != null) {
                moveEnemyLogic.update();
                enemy.update();
            }
        }
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

    public static void main(String[] args) {
        launch(args);
    }

    private Entity createEntity(Constants.EntityType type, int x, int y, int w, int h, int index) {
        Entity entity = EntityFactory.createEntity(type, x, y, w, h, index);
        gameRoot.getChildren().add(entity.node());
        return entity;
    }
}
