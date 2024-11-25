package com.example.platformerplain;

import com.example.platformerplain.ScreenManager.ScreenManager;

import com.example.platformerplain.entities.*;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.MovePlayer;
import com.example.platformerplain.texture.ImageScaler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
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
    private MovePlayer movePlayerLogic;
    private MoveEnemy moveEnemyLogic;
    private Move move;
    private Scene gameScene;

    private static long startTime;

    private Label framerateLabel = new Label();
    private long lastTime = 0;
    private int frameCount = 0;

    static int currentLevel = 0;

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
        //todo: set background layout
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

    private void update() {
        movePlayerLogic.update();
        if (enemyMap != null) {
            for (Enemy enemy : enemyMap) {
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

    private void initContent() {
        framerateLabel.setTextFill(Color.WHITE);
        framerateLabel.setFont(new Font(18));
        framerateLabel.setTranslateX(10);
        framerateLabel.setTranslateY(10);
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

        Image background0 = Constants.BACKGROUND_SKY;
        ImageView backgroundImageView0 = new ImageView(background0);
        backgroundImageView0.setFitWidth(Constants.BACKGROUND_WIDTH);
        backgroundImageView0.setFitHeight(Constants.BACKGROUND_HEIGHT);

        Image background1 = Constants.BACKGROUND_CLOUD_1;
        ImageView backgroundImageView1 = new ImageView(ImageScaler.nearestNeighborScale(background1));
        backgroundImageView1.setFitWidth(Constants.BACKGROUND_WIDTH*1.2);
        backgroundImageView1.setFitHeight(Constants.BACKGROUND_HEIGHT*1.2);

        Image background2 = Constants.BACKGROUND_CLOUD_2;
        ImageView backgroundImageView2 = new ImageView(ImageScaler.nearestNeighborScale(background2));
        backgroundImageView2.setFitWidth(Constants.BACKGROUND_WIDTH*1.2);
        backgroundImageView2.setFitHeight(Constants.BACKGROUND_HEIGHT*1.2);

        Image background3 = Constants.BACKGROUND_CLOUD_3;
        ImageView backgroundImageView3 = new ImageView(ImageScaler.nearestNeighborScale(background3));
        backgroundImageView3.setFitWidth(Constants.BACKGROUND_WIDTH*1.2);
        backgroundImageView3.setFitHeight(Constants.BACKGROUND_HEIGHT*1.2);

        Image background4 = Constants.BACKGROUND_MOON;
        ImageView backgroundImageView4 = new ImageView(ImageScaler.nearestNeighborScale(background4));
        backgroundImageView4.setFitWidth(Constants.BACKGROUND_WIDTH);
        backgroundImageView4.setFitHeight(Constants.BACKGROUND_HEIGHT);

        backgroundRoot.getChildren().addAll(backgroundImageView0, backgroundImageView3, backgroundImageView4, backgroundImageView2, backgroundImageView1);

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

        for (int i = 0; i < LevelData.Levels[currentLevel].length; i++) {
            String line = LevelData.Levels[currentLevel][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case 'P':
                        player = createEntity(Constants.EntityType.PLAYER, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE, 0);
                        movePlayerLogic = new MovePlayer(player, collidableMap, enemyMap, levelWidth, keys, this);
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
                    case 'S':
                        Entity spike = createEntity(Constants.EntityType.SPIKE, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 0);
                        collidableMap.add(spike);
                        break;
                    case 'G':
                        Entity goal = createEntity(Constants.EntityType.GOAL, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 50);
                        collidableMap.add(goal);
                        break;
                    case 'E':
                        Enemy enemy = (Enemy) createEntity(Constants.EntityType.ENEMY, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE, 70);
                        //moveEnemyLogic = new MoveEnemy(enemy, collidableMap, levelWidth, keys);
                        enemyMap.add(enemy);
                        break;
                }
            }
        }

        player.hitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > Constants.BACKGROUND_WIDTH / 2 && offset < levelWidth - Constants.BACKGROUND_WIDTH / 2) {
                gameRoot.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2));
                backgroundImageView1.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.2); // Layer 1 moves at half speed
                backgroundImageView2.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.3); // Layer 2 moves at 30% speed
                backgroundImageView3.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.4); // Layer 2 moves at 30% speed
                backgroundImageView4.setLayoutX((-(offset - Constants.BACKGROUND_WIDTH / 2) * 0.05)+600 ); // Layer 2 moves at 30% speed

            }
        });
        player.hitBox().translateYProperty().addListener((obs, old, newValue) -> {
            int offsetY = newValue.intValue();
            //test
            //System.out.println(offsetY);
            if (levelHeight - offsetY > Constants.BACKGROUND_HEIGHT / 2 && offsetY > Constants.BACKGROUND_HEIGHT / 4) {
                gameRoot.setLayoutY(-(offsetY - Constants.BACKGROUND_HEIGHT / 2));
                backgroundImageView1.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.2) - 50); // Layer 1 moves at half speed
                backgroundImageView2.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.3) - 50); // Layer 2 moves at 30% speed
                backgroundImageView3.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.4) - 50); // Layer 2 moves at 30% speed
                backgroundImageView4.setLayoutY((-(offsetY - Constants.BACKGROUND_HEIGHT / 2) * 0.05) - 50); // Layer 2 moves at 30% speed
            }
        });

        appRoot.getChildren().addAll(backgroundRoot, gameRoot, uiRoot);

        move = new Move(collidableMap);

        gameScene = new Scene(appRoot);
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

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

    public static ArrayList<Entity> getCollidableMap() {
        return collidableMap;
    }

    public static ArrayList<Enemy> getEnemyMap() {
        return enemyMap;
    }


    public void removeEnemy(Enemy enemy) {
        toRemove.add(enemy);
        gameRoot.getChildren().remove(enemy.node());
    }


    private Entity createEntity(Constants.EntityType type, int x, int y, int w, int h, int index) {
        Entity entity = EntityFactory.createEntity(type, x, y, w, h, index);
        gameRoot.getChildren().add(entity.node());
        return entity;
    }
}
