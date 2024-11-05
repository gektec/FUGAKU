package com.example.platformerplain;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private ArrayList<Node> platforms = new ArrayList<>();
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Node player;
    private int levelWidth;
    private Move moveLogic;

    private void initContent() {
        Rectangle bg = new Rectangle(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        levelWidth = LevelData.Level1[0].length() * TILE_SIZE;

        for (int i = 0; i < LevelData.Level1.length; i++) {
            String line = LevelData.Level1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Node platform = createEntity(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.GREEN);
                        platforms.add(platform);
                        break;
                }
            }
        }
        player = createEntity(PLAYER_START_X, PLAYER_START_Y, PLAYER_SIZE, PLAYER_SIZE, Color.BLUE);
        player.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > BACKGROUND_WIDTH / 2 && offset < levelWidth - BACKGROUND_WIDTH / 2) {
                gameRoot.setLayoutX(-(offset - BACKGROUND_WIDTH / 2));
            }
        });
        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);

        moveLogic = new Move(player, platforms, levelWidth, keys);
    }

    private Node createEntity(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        gameRoot.getChildren().add(entity);
        return entity;
    }

    @Override
    public void start(Stage primaryStage) {
        initContent();
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("Sample game");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveLogic.update();  // 使用新的移动逻辑类
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
