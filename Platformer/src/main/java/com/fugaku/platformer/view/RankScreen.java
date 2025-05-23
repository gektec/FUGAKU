package com.fugaku.platformer.view;

import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.controller.RankingScreenController;
import com.fugaku.platformer.model.GameModel;
import com.fugaku.platformer.model.RankModel;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fugaku/platformer/Rank.fxml"));
            Parent rankScreen = loader.load();
            Scene rankScene = new Scene(rankScreen, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

            RankingScreenController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setYourScore(GameModel.getRankingScore());
            controller.setHighScore1Label(RankModel.getScore(0));
            controller.setHighScore2Label(RankModel.getScore(1));
            controller.setHighScore3Label(RankModel.getScore(2));


            primaryStage.setTitle("FUGAKU: Ranking");
            primaryStage.setScene(rankScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
