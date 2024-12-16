package com.fugaku.platformer.view;

import com.fugaku.platformer.controller.GameScreenController;
import com.fugaku.platformer.data.AssetManager;
import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.data.LevelData;
import com.fugaku.platformer.model.GameModel;
import com.fugaku.platformer.model.GameModelObserver;
import com.fugaku.platformer.texture.ImageScaler;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Represents the main game screen where the game is displayed.
 * Implements the {@link Screen} interface and {@link GameModelObserver}.
 * Responsible for initializing and managing the game's UI components, background, and camera follow behavior.
 *
 * @autho Changyu Li
 * @date 2024/12/11
 */
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
    private static final Label framerateLabel = new Label();
    private static final Label moveStateLabel = new Label();
    private static final Label playerSpeedLabel = new Label();
    private static final Label timeLabel = new Label();
    private static final Label positionLabel = new Label();
    private static final NumberAxis xAxis = new NumberAxis();
    private static final NumberAxis yAxis = new NumberAxis();
    private static final LineChart<Number, Number> speedChart = new LineChart<>(xAxis, yAxis);
    private static XYChart.Series<Number, Number> speedX;
    private static XYChart.Series<Number, Number> speedY;

    // label
    private static final Label scoreLabel = new Label();
    private static final Label killedLabel = new Label();

    // Main
    private static final Button pauseMenu = new Button();
    private static int labelNumber = 0;

    static ImageView backgroundSky;
    static ImageView backgroundMountain1;
    static ImageView backgroundMountain2;
    static ImageView backgroundCloud1;
    static ImageView backgroundCloud2;
    static ImageView backgroundCloud3;
    static ImageView backgroundMoon;

    /**
     * Constructs a new {@code GameScreen} for the provided level.
     *
     * @param level the level number to initialize the game screen for.
     */
    public GameScreen(int level) {
        GameScreen.level = level;
    }

    /**
     * Displays the game screen.
     *
     * @param primaryStage the primary stage on which the game screen will be displayed.
     */
    @Override
    public void show(Stage primaryStage) {
        GameModel.startGame(primaryStage, level);
    }

    /**
     * Initializes content for the game screen, including panes, labels, and pause button.
     */
    public static void initContent() {

        initPanes();

        initializePauseButton();

        labelNumber = 0;
        initLabels();

        GameScreen instance = new GameScreen(level);
        GameModel.addObserver(instance);
    }

    /**
     * Sets the properties for a given label, including font and position.
     *
     * @param label the label to set properties for.
     */
    private static void setLable(Label label){
        label.setFont(AssetManager.loadFont(Assets.baseFont, 18));
        label.setTranslateX(10);
        label.setTranslateY(10 + labelNumber * 20);
        labelNumber++;
    }

    /**
     * Initializes various labels used in the game UI.
     */
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

            speedChart.setTitle("Player Speed Over Time");
            speedX = new XYChart.Series<>();
            speedY = new XYChart.Series<>();
            speedChart.getData().addAll(speedX, speedY);
            speedChart.setTranslateX(10);
            speedChart.setTranslateY(10 + labelNumber * 20);
            speedChart.setPrefSize(400, 300);
        }
    }

    /**
     * Initializes the pause button and sets its properties and action events.
     */
    private static void initializePauseButton() {
        pauseMenu.getStyleClass().add("button");
        pauseMenu.setTranslateX(Constants.WINDOW_WIDTH - 150);
        pauseMenu.setTranslateY(30);
        pauseMenu.setText("Pause");
        pauseMenu.setOnAction(event -> GameModel.togglePauseMenu());
    }

    /**
     * Initializes the panes used in the game screen.
     */
    private static void initPanes(){
        appRoot = new Pane();
        gameRoot = new Pane();
        uiRoot = new Pane();
        backgroundRoot = new Pane();
    }

    /**
     * Initializes UI elements and adds them to the UI root pane.
     */
    private static void initUIElements() {
        uiRoot.getChildren().add(pauseMenu);
        uiRoot.getChildren().add(scoreLabel);
        uiRoot.getChildren().add(killedLabel);
        if (GameModel.isDebugMode()) {
            uiRoot.getChildren().add(timeLabel);
            uiRoot.getChildren().add(framerateLabel);
            uiRoot.getChildren().add(playerSpeedLabel);
            uiRoot.getChildren().add(speedChart);
            uiRoot.getChildren().add(moveStateLabel);
            uiRoot.getChildren().add(positionLabel);
        }
    }

    /**
     * Initializes the background images and adds them to the background root pane.
     */
    private static void initBackground(){
//        backgroundSky = new ImageView(Assets.BACKGROUND_SKY);
//        backgroundSky.setFitWidth(Constants.WINDOW_WIDTH * 5);
//        backgroundSky.setFitHeight(Constants.WINDOW_HEIGHT * 5);

        backgroundMountain1 = new ImageView(ImageScaler.nearestNeighborScale(Assets.BACKGROUND_MOUNTAIN_1, 3));
        backgroundMountain1.setFitWidth(Constants.WINDOW_WIDTH * 1.8);
        backgroundMountain1.setFitHeight(Constants.WINDOW_HEIGHT * 1.8);

        backgroundMountain2 = new ImageView(ImageScaler.nearestNeighborScale(Assets.BACKGROUND_MOUNTAIN_2, 3));
        backgroundMountain2.setFitWidth(Constants.WINDOW_WIDTH * 1.6);
        backgroundMountain2.setFitHeight(Constants.WINDOW_HEIGHT * 1.6);

        backgroundCloud1 = new ImageView(ImageScaler.nearestNeighborScale(Assets.BACKGROUND_CLOUD_1, 2));
        backgroundCloud1.setFitWidth(Constants.WINDOW_WIDTH * 1.5);
        backgroundCloud1.setFitHeight(Constants.WINDOW_HEIGHT * 1.5);

        backgroundCloud2 = new ImageView(ImageScaler.nearestNeighborScale(Assets.BACKGROUND_CLOUD_2, 2));
        backgroundCloud2.setScaleX(-1);
        backgroundCloud2.setFitWidth(Constants.WINDOW_WIDTH * 1.3);
        backgroundCloud2.setFitHeight(Constants.WINDOW_HEIGHT * 1.3);

        backgroundCloud3 = new ImageView(ImageScaler.nearestNeighborScale(Assets.BACKGROUND_CLOUD_3, 2));
        backgroundCloud3.setFitWidth(Constants.WINDOW_WIDTH * 1.2);
        backgroundCloud3.setFitHeight(Constants.WINDOW_HEIGHT * 1.2);

        backgroundMoon = new ImageView(ImageScaler.nearestNeighborScale(Assets.BACKGROUND_MOON, 2));
        backgroundMoon.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundMoon.setFitHeight(Constants.WINDOW_HEIGHT);

        //backgroundRoot.getChildren().addAll(backgroundSky, backgroundMoon, backgroundCloud3, backgroundCloud2, backgroundCloud1, backgroundMountain2, backgroundMountain1);
        backgroundRoot.getChildren().addAll(backgroundMoon, backgroundCloud3, backgroundCloud2, backgroundCloud1, backgroundMountain2, backgroundMountain1);

    }

    /**
     * Sets the camera follow behavior to track the player's movements.
     * The closer the background image is to the camera, the faster it moves.
     */
    private static void setCameraFollow(){
        GameModel.player.hitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > Constants.WINDOW_WIDTH / 2 && offset < levelWidth - (Constants.WINDOW_WIDTH - Constants.PLAYER_SIZE) / 2) {
                gameRoot.setLayoutX(-(offset - (double) Constants.WINDOW_WIDTH / 2));
                backgroundMountain1.setLayoutX(-(offset - (double) Constants.WINDOW_WIDTH / 2) * 0.6);
                backgroundMountain2.setLayoutX(-(offset - (double) Constants.WINDOW_WIDTH / 2) * 0.5);
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
                backgroundMountain1.setLayoutY(-(offsetY - (double) Constants.WINDOW_WIDTH / 2) * 0.6 - 100);
                backgroundMountain2.setLayoutY(-(offsetY - (double) Constants.WINDOW_WIDTH / 2) * 0.5 - 100);
                backgroundCloud1.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.4) - 100);
                backgroundCloud2.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.3) - 100);
                backgroundCloud3.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.2) - 100);
                backgroundMoon.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.05) - 100);

            }
        });
    }

    /**
     * Creates the game scene and applies styles and key events.
     */
    private static void createGameScene() {
        gameScene = new Scene(appRoot);
        gameScene.getStylesheets().add(Objects.requireNonNull(GameScreen.class.getResource("/com/fugaku/platformer/styles.css")).toExternalForm());
        GameScreenController.setKeys(gameScene);
    }

    /**
     * Starts the level by initializing background, setting camera follow, and creating the game scene.
     */
    public static void startLevel() {
        // Set up the background
        initBackground();

        // Set background color based on Option
        String colorStr = String.format("#%02X%02X%02X",
                (int) (GameModel.getColor().getRed() * 255),
                (int) (GameModel.getColor().getGreen() * 255),
                (int) (GameModel.getColor().getBlue() * 255));
        appRoot.setStyle("-fx-background-color: " + colorStr + ";");

        levelWidth = LevelData.getLevelInformation.getLevelWidth();
        levelHeight = LevelData.getLevelInformation.getLevelHeight();

        // Position the game view
        gameRoot.setLayoutY(-(levelHeight - Constants.WINDOW_HEIGHT));
        gameRoot.setLayoutX(0);

        // Position the background images
        int offsetY = levelHeight - Constants.WINDOW_HEIGHT/2;
        backgroundMountain1.setLayoutY(-(offsetY - (double) Constants.WINDOW_WIDTH / 2) * 0.6 - 100);
        backgroundMountain2.setLayoutY(-(offsetY - (double) Constants.WINDOW_WIDTH / 2) * 0.5 - 100);
        backgroundCloud1.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.4) - 100);
        backgroundCloud2.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.3) - 100);
        backgroundCloud3.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.2) - 100);
        backgroundMoon.setLayoutY((-(offsetY - (double) Constants.WINDOW_HEIGHT / 2) * 0.05) - 100);

        setCameraFollow();

        // Add roots to the scene
        appRoot.getChildren().addAll(backgroundRoot, gameRoot, uiRoot);

        // Create game scene
        createGameScene();

        // UI Elements
        initUIElements();

    }



    /**
     * Retrieves the game root pane.
     * This pane contains all game-related nodes and is used for rendering the game scene.
     *
     * @return the game root pane
     */
