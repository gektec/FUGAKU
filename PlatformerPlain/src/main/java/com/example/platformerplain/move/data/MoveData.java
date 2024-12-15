package com.example.platformerplain.move.data;

import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.texture.Animation;

public class MoveData {
    private MoveState moveState;
    public boolean isFacingLeft;
    public boolean isTouchingGround;
    public boolean isTouchingWall;
    public Coord2D velocity;
    private MoveStateHandler stateHandler;

    public MoveData(MoveState moveState, boolean isFacingLeft, boolean isTouchingGround, boolean isTouchingWall, Coord2D velocity) {
        this.moveState = moveState;
        this.isFacingLeft = isFacingLeft;
        this.isTouchingGround = isTouchingGround;
        this.isTouchingWall = isTouchingWall;
        this.velocity = velocity;
    }

    public void setState(MoveState state) {
        this.moveState = state;
        switch (state) {
            case IDLE:
                this.stateHandler = new IdleState();
                break;
            case SLIDING:
                this.stateHandler = new SlidingState();
                break;
            case FALLING:
                this.stateHandler = new FallingState();
                break;
            case JUMPING:
                this.stateHandler = new JumpingState();
                break;
            case RUNNING:
                this.stateHandler = new RunningState();
                break;
            case SLIDE_JUMPING:
                this.stateHandler = new SlideJumpingState();
                break;
            case DASHING:
                this.stateHandler = new DashingState();
                break;
            case CLIMBING:
                this.stateHandler = new ClimbingState();
                break;
            default:
                throw new IllegalStateException("Unexpected state: " + state);
        }
    }

    public MoveState getState() {
        return moveState;
    }


    public void analyzeState(MoveData moveData) {
        if (stateHandler != null) {
            stateHandler.analyzeState(moveData);
        }
    }

    public void updatePlayer(Player player, MoveState lastState, Animation animation) {
        if (stateHandler != null) {
            stateHandler.updatePlayer(player, lastState, animation);
        }
    }




    public boolean stateIs(MoveState state) {
        return moveState == state;
    }
}
