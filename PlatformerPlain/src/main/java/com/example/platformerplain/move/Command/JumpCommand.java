package com.example.platformerplain.move.Command;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.MoveState;
import com.example.platformerplain.move.MoveStatus;

import static com.example.platformerplain.Assets.JUMP_SFX;

public class JumpCommand implements PlayCommand {
    private MoveStatus moveStatus;

    public JumpCommand(MoveStatus moveStatus) {
        this.moveStatus = moveStatus;
    }

    @Override
    public void execute() {
        moveStatus.velocity.setY(-20);
        moveStatus.isTouchingGround = false;
        moveStatus.moveState = MoveState.JUMPING;
        JUMP_SFX.play();

    }
}
