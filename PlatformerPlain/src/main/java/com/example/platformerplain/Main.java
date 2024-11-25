package com.example.platformerplain;

import com.example.platformerplain.ScreenManager.ScreenManager;
import com.example.platformerplain.entities.Enemy;
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
import java.util.List;

public class Main extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private ArrayList<Entity> collidableMap = new ArrayList<>();
    private ArrayList<Enemy> enemyMap = new ArrayList<>();
    private List<Entity> toRemove = new ArrayList<>();
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Entity player;
    private int levelWidth = -1;
    private int levelHeight = -1;
    private MovePlayer movePlayerLogic;
    private MoveEnemy moveEnemyLogic;
    private Move move;
    private Scene gameScene;

    private static long startTime;

    private Label framerateLabel = new Label();
    private long lastTime = 0;
    private int frameCount = 0;

    private int currentLevel = 0;

    private static Main instance;
    private Stage primaryStage;
    private ScreenManager screenManager;  // ScreenManager instance

    private Timeline gameLoop;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        startTime = System.currentTimeMillis();
        instance = this;

        screenManager = new ScreenManager(primaryStage);
        screenManager.showStartScreen();
    }

    public void startGame(Stage primaryStage) {
        currentLevel = 1;
        initContent();
        startLevel();
        primaryStage.setScene(gameScene);
        gameRoot.setLayoutY(-(levelHeight - Constants.BACKGROUND_HEIGHT));
        startGameLoop();
    }


    private void startGameLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60), event -> {
        update();
        updateFramerate();
        enemyMap.removeAll(toRemove);
        collidableMap.removeAll(toRemove);
    });

        gameLoop = new Timeline(frame);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void initContent() {

    }

    private void startLevel() {
        keys.clear();
        collidableMap.clear();
        enemyMap.clear();
        toRemove.clear();


        appRoot = new Pane();
        gameRoot = new Pane();
        uiRoot = new Pane();

        levelWidth = LevelData.Levels[currentLevel][0].length() * Constants.TILE_SIZE;
        levelHeight = LevelData.Levels[currentLevel].length * Constants.TILE_SIZE;


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

        for (int i = 0; i < LevelData.Levels[currentLevel].length; i++) {
            String line = LevelData.Levels[currentLevel][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case 'P':
                        player = createEntity(Constants.EntityType.PLAYER, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE, 0);
                        break;
                    case 'M':
                        int adjacencyCode = 0;
                        if (i > 0 && LevelData.Levels[currentLevel][i - 1].charAt(j) == 'M') {
                            adjacencyCode += 1;
                        }
                        if (j < line.length() - 1 && LevelData.Levels[currentLevel][i].charAt(j + 1) == 'M') {
                            adjacencyCode += 2;
                        }
                        if (i < LevelData.Levels[currentLevel].length - 1 && LevelData.Levels[currentLevel][i + 1].charAt(j) == 'M') {
                            adjacencyCode += 4;
                        }
                        if (j > 0 && LevelData.Levels[currentLevel][i].charAt(j - 1) == 'M') {
                            adjacencyCode += 8;
                        }
                        Entity platform = createEntity(Constants.EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        collidableMap.add(platform);
                        break;
                    case 'G':
                        Entity goal = createEntity(Constants.EntityType.GOAL, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 50);
                        collidableMap.add(goal);
                        break;
                    case 'E':
                        Enemy enemy = (Enemy) createEntity(Constants.EntityType.ENEMY, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE, 70);
                        moveEnemyLogic = new MoveEnemy(enemy, collidableMap, levelWidth, keys);
                        enemyMap.add(enemy);
                        break;
                }
            }
        }

        player.hitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > Constants.BACKGROUND_WIDTH / 2 && offset < levelWidth - Constants.BACKGROUND_WIDTH / 2) {
                gameRoot.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2));
            }
        });
        player.hitBox().translateYProperty().addListener((obs, old, newValue) -> {
            int offsetY = newValue.intValue();
            //test
            //System.out.println(offsetY);
            if (levelHeight - offsetY > Constants.BACKGROUND_HEIGHT / 2 && offsetY > Constants.BACKGROUND_HEIGHT / 4) {
                gameRoot.setLayoutY(-(offsetY - Constants.BACKGROUND_HEIGHT / 2));
            }
        });

        Rectangle bg = new Rectangle(Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);
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


    public void transitionToLevel2() {
        stopGameLoop();

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
        screenManager.showFailScreen();
    }

    public static Main getInstance() {
        return instance;
    }


    public void removeEnemy(Enemy enemy) {
        toRemove.add(enemy);
        gameRoot.getChildren().remove(enemy.node());
    }

    public int getLevel() {
        return currentLevel;
    }

    private void update() {
        movePlayerLogic.update();
        if (enemyMap != null) {
            for (Enemy enemy : enemyMap) {
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


    private Entity createEntity(Constants.EntityType type, int x, int y, int w, int h, int index) {
        Entity entity = EntityFactory.createEntity(type, x, y, w, h, index);
        gameRoot.getChildren().add(entity.node());
        return entity;
    }
}
