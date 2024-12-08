package com.example.platformerplain.move;

public class MoveStatus {
    public MoveState moveState;
    public boolean faceLeft;
    public boolean canJump;
    public boolean canDash;
    public boolean canSlideJump;
    public Coord2D velocity;

    public MoveStatus(MoveState moveState, boolean faceLeft, boolean canJump, boolean canDash, boolean canSlideJump, Coord2D velocity) {
        this.moveState = moveState;
        this.faceLeft = faceLeft;
        this.canJump = canJump;
        this.canDash = canDash;
        this.canSlideJump = canSlideJump;
        this.velocity = velocity;
    }
}
