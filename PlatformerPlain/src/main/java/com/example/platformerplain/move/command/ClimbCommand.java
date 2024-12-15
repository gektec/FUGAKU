package com.example.platformerplain.move.command;

import com.example.platformerplain.move.state.MoveState;
import com.example.platformerplain.move.state.MoveData;
import javafx.scene.input.KeyCode;

import static com.example.platformerplain.data.Constants.MAX_CLIMB_SPEED;
import static com.example.platformerplain.model.GameModel.keys;

/**
 * @author Changyu Li
 * @date 2024/12/15
 */
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
