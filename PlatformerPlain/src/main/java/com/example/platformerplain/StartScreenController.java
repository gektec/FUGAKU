package com.example.platformerplain;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartScreenController {

    @FXML
    private Button startButton;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // 新的启动游戏方法
    @FXML
    void handleStartGame() {
        // 调用 Main 类的 startGame 方法，传递 primaryStage
        Main.getInstance().startGame(primaryStage);
    }
}
