package com.example.platformerplain.move;

public class MoveStatus {
    public MoveState moveState;
    public boolean moveLeft;
    public boolean canJump;
    public boolean canDash;
    public boolean canSlideJump;

    public MoveStatus(MoveState moveState,boolean moveLeft, boolean canJump, boolean canDash, boolean canSlideJump) {
        this.moveState = moveState;
        this.moveLeft = moveLeft;
        this.canJump = canJump;
        this.canDash = canDash;
        this.canSlideJump = canSlideJump;
    }
}
