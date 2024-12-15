package com.example.platformerplain.view;

import com.example.platformerplain.data.Constants;
import com.example.platformerplain.controller.RankingScreenController;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.model.RankModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class manages the main menu screen of the game.
 */
public class RankScreen implements Screen {

    /**
     * Loads and displays the main menu screen on the specified stage.
     * @param primaryStage The stage to display the main menu screen on.
     */
    @Override
    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformerplain/Rank.fxml"));
            Parent rankScreen = loader.load();
            Scene rankScene = new Scene(rankScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            RankingScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setYourScore(GameModel.getFinalScore());
            controller.setHighScore1Label(RankModel.getFirstScore());
            controller.setHighScore2Label(RankModel.getSecondScore());
            controller.setHighScore3Label(RankModel.getThirdScore());


            primaryStage.setTitle("RANKING");
            primaryStage.setScene(rankScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
