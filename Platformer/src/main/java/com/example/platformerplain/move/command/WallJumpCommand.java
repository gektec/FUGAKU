package com.example.platformerplain.move.command;

import com.example.platformerplain.data.Constants;
import com.example.platformerplain.move.state.MoveData;
import com.example.platformerplain.move.state.MoveState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import static com.example.platformerplain.data.Assets.JUMP_SFX;

/**
 * @author Changyu Li
 * @date 2024/12/15
 */
public class WallJumpCommand implements PlayCommand {
    private final MoveData moveData;
    private final Timeline slideJumpCooldownTimer;


    public WallJumpCommand(MoveData moveData) {
        this.moveData = moveData;
        slideJumpCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.SLIDE_JUMP_DURATION), event -> moveData.setState(MoveState.IDLE)));
        slideJumpCooldownTimer.setCycleCount(1);
    }

    @Override
    public void execute() {
        moveData.velocity.setY(-Constants.SLIDE_JUMP_SPEED);
        moveData.velocity.setX(moveData.isFacingLeft ? Constants.SLIDE_JUMP_SPEED : -Constants.SLIDE_JUMP_SPEED);
        moveData.isTouchingWall = false;
        moveData.setState(MoveState.SLIDE_JUMPING);
        JUMP_SFX.play();
        slideJumpCooldownTimer.playFromStart();
    }
}
