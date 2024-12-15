package com.example.platformerplain;

import com.example.platformerplain.move.MovePlayer;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

/**
 * @author lcy
 * @date 2024/12/15
 */
public class BaseTest {
    @BeforeEach
    public void setUp() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    /**
     *
     */
    @Test
    public void test() throws InterruptedException {
        // simulate button click
        Thread.sleep(3000);
        FxRobot robot = new FxRobot();

        robot.clickOn("#startButton");
        Thread.sleep(1000);

        verifyThat("#FirstLevel", LabeledMatchers.hasText("1"));

        robot.clickOn("#FirstLevel");
        Thread.sleep(1000);

        double x1 = MovePlayer.getPlayerPosition().getX();
        robot.press(KeyCode.D);
        Thread.sleep(100);
        robot.release(KeyCode.D);
        double x2 = MovePlayer.getPlayerPosition().getX();

        assert x1 < x2;

        double previousY = MovePlayer.getPlayerPosition().getY();
        boolean isFalling = false;
        robot.press(KeyCode.J);
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

        assert isFalling;


    }

}
