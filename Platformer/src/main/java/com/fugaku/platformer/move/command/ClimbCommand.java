package com.fugaku.platformer.move.command;

import com.fugaku.platformer.move.state.MoveState;
import com.fugaku.platformer.move.state.MoveData;
import javafx.scene.input.KeyCode;

import static com.fugaku.platformer.data.Constants.MAX_CLIMB_SPEED;
import static com.fugaku.platformer.model.GameModel.keys;

/**
 * @author Changyu Li
 * @date 2024/12/15
 */
public class ClimbCommand implements PlayCommand {
    private final MoveData moveData;
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
