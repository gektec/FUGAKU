package com.example.platformerplain.model;

import com.example.platformerplain.Constants;
import com.example.platformerplain.ScreenManager;
import com.example.platformerplain.View.CompletedScreen;
import com.example.platformerplain.View.FailScreen;
import com.example.platformerplain.View.MenuScreen;
import com.example.platformerplain.View.TransitionScreen;
import com.example.platformerplain.entities.Enemy;
import com.example.platformerplain.move.MoveState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;

public class GameModel {




    private void initContent() {
        if(isDebugMode) {
            framerateLabel.setTextFill(Color.WHITE);
            framerateLabel.setFont(new Font(18));
            framerateLabel.setTranslateX(10);
            framerateLabel.setTranslateY(10);

            playerSpeedLabel.setTextFill(Color.WHITE);
            playerSpeedLabel.setFont(new Font(18));
            playerSpeedLabel.setTranslateX(10);
            playerSpeedLabel.setTranslateY(30);

            moveStateLabel.setTextFill(Color.WHITE);
            moveStateLabel.setFont(new Font(18));
            moveStateLabel.setTranslateX(10);
            moveStateLabel.setTranslateY(50);

            // Add a Time Label
            timeLabel.setTextFill(Color.WHITE);
            timeLabel.setFont(new Font(18));
            timeLabel.setTranslateX(10);
            timeLabel.setTranslateY(70);

            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Time");
            yAxis.setLabel("Speed");

            speedChart = new LineChart<>(xAxis, yAxis);
            speedChart.setTitle("Player Speed Over Time");
            speedX = new XYChart.Series<>();
            speedX.setName("Speed X");
            speedY = new XYChart.Series<>();
            speedY.setName("Speed Y");
            speedChart.getData().addAll(speedX, speedY);
            speedChart.setTranslateX(10);
            speedChart.setTranslateY(90);
            speedChart.setPrefSize(400, 300);

        }


        // Add a Pause Button
        pauseMenu.setTextFill(Color.BLACK);
        pauseMenu.setFont(new Font(18));
        pauseMenu.setTranslateX(Constants.BACKGROUND_WIDTH - 100);
        pauseMenu.setTranslateY(30);
        pauseMenu.setText("Pause");

        pauseMenu.setOnAction(event -> togglePauseMenu());
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        instance = this;

        // Initialize ScreenManager
        screenManager = ScreenManager.getInstance(primaryStage);

        // Show the start screen
        screenManager.showScreen(new MenuScreen());

        primaryStage.setWidth(Constants.BACKGROUND_WIDTH);
        primaryStage.setHeight(Constants.BACKGROUND_HEIGHT);
        primaryStage.setResizable(false);

    }

    public void startGame(Stage primaryStage) {
        currentLevel = 1;
        isPaused = false;
        initContent();
        startLevel();
        primaryStage.setScene(gameScene);
        startGameLoop();
    }

    public void startLevel2(Stage primaryStage) {
        currentLevel = 2;
        isPaused = false;
        initContent();
        startLevel();
        primaryStage.setScene(gameScene);
        startGameLoop();
    }


    private void startGameLoop() {
        lastUpdateTime = System.currentTimeMillis(); // Initialize last update time
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60), event -> {
            long currentTime = System.currentTimeMillis();
            elapsedTime += currentTime - lastUpdateTime; // Update elapsed time based on current frame
            lastUpdateTime = currentTime; // Update last update time

            update();
            if (isDebugMode) updateLables();

            enemyMap.removeAll(toRemove);
            collidableMap.removeAll(toRemove);
        });

        gameLoop = new Timeline(frame);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }


    private void update() {
        player.update();
        if (enemyMap != null) {
            for (Enemy enemy : enemyMap) {
                enemy.update();
            }
        }
    }

    private void updateLables() {
        updateFramerate();
        updatePlayerSpeed();
        updateMoveState();
        updateTime();
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

    private void updateTime() {
        if (!isPaused) {
            long seconds = (elapsedTime / 1000) % 60; // Convert milliseconds to seconds
            long minutes = (elapsedTime / 1000) / 60; // Calculate minutes
            timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)); // Update Label
        }
    }



    private void updateMoveState() {
        MoveState moveState = movePlayerLogic.getMoveStatus().moveState;
        moveStateLabel.setText("Move State: " + moveState);
    }

    private void updatePlayerSpeed() {
        float[] speed = movePlayerLogic.getMoveStatus().velocity.get();
        playerSpeedLabel.setText("Speed: " + Arrays.toString(speed));
        speedX.getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[0])));
        speedY.getData().add(new XYChart.Data<>(timeStep++, Math.abs(speed[1])));

        // Keep only the last 60 data points
        if (speedX.getData().size() > 60) {
            speedX.getData().removeFirst();
            speedY.getData().removeFirst();
        }

        // Update x-axis bounds
        NumberAxis xAxis = (NumberAxis) speedChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) speedChart.getYAxis();
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(timeStep - 60);
        xAxis.setUpperBound(timeStep);
        yAxis.setUpperBound(20);
    }


    public void transitionToNextLevel() {
        stopGameLoop();
        if(currentLevel == 1) {
            currentScore(elapsedTime);
            screenManager.showScreen(new TransitionScreen());
        }else{
            currentScore(elapsedTime);
            screenManager.showScreen(new CompletedScreen());
        }
    }

    public void startNextLevel(){
        startTime = System.currentTimeMillis(); // Reset start time
        elapsedTime = 0; // Reset elapsed time
        currentLevel = 2;
        startLevel();
        primaryStage.setScene(gameScene);
        startGameLoop();
    }

    public void restartLevel(){
        isPaused = false;
        startTime = System.currentTimeMillis(); // Reset start time
        elapsedTime = 0; // Reset elapsed time
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
        screenManager.showScreen(new FailScreen());
    }



    public void resumeGame() {
        isPaused = false;
        startTime = System.currentTimeMillis() - elapsedTime;// Ensure time continuity
        startGameLoop();
    }

    public void currentScore(long elapsedTime) {
        int maxScore = 1000;
        int penaltyPerSecond = 10;

        // Calculate elapsed time in seconds for the score
        long secondsElapsed = elapsedTime / 1000;
        totalTime += secondsElapsed;

        currentScore = (int) (maxScore - (penaltyPerSecond * secondsElapsed) + (killedEnemy * 200));

        // Ensure currentScore does not drop below 0
        if (currentScore < 0) {
            currentScore = 0;
        }

        finalScore += currentScore;
    }
}
