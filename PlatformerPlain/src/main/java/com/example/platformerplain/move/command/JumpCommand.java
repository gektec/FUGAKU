package com.example.platformerplain.move.command;

import com.example.platformerplain.move.data.MoveData;
import com.example.platformerplain.move.data.MoveState;

import static com.example.platformerplain.Assets.JUMP_SFX;

/**
 * @author Zelin Xia
 * @date 2024/12/2
 */
public class JumpCommand implements PlayCommand {
    private MoveData moveData;

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
