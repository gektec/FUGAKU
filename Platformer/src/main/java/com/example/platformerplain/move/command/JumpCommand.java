package com.example.platformerplain.move.command;

import com.example.platformerplain.move.state.MoveData;
import com.example.platformerplain.move.state.MoveState;

/**
 * @author Zelin Xia
 * @date 2024/12/2
 */
public class JumpCommand implements PlayCommand {
    private final MoveData moveData;

    public JumpCommand(MoveData moveData) {
        this.moveData = moveData;
    }

    @Override
    public void execute() {
        moveData.velocity.setY(-20);
        moveData.isTouchingGround = false;
        moveData.setState(MoveState.JUMPING);
    }
}
