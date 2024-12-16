package com.example.platformerplain;

import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.MovePlayer;
import com.example.platformerplain.move.state.MoveState;
import com.example.platformerplain.view.GameScreen;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

/**
 * @author Changyu Li
 * @description <p>test if player can move correctly</p>
 * @date 2024-12-16 08:16
 **/
public class MoveTest {
    @BeforeEach
    public void setUp() throws Exception {
        ApplicationTest.launch(Main.class);
        GameModel.setDebugMode(true);
        Platform.runLater(() -> {
            try {
                GameModel.startGame(Main.getPrimaryStage(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 确保 FX 线程操作完成
        Thread.sleep(1000);  // 延时可能需要根据实际情况调整
    }

    @Test
    public void test() throws InterruptedException {
        FxRobot robot = new FxRobot();
        // Test player movement
        double x1 = MovePlayer.getPlayerPosition().getX();
        robot.press(KeyCode.D);
        Thread.sleep(600);
        robot.release(KeyCode.D);
        double x2 = MovePlayer.getPlayerPosition().getX();

        assert x1 < x2;


        // Test player jumping
        double previousY = MovePlayer.getPlayerPosition().getY();
        boolean isFalling = false;
        robot.press(KeyCode.J);
        robot.press(KeyCode.D);
        while (true) {
            Thread.sleep(100);
            double currentY = MovePlayer.getPlayerPosition().getY();
            if (currentY > previousY) {
                isFalling = true;
                break;
            }
            previousY = currentY;
        }
        robot.release(KeyCode.J);
        robot.release(KeyCode.D);

        assert isFalling;

        // Test player dashing
        robot.press(KeyCode.D);
        robot.press(KeyCode.K);
        Thread.sleep(50);
        robot.release(KeyCode.D);
        robot.release(KeyCode.K);

        assert MovePlayer.getMoveData().stateIs(MoveState.DASHING);

        Thread.sleep(100);

        // Test player climbing
        robot.press(KeyCode.W);
        Thread.sleep(300);
        robot.release(KeyCode.W);

        assert MovePlayer.getMoveData().stateIs(MoveState.CLIMBING);

        // Test player sliding

        robot.press(KeyCode.D);
        Thread.sleep(400);
        robot.release(KeyCode.D);

        assert MovePlayer.getMoveData().stateIs(MoveState.SLIDING);

        // Test player wall jumping

        Thread.sleep(1000);
        robot.press(KeyCode.J);
        Thread.sleep(20);
        robot.release(KeyCode.J);

        assert MovePlayer.getMoveData().stateIs(MoveState.SLIDE_JUMPING);

        // Test goal reaching

        Thread.sleep(3000);
        robot.press(KeyCode.A);
        Thread.sleep(3000);
        robot.release(KeyCode.A);

        verifyThat("#NextLevelButton", LabeledMatchers.hasText("Next Level"));

    }
}
