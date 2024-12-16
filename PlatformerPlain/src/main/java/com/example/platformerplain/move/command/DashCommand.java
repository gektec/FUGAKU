package com.example.platformerplain.move.command;

import com.example.platformerplain.data.Constants;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.state.MoveState;
import com.example.platformerplain.move.state.MoveData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.example.platformerplain.model.GameModel.keys;

/**
 * @author Changyu Li
 * @date 2024/12/15
 */
public class DashCommand implements PlayCommand {
    private final Coord2D velocity;
    private final MoveData moveData;
    private final Timeline dashCooldownTimer;
    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public DashCommand(MoveData moveData) {
        this.velocity = moveData.velocity;
        this.moveData = moveData;
        dashCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.DASH_DURATION), event -> moveData.setState(MoveState.IDLE)));
        dashCooldownTimer.setCycleCount(1);
    }

    @Override
    public void execute() {
        int x = 0, y = 0;
        if (isPressed(KeyCode.A)) {
            x -= Constants.DASH_SPEED;
        }
        if (isPressed(KeyCode.D)) {
            x += Constants.DASH_SPEED;
        }
        if (isPressed(KeyCode.W)) {
            y -= Constants.DASH_SPEED;
        }
        if (isPressed(KeyCode.S)) {
            y += Constants.DASH_SPEED;
        }
        if (x != 0 || y != 0) {
            if (x != 0 && y != 0)
                velocity.set((float) (x / 1.6), (float) (y / 1.6));
            else
                velocity.set(x, y);
            moveData.setState(MoveState.DASHING);
            dashCooldownTimer.playFromStart();

    }
}
}

