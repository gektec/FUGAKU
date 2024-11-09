package com.example.platformerplain;

import com.example.platformerplain.map.EntityFactory;
import com.example.platformerplain.EntityType;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    private static final int TILE_SIZE = 60;
    private static final int PLAYER_SIZE = 40;
    private static final int BACKGROUND_WIDTH = 1280;
    private static final int BACKGROUND_HEIGHT = 720;
    private static final int PLAYER_START_X = 0;
    private static final int PLAYER_START_Y = 600;

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private ArrayList<Entity> entitymap = new ArrayList<>();
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Entity player;
    private int levelWidth;
    private Move moveLogic;
    private Scene menuScene;
    private Scene gameScene;

    private void initMenu(Stage primaryStage) {
        StackPane menuRoot = new StackPane();
        menuScene = new Scene(menuRoot, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, Color.web("#2b2b2b"));  // 设置深灰色背景

        Text instructions = new Text("Use 'W' to Jump, 'A' to Move Left, 'D' to Move Right");
        instructions.setFont(new Font(24));
        instructions.setFill(Color.LIGHTGRAY);  // 使用浅灰色字体以提高对比度
        instructions.setTranslateY(-50);  // 上移文本位置

        Button startButton = new Button("Click to Play!");
        startButton.setFont(new Font(18));
        startButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");  // 按钮颜色
        startButton.setOnAction(e -> primaryStage.setScene(gameScene));

        // 布局元素
        menuRoot.getChildren().addAll(instructions, startButton);
        StackPane.setAlignment(instructions, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(startButton, javafx.geometry.Pos.CENTER);
        startButton.setTranslateY(50);  // 下移按钮位置

        primaryStage.setScene(menuScene);
    }

    private void initContent() {
        Rectangle bg = new Rectangle(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        levelWidth = LevelData.Level1[0].length() * TILE_SIZE;

        // Create a text element for the title
        Text title = new Text("Try to get the goal");
        title.setFont(new Font(36));  // Set the font size
        title.setFill(Color.YELLOW);  // Set the text color to yellow
        // Center the text horizontally by calculating its X position
        double textWidth = title.getLayoutBounds().getWidth();
        title.setX((BACKGROUND_WIDTH - textWidth) / 2);
        title.setY(40);  // Position Y for visibility at the top middle

        uiRoot.getChildren().add(title);

        for (int i = 0; i < LevelData.Level1.length; i++) {
            String line = LevelData.Level1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Entity platform = createEntity(EntityType.PLATFORM, j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        entitymap.add(platform);
                        break;
                    case '9':
                        Entity goal = createEntity(EntityType.GOAL, j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        entitymap.add(goal);
                        break;
                }
            }
        }

        player = createEntity(EntityType.PLAYER, PLAYER_START_X, PLAYER_START_Y, PLAYER_SIZE, PLAYER_SIZE);
        player.node().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > BACKGROUND_WIDTH / 2 && offset < levelWidth - BACKGROUND_WIDTH / 2) {
                gameRoot.setLayoutX(-(offset - BACKGROUND_WIDTH / 2));
            }
        });

        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);

        moveLogic = new Move(player, entitymap, levelWidth, keys);

        gameScene = new Scene(appRoot);
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    }

    @Override
    public void start(Stage primaryStage) {
        initMenu(primaryStage);  // Initialize the menu interface
        initContent();

        primaryStage.setTitle("PlatformerGame");
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (primaryStage.getScene() == gameScene) {
                    moveLogic.update();  // 仅在游戏开始时调用更新逻辑
                }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Entity createEntity(EntityType type, int x, int y, int w, int h) {
        Entity entity = EntityFactory.createEntity(type, x, y, w, h);
        gameRoot.getChildren().add(entity.node());
        return entity;
    }
}
