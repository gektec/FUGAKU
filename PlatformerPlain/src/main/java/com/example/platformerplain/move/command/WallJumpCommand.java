package com.example.platformerplain.move.command;

import com.example.platformerplain.Constants;
import com.example.platformerplain.move.MoveState;
import com.example.platformerplain.move.MoveStatus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import static com.example.platformerplain.Assets.JUMP_SFX;

public class WallJumpCommand implements PlayCommand {
    private MoveStatus moveStatus;
    private Timeline slideJumpCooldownTimer;


    public WallJumpCommand(MoveStatus moveStatus) {
        this.moveStatus = moveStatus;
        slideJumpCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.SLIDE_JUMP_DURATION), event -> moveStatus.moveState = MoveState.IDLE));
        slideJumpCooldownTimer.setCycleCount(1);
    }

    @Override
    public void execute() {
        moveStatus.velocity.setY(-15);
        moveStatus.isTouchingWall = false;
        moveStatus.moveState = MoveState.SLIDE_JUMPING;
        JUMP_SFX.play();
        slideJumpCooldownTimer.playFromStart();
    }
}
