package com.example.platformerplain;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneInitializer {

    private Main mainApp;
    private Stage primaryStage;
    private static final int BACKGROUND_WIDTH = 1280;
    private static final int BACKGROUND_HEIGHT = 720;

    public SceneInitializer(Main mainApp, Stage primaryStage) {
        this.mainApp = mainApp;
        this.primaryStage = primaryStage;
    }

    public Scene initMenu() {
        StackPane menuRoot = new StackPane();

        // Load the background image for the menu
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/background.png"));
        ImageView bgImageView = new ImageView(backgroundImage);
        bgImageView.setFitWidth(BACKGROUND_WIDTH);
        bgImageView.setFitHeight(BACKGROUND_HEIGHT);
        bgImageView.setPreserveRatio(false);

        Text instructions = new Text("Use 'W' to Jump, 'A' to Move Left, 'D' to Move Right");
        instructions.setFont(new Font(24));
        instructions.setTranslateY(-50);

        Button startButton = new Button("Click to Play!");
        startButton.setFont(new Font(18));
        startButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
        startButton.setOnAction(e -> primaryStage.setScene(mainApp.gameScene));

        menuRoot.getChildren().addAll(bgImageView, instructions, startButton);
        StackPane.setAlignment(instructions, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(startButton, javafx.geometry.Pos.CENTER);
        startButton.setTranslateY(50);

        return new Scene(menuRoot, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
    }

    public Scene initFailScreen() {
        StackPane failRoot = new StackPane();

        // Load the background image for the fail screen
        Image failBackgroundImage = new Image(getClass().getResourceAsStream("/images/defeat.png"));
        ImageView failBgImageView = new ImageView(failBackgroundImage);
        failBgImageView.setFitWidth(BACKGROUND_WIDTH);
        failBgImageView.setFitHeight(BACKGROUND_HEIGHT);
        failBgImageView.setPreserveRatio(false);

        Text failText = new Text("You Failed!");
        failText.setFont(new Font(36));
        failText.setTranslateY(-50);

        Button exitButton = new Button("Click to Exit");
        exitButton.setFont(new Font(18));
        exitButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
        exitButton.setOnAction(e -> primaryStage.close());

        failRoot.getChildren().addAll(failBgImageView, failText, exitButton);
        StackPane.setAlignment(failText, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(exitButton, javafx.geometry.Pos.CENTER);
        exitButton.setTranslateY(50);

        return new Scene(failRoot, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
    }
}
