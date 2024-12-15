package com.example.platformerplain.move.command;

import com.example.platformerplain.Constants;
import com.example.platformerplain.move.data.MoveData;
import com.example.platformerplain.move.data.state.MoveState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import static com.example.platformerplain.Assets.JUMP_SFX;

public class WallJumpCommand implements PlayCommand {
    private MoveData moveData;
    private Timeline slideJumpCooldownTimer;


    public WallJumpCommand(MoveData moveData) {
        this.moveData = moveData;
        slideJumpCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.SLIDE_JUMP_DURATION), event -> moveData.moveState = MoveState.IDLE));
        slideJumpCooldownTimer.setCycleCount(1);
    }

    @Override
    public void execute() {
        moveData.velocity.setY(-15);
        moveData.isTouchingWall = false;
        moveData.moveState = MoveState.SLIDE_JUMPING;
        JUMP_SFX.play();
        slideJumpCooldownTimer.playFromStart();
    }
}
