// src/com/example/platformerplain/Main.java
package com.example.platformerplain;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
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
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private ArrayList<Node> platforms = new ArrayList<>();
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Node player;
    private int levelWidth;
    private Move moveLogic;

    private void initContent() {
        Rectangle bg = new Rectangle(1280, 720);
        levelWidth = LevelData.Level1[0].length() * 60;

        for (int i = 0; i < LevelData.Level1.length; i++) {
            String line = LevelData.Level1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Node platform = createEntity(j * 60, i * 60, 60, 60, Color.GREEN);
                        platforms.add(platform);
                        break;
                }
            }
        }
        player = createEntity(0, 600, 40, 40, Color.BLUE);
        player.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });
        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);

        // Initialize the Move class instance
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
    public void start(Stage primaryStage) throws Exception {
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
                moveLogic.update();
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
