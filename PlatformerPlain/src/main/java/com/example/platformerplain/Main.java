package com.example.platformerplain;

import com.example.platformerplain.Controller.FailScreenController;
import com.example.platformerplain.Controller.StartScreenController;
import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityFactory;

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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
    private Scene gameScene;  // Ensure gameScene is declared and initialized
    private Scene exitScene;  // Ensure exitScene is declared and initialized

    private static long startTime;

    private Label framerateLabel = new Label();
    private long lastTime = 0;
    private int frameCount = 0;
    // Add a static instance to Main
    private static Main instance;
    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        startTime = System.currentTimeMillis();
        //initContent();  // Initialize the game content and scene
        instance = this;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/start_screen.fxml"));
            Parent startScreen = loader.load();
            Scene startScene = new Scene(startScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);

            // Pass the primaryStage to the StartScreenController
            StartScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("Platformer Game");
            primaryStage.setScene(startScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startGame(Stage primaryStage) {
        initContent();
        primaryStage.setScene(gameScene);

        // Start the game loop
        startGameLoop();
    }

    public void exitGame() {  // Remove the Stage parameter
        try {
            if (primaryStage == null) {  // Use the class level primaryStage field
                System.err.println("Primary stage is null!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/fail_screen.fxml"));
            Parent exitScreen = loader.load();
            Scene exitScene = new Scene(exitScreen, Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);

            FailScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("EXIT AND TRY AGAIN!");
            primaryStage.setScene(exitScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }



    private Timeline gameLoop;

    private void startGameLoop() {
        // 设置每帧的持续时间为约16.67毫秒，即1秒/60
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60), event -> {
            update();
            updateFramerate();
        });

        // 创建时间线并添加帧
        gameLoop = new Timeline(frame);
        gameLoop.setCycleCount(Timeline.INDEFINITE); // 设置时间线为无限循环
        gameLoop.play(); // 开始动画
    }

    private void update() {
        movePlayerLogic.update();  // Update player logic
        for (Entity enemy : enemyMap) {
            if (moveEnemyLogic != null) {
                moveEnemyLogic.update();  // Update enemy logic
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

    public static Main getInstance() {
        return instance;
    }

    private void initContent() {
        // Clear existing content
        appRoot.getChildren().clear();
        gameRoot.getChildren().clear();
        uiRoot.getChildren().clear();

        Rectangle bg = new Rectangle(Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT);
        levelWidth = LevelData.Level1[0].length() * Constants.TILE_SIZE;

        // Create a text element for the title
        Text title = new Text("Try to get the goal");
        title.setFont(new Font(36));  // Set the font size
        title.setFill(Color.YELLOW);  // Set the text color to yellow
        double textWidth = title.getLayoutBounds().getWidth();
        title.setX((Constants.BACKGROUND_WIDTH - textWidth) / 2);
        title.setY(40);  // Position Y for visibility at the top middle

        uiRoot.getChildren().add(title);

        for (int i = 0; i < LevelData.Level1.length; i++) {
            String line = LevelData.Level1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case 'L':
                        Entity platformLeft = createEntity(Constants.EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 3);
                        collidableMap.add(platformLeft);
                        break;
                    case 'M':
                        Entity platform = createEntity(Constants.EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 4);
                        collidableMap.add(platform);
                        break;
                    case 'R':
                        Entity platformRight = createEntity(Constants.EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 5);
                        collidableMap.add(platformRight);
                        break;
                    case'l':
                        Entity platformLeftLow = createEntity(Constants.EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 13);
                        collidableMap.add(platformLeftLow);
                        break;
                    case'm':
                        Entity platformLow = createEntity(Constants.EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 14);
                        collidableMap.add(platformLow);
                        break;
                    case'r':
                        Entity platformRightLow = createEntity(Constants.EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 15);
                        collidableMap.add(platformRightLow);
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
        player.node().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > Constants.BACKGROUND_WIDTH / 2 && offset < levelWidth - Constants.BACKGROUND_WIDTH / 2) {
                gameRoot.setLayoutX(-(offset - Constants.BACKGROUND_WIDTH / 2));
            }
        });

        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);
        movePlayerLogic = new MovePlayer(player, collidableMap, enemyMap, levelWidth, keys, this);

        move = new Move(collidableMap);

        // Initialize the game scene with the appRoot
        gameScene = new Scene(appRoot);
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        // Add the framerateLabel to the uiRoot
        framerateLabel.setTextFill(Color.WHITE);  // Set the text color to white
        framerateLabel.setFont(new Font(18));  // Set the font size
        framerateLabel.setTranslateX(10);  // Position X
        framerateLabel.setTranslateY(10);  // Position Y
        uiRoot.getChildren().add(framerateLabel);
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
