package com.example.platformerplain.move.command;

import com.example.platformerplain.move.data.MoveState;
import com.example.platformerplain.move.data.MoveData;
import javafx.scene.input.KeyCode;

import static com.example.platformerplain.Constants.MAX_CLIMB_SPEED;
import static com.example.platformerplain.model.GameModel.keys;

public class ClimbCommand implements PlayCommand {
    private MoveData moveData;
    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }


    public ClimbCommand(MoveData moveData) {
        this.moveData = moveData;
    }

    @Override
    public void execute() {
        moveData.isTouchingGround = true;
        if (isPressed(KeyCode.W) && moveData.velocity.getY() >= -MAX_CLIMB_SPEED) {
            moveData.velocity.add(0, -2);
            moveData.setState(MoveState.CLIMBING);
        }
        else if (isPressed(KeyCode.S) && moveData.velocity.getY() <= MAX_CLIMB_SPEED) {
            moveData.velocity.add(0, 2);
            moveData.setState(MoveState.CLIMBING);
        }
        else if (moveData.stateIs(MoveState.CLIMBING))
            moveData.velocity.reduce(0,2);
    }
}
