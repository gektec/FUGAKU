package com.example.platformerplain;

import com.example.platformerplain.map.EntityFactory;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


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
    private MovePlayer movePlayerLogic;
    private Move move;
    private Scene menuScene;
    private Scene gameScene;

    private Label framerateLabel = new Label();
    private long lastTime = 0;
    private int frameCount = 0;



    private void initMenu(Stage primaryStage) {
        StackPane menuRoot = new StackPane();

        // Load the background image for the menu
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/background.png"));
        ImageView bgImageView = new ImageView(backgroundImage);
        bgImageView.setFitWidth(BACKGROUND_WIDTH);
        bgImageView.setFitHeight(BACKGROUND_HEIGHT);
        bgImageView.setPreserveRatio(false);

        Text instructions = new Text("Use 'W' to Jump, 'A' to MovePlayer Left, 'D' to MovePlayer Right");
        instructions.setFont(new Font(24));
        instructions.setFill(Color.LIGHTGRAY);
        instructions.setTranslateY(-50);

        Button startButton = new Button("Click to Play!");
        startButton.setFont(new Font(18));
        startButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
        startButton.setOnAction(e -> primaryStage.setScene(gameScene));

        menuRoot.getChildren().addAll(bgImageView, instructions, startButton);
        StackPane.setAlignment(instructions, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(startButton, javafx.geometry.Pos.CENTER);
        startButton.setTranslateY(50);

        // Set dark gray background
        menuScene = new Scene(menuRoot, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
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

        movePlayerLogic = new MovePlayer(player, entitymap, levelWidth, keys);
        move = new Move(entitymap);

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

    @Override
    public void start(Stage primaryStage) {
        initMenu(primaryStage);  // Initialize the menu screen
        initContent();

        primaryStage.setTitle("PlatformerGame");
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60), event -> {
            if (primaryStage.getScene() == gameScene) {
                movePlayerLogic.update();  // Update logic runs only when the game has started

                // Update framerate
                if (lastTime > 0) {
                    frameCount++;
                    if (System.nanoTime() - lastTime >= 1_000_000_000) {
                        framerateLabel.setText("FPS: " + frameCount);
                        frameCount = 0;
                        lastTime = System.nanoTime();
                    }
                } else {
                    lastTime = System.nanoTime();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
