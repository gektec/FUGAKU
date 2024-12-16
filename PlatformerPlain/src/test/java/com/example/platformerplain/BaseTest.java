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
        // Test button click
        Thread.sleep(2000);
        FxRobot robot = new FxRobot();

        robot.clickOn("#startButton");
        Thread.sleep(1000);

        verifyThat("#FirstLevel", LabeledMatchers.hasText("I"));

        robot.clickOn("#FirstLevel");
        Thread.sleep(1000);

        robot.clickOn("pauseMenu");

        Thread.sleep(1000);

    }

}