public static Pane getGameRoot() {
        return gameRoot;
    }

    /**
     * Retrieves the UI root pane.
     * This pane contains all UI-related nodes and overlays used for player interface elements.
     *
     * @return the UI root pane
     */
    public static Pane getUIRoot() {
        return uiRoot;
    }

    /**
     * Retrieves the background root pane.
     * This pane contains all background images used in the game.
     *
     * @return the background root pane
     */
    public static Pane getBackgroundRoot() {
        return backgroundRoot;
    }

    /**
     * Retrieves the game scene.
     * This scene contains all nodes and UI elements for the game window.
     *
     * @return the game scene
     */
    public static Scene getGameScene() {
        return gameScene;
    }

    /**
     * Retrieves the framerate label used for displaying debug information.
     *
     * @return the framerate label
     */
    public static Label getFramerateLabel() {
        return framerateLabel;
    }

    /**
     * Retrieves the move state label used for displaying debug information.
     *
     * @return the move state label
     */
    public static Label getMoveStateLabel() {
        return moveStateLabel;
    }

    /**
     * Retrieves the player speed label used for displaying debug information.
     *
     * @return the player speed label
     */
    public static Label getPlayerSpeedLabel() {
        return playerSpeedLabel;
    }

    /**
     * Retrieves the time label used for displaying debug information.
     *
     * @return the time label
     */
    public static Label getTimeLabel() {
        return timeLabel;
    }

    /**
     * Retrieves the position label used for displaying debug information.
     *
     * @return the position label
     */
    public static Label getPositionLabel() {
        return positionLabel;
    }


    /**
     * Retrieves the speed chart used for displaying player speed over time.
     *
     * @return the speed chart
     */
    public static LineChart<Number, Number> getSpeedChart() {
        return speedChart;
    }

    /**
     * Retrieves the X-axis state series for the speed chart.
     *
     * @return the X-axis state series
     */
    public static XYChart.Series<Number, Number> getSpeedX() {
        return speedX;
    }

    /**
     * Retrieves the Y-axis state series for the speed chart.
     *
     * @return the Y-axis state series
     */
    public static XYChart.Series<Number, Number> getSpeedY() {
        return speedY;
    }


    /**
     * Notifies observers when the score has changed.
     * This method updates the score label with the new score value.
     *
     * @param newScore the new score value after the change
     */
    @Override
    public void onScoreChanged(int newScore) {
        Platform.runLater(() -> scoreLabel.setText("Score: " + newScore));
    }

    /**
     * Notifies observers when an enemy has been killed.
     * This method updates the enemies killed label with the new total.
     *
     * @param totalKilled the total number of enemies killed so far
     */
    @Override
    public void onEnemyKilled(int totalKilled) {
        Platform.runLater(() -> killedLabel.setText("Enemies Killed: " + totalKilled));
    }
}