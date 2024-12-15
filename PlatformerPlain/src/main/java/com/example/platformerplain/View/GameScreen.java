package com.example.platformerplain.View;

import com.example.platformerplain.*;
import com.example.platformerplain.Controller.GameScreenController;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.model.GameModelObserver;
import com.example.platformerplain.texture.ImageScaler;
import javafx.application.Platform;
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
import java.util.Objects;

public class GameScreen implements Screen, GameModelObserver {

    private static Pane appRoot = new Pane();
    private static Pane gameRoot = new Pane();
    private static Pane uiRoot = new Pane();
    private static Pane backgroundRoot = new Pane();

    private static int levelWidth = -1;
    private static int levelHeight = -1;
    private static int level;
    private static Scene gameScene;

    // Debug
    private static Label framerateLabel = new Label();
    private static Label moveStateLabel = new Label();
    private static Label playerSpeedLabel = new Label();
    private static Label timeLabel = new Label();
    private static Label positionLabel = new Label();
    private static NumberAxis xAxis = new NumberAxis();
    private static NumberAxis yAxis = new NumberAxis();
    private static LineChart speedChart = new LineChart<>(xAxis, yAxis);
    private static XYChart.Series<Number, Number> speedX;
    private static XYChart.Series<Number, Number> speedY;

    // label
    private static Label scoreLabel = new Label();
    private static Label killedLabel = new Label();

    // Main
    private static Button pauseMenu = new Button();
    private static int labelNumber = 0;

    static ImageView backgroundSky;
    static ImageView backgroundCloud1;
    static ImageView backgroundCloud2;
    static ImageView backgroundCloud3;
    static ImageView backgroundMoon;

    public GameScreen(int level) {
        GameScreen.level = level;
    }


    @Override
    public void show(Stage primaryStage) {
        GameModel.getInstance().startGame(primaryStage, level);
    }


    public static void initContent() {
        GameModel gameModel = GameModel.getInstance(); // ????

        initPanes();

        initializePauseButton();

        initLabels();

        GameScreen instance = new GameScreen(level);// ????
        gameModel.addObserver(instance);
    }

    private static void setLable(Label label){
        label.setFont(AssetManager.loadFont(Assets.baseFont, 18));
        label.setTranslateX(10);
        label.setTranslateY(10 + labelNumber * 20);
        labelNumber++;
    }

    private static void initLabels() {
        scoreLabel.setText("Score: 1000");
        setLable(scoreLabel);

        killedLabel.setText("Enemies Killed: 0");
        setLable(killedLabel);

        if(GameModel.isDebugMode()) {
            setLable(framerateLabel);

            setLable(playerSpeedLabel);

            setLable(moveStateLabel);

            setLable(positionLabel);

            setLable(timeLabel);


            xAxis.setLabel("Time");
            yAxis.setLabel("Speed");

            speedChart.setTitle("Player Speed Over Time");
            speedX = new XYChart.Series<>();
            speedX.setName("Speed X");
            speedY = new XYChart.Series<>();
            speedY.setName("Speed Y");
            speedChart.getData().addAll(speedX, speedY);
            speedChart.setTranslateX(10);
            speedChart.setTranslateY(10 + labelNumber * 20);
            speedChart.setPrefSize(400, 300);
        }
    }

    private static void initializePauseButton() {
        pauseMenu.getStyleClass().add("button");
        pauseMenu.setTranslateX(Constants.WINDOW_WIDTH - 100);
        pauseMenu.setTranslateY(30);
        pauseMenu.setText("Pause");
        pauseMenu.setOnAction(event -> GameModel.getInstance().togglePauseMenu());
    }

    public static  void clearPane(){
        Pane[] panes = {appRoot, gameRoot, uiRoot, backgroundRoot};

        for (Pane pane : panes) {
            if (pane != null) {
                pane.getChildren().clear();

                if (pane.getParent() != null) {
                    ((Pane) pane.getParent()).getChildren().remove(pane);
                }
            }
        }
    }

    private static void initPanes(){
        appRoot = new Pane();
        gameRoot = new Pane();
        uiRoot = new Pane();
        backgroundRoot = new Pane();
    }

    private static void addLevelIndicatorText() {
        int levelNumber = LevelData.getLevelInformation.getLevelNumber();
        String titleText;

        if (levelNumber == 1) {
            titleText = "Try to get the goal";
        } else if (levelNumber == 2) {
            titleText = "Level 2: New Challenges Await!";
        } else if (levelNumber == 3) {
            titleText = "Level 3: The Final Battle Begins";
        } else {
            return; // Return early if the level number does not match case
        }

        Text title = new Text(titleText);
        title.setFont(new Font(36));
        title.setFill(Color.YELLOW);
        double textWidth = title.getLayoutBounds().getWidth();
        title.setX((Constants.WINDOW_WIDTH - textWidth) / 2);
        title.setY(40);
        uiRoot.getChildren().add(title);
    }

    private static void initializeUIElements() {
        uiRoot.getChildren().add(pauseMenu);
        uiRoot.getChildren().add(timeLabel);
        uiRoot.getChildren().add(scoreLabel);
        uiRoot.getChildren().add(killedLabel);
        if (GameModel.isDebugMode()) {
            uiRoot.getChildren().add(framerateLabel);
            uiRoot.getChildren().add(playerSpeedLabel);
            uiRoot.getChildren().add(speedChart);
            uiRoot.getChildren().add(moveStateLabel);
            uiRoot.getChildren().add(positionLabel);
        }
    }

