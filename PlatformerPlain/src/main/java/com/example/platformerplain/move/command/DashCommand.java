package com.example.platformerplain.move.command;

import com.example.platformerplain.Constants;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.MoveState;
import com.example.platformerplain.move.MoveStatus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.example.platformerplain.Assets.DASH_SFX;
import static com.example.platformerplain.model.GameModel.keys;

public class DashCommand implements PlayCommand {
    private Coord2D velocity;
    private MoveStatus moveStatus;
    private Timeline dashCooldownTimer;
    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public DashCommand(MoveStatus moveStatus) {
        this.velocity = moveStatus.velocity;
        this.moveStatus = moveStatus;
        dashCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.DASH_DURATION), event -> moveStatus.moveState = MoveState.IDLE));
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
            DASH_SFX.play();
            moveStatus.moveState = MoveState.DASHING;
            dashCooldownTimer.playFromStart();

    }
}
}

