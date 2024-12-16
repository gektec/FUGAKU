package com.fugaku.platformer;

import com.fugaku.platformer.data.AssetManager;
import com.fugaku.platformer.data.Constants;

import com.fugaku.platformer.model.RankModel;
import com.fugaku.platformer.view.MenuScreen;
import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import static com.fugaku.platformer.data.Assets.BACKGROUND_MUSIC;

/**
 * <h3>PlatformerPlain</h3>
 * @author Changyu Li, Zelin Xia
 * @date 2024/11/5
 */
public class Main extends Application {

    private static Main instance;
    static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start the application
     * @param primaryStage the primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        instance = this;

        AssetManager.preloadAssets();
        RankModel.initializeScores();

        // Show the start screen
        new MenuScreen().show(Main.getPrimaryStage());

        primaryStage.setWidth(Constants.WINDOW_WIDTH);
        primaryStage.setHeight(Constants.WINDOW_HEIGHT);
        primaryStage.setResizable(false);

        BACKGROUND_MUSIC.play();
        BACKGROUND_MUSIC.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
    }

    /**
     * Gets the instance of the Main class.
     * @return the current instance of Main.
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Gets the primary stage of the application.
     * @return the primary stage.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void putDebugInfo(String message) {
        System.out.println("[DEBUG]: " + message);
    }
}