    private static void initBackground(){
        Image background0 = Assets.BACKGROUND_SKY;
        backgroundSky = new ImageView(background0);
        backgroundSky.setFitWidth(Constants.WINDOW_WIDTH * 5);
        backgroundSky.setFitHeight(Constants.WINDOW_HEIGHT * 5);

        Image background1 = Assets.BACKGROUND_CLOUD_1;
        backgroundCloud1 = new ImageView(ImageScaler.nearestNeighborScale(background1, 2));
        backgroundCloud1.setFitWidth(Constants.WINDOW_WIDTH * 1.2);
        backgroundCloud1.setFitHeight(Constants.WINDOW_HEIGHT * 1.2);

        Image background2 = Assets.BACKGROUND_CLOUD_2;
        backgroundCloud2 = new ImageView(ImageScaler.nearestNeighborScale(background2, 2));
        backgroundCloud2.setScaleX(-1);
        backgroundCloud2.setFitWidth(Constants.WINDOW_WIDTH * 1.2);
        backgroundCloud2.setFitHeight(Constants.WINDOW_HEIGHT * 1.2);

        Image background3 = Assets.BACKGROUND_CLOUD_3;
        backgroundCloud3 = new ImageView(ImageScaler.nearestNeighborScale(background3, 2));
        backgroundCloud3.setFitWidth(Constants.WINDOW_WIDTH * 1.2);
        backgroundCloud3.setFitHeight(Constants.WINDOW_HEIGHT * 1.2);

        Image background4 = Assets.BACKGROUND_MOON;
        backgroundMoon = new ImageView(ImageScaler.nearestNeighborScale(background4, 2));
        backgroundMoon.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundMoon.setFitHeight(Constants.WINDOW_HEIGHT);

        backgroundRoot.getChildren().addAll(backgroundSky, backgroundCloud3, backgroundCloud2, backgroundMoon, backgroundCloud1);
    }

    private static void setCameraFollow(){
        GameModel.player.hitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > Constants.WINDOW_WIDTH / 2 && offset < levelWidth - (Constants.WINDOW_WIDTH - Constants.PLAYER_SIZE) / 2) {
                gameRoot.setLayoutX(-(offset - (double) Constants.WINDOW_WIDTH / 2));
                backgroundCloud1.setLayoutX(-(offset - (double) Constants.WINDOW_WIDTH / 2) * 0.4);
                backgroundCloud2.setLayoutX(-(offset - (double) Constants.WINDOW_WIDTH / 2) * 0.3);
                backgroundCloud3.setLayoutX(-(offset - (double) Constants.WINDOW_WIDTH / 2) * 0.2);
                backgroundMoon.setLayoutX((-(offset - (double) Constants.WINDOW_WIDTH / 2) * 0.05));
            }
        });

        GameModel.player.hitBox().translateYProperty().addListener((obs, old, newValue) -> {
            int offsetY = newValue.intValue();
            if (levelHeight - offsetY > Constants.WINDOW_HEIGHT / 2 && offsetY > Constants.WINDOW_HEIGHT / 4) {
                gameRoot.setLayoutY(-(offsetY - (double) Constants.WINDOW_HEIGHT / 2));
                backgroundCloud1.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.4) - 50);
                backgroundCloud2.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.3) - 50);
                backgroundCloud3.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.2) - 50);
                backgroundMoon.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.05) - 50);
            }
        });
    }


    private static void createGameScene() {
        gameScene = new Scene(appRoot);
        gameScene.getStylesheets().add(Objects.requireNonNull(GameScreen.class.getResource("/styles.css")).toExternalForm());
        GameScreenController.setKeys(gameScene);
    }


    public static void startLevel() {
        // Set up the background
        initBackground();

        levelWidth = LevelData.getLevelInformation.getLevelWidth();
        levelHeight = LevelData.getLevelInformation.getLevelHeight();

        // Position the game view
        gameRoot.setLayoutY(-(levelHeight - Constants.WINDOW_HEIGHT));
        gameRoot.setLayoutX(0);
        for (Node background : backgroundRoot.getChildren()) {
            background.setLayoutY(-(levelHeight - Constants.WINDOW_HEIGHT));
        }

        // Add level indicator text based on level
        addLevelIndicatorText();

        // Use LevelInitializer to set up the level
        //todo

        setCameraFollow();

        // Add roots to the scene
        appRoot.getChildren().addAll(backgroundRoot, gameRoot, uiRoot);

        // Create game scene
        createGameScene();

        // UI Elements
        initializeUIElements();

    }


    public static Pane getGameRoot() {
        return gameRoot;
    }

    public static Pane getUIRoot() {
        return uiRoot;
    }

    public static Pane getBackgroundRoot() {
        return backgroundRoot;
    }


    public static Scene getGameScene() {
        return gameScene;
    }


    public static Label getFramerateLabel() {
        return framerateLabel;
    }


    public static Label getMoveStateLabel() {
        return moveStateLabel;
    }


    public static Label getPlayerSpeedLabel() {
        return playerSpeedLabel;
    }


    public static Label getTimeLabel() {
        return timeLabel;
    }

    public static Label getPositionLabel() {
        return positionLabel;
    }



    public static LineChart<Number, Number> getSpeedChart() {
        return speedChart;
    }


    public static XYChart.Series<Number, Number> getSpeedX() {
        return speedX;
    }


    public static XYChart.Series<Number, Number> getSpeedY() {
        return speedY;
    }


    public static int getLevelWidth() {
        return levelWidth;
    }


    public static int getLevelHeight() {
        return levelHeight;
    }

    //  GameModelObserver interface
    @Override
    public void onScoreChanged(int newScore) {
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + newScore);
        });
    }

    @Override
    public void onEnemyKilled(int totalKilled) {
        Platform.runLater(() -> {
            killedLabel.setText("Enemies Killed: " + totalKilled);
        });
    }
}