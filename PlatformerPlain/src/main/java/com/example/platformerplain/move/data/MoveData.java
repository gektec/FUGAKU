package com.example.platformerplain.move.data;

import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.data.state.MoveState;

public class MoveData {
    public MoveState moveState;
    public boolean isFacingLeft;
    public boolean isTouchingGround;
    public boolean isTouchingWall;
    public Coord2D velocity;

    public MoveData(MoveState moveState, boolean isFacingLeft, boolean isTouchingGround, boolean isTouchingWall, Coord2D velocity) {
        this.moveState = moveState;
        this.isFacingLeft = isFacingLeft;
        this.isTouchingGround = isTouchingGround;
        this.isTouchingWall = isTouchingWall;
        this.velocity = velocity;
    }



    public boolean stateIs(MoveState state) {
        return moveState == state;
    }
}
