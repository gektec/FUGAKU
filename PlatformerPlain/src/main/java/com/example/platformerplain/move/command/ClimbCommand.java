package com.example.platformerplain.move.command;

import com.example.platformerplain.move.MoveState;
import com.example.platformerplain.move.MoveStatus;
import javafx.scene.input.KeyCode;

import static com.example.platformerplain.Constants.MAX_CLIMB_SPEED;
import static com.example.platformerplain.model.GameModel.keys;

public class ClimbCommand implements PlayCommand {
    private MoveStatus moveStatus;
    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }


    public ClimbCommand(MoveStatus moveStatus) {
        this.moveStatus = moveStatus;
    }

    @Override
    public void execute() {
        moveStatus.isTouchingGround = true;
        if (isPressed(KeyCode.W) && moveStatus.velocity.getY() >= -MAX_CLIMB_SPEED) {
            moveStatus.velocity.add(0, -2);
            moveStatus.moveState = MoveState.CLIMBING;
        }
        else if (isPressed(KeyCode.S) && moveStatus.velocity.getY() <= MAX_CLIMB_SPEED) {
            moveStatus.velocity.add(0, 2);
            moveStatus.moveState = MoveState.CLIMBING;
        }
        else if (moveStatus.moveState == MoveState.CLIMBING)
            moveStatus.velocity.reduce(0,2);
    }
}
