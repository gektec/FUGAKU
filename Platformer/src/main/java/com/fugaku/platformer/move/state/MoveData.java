package com.fugaku.platformer.move.state;

import com.fugaku.platformer.entities.moveable.Player;
import com.fugaku.platformer.move.Coord2D;
import com.fugaku.platformer.texture.Animation;

/**
 * Represents the movement state for a player character.
 * This class maintains the current movement state and handles the logic
 * for different movement states.
 *
 * @author Changyu Li
 * @date 2024/12/15
 */
public class MoveData {
    private MoveState moveState;
    public boolean isFacingLeft;
    public boolean isTouchingGround;
    public boolean isTouchingWall;
    public final Coord2D velocity;
    private MoveStateHandler stateHandler;

    /**
     *
     * @param moveState        the initial movement state of the player
     * @param isFacingLeft     indicates if the player is facing left
     * @param isTouchingGround indicates if the player is on the ground
     * @param isTouchingWall   indicates if the player is touching a wall
     * @param velocity         the current velocity of the player
     */
    public MoveData(MoveState moveState, boolean isFacingLeft, boolean isTouchingGround, boolean isTouchingWall, Coord2D velocity) {
        this.moveState = moveState;
        this.isFacingLeft = isFacingLeft;
        this.isTouchingGround = isTouchingGround;
        this.isTouchingWall = isTouchingWall;
        this.velocity = velocity;
    }

    /**
     * Sets the current movement state and initializes the corresponding state handler.
     *
     * @param state the new movement state
     */
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

    /**
     * Retrieves the current movement state.
     *
     * @return the current movement state
     */
    public MoveState getState() {
        return moveState;
    }

    /**
     * Analyzes the current state of the movement state using the active state handler.
     *
     * @param moveData the MoveData instance to analyze
     */
    public void analyzeState(MoveData moveData) {
        if (stateHandler != null) {
            stateHandler.analyzeState(moveData);
        }
    }

    /**
     * Updates the player appearance based on the current state and animation.
     *
     * @param player     the player to update
     * @param lastState  the last movement state of the player
     * @param animation  the animation to be applied
     */
    public void drawPlayer(Player player, MoveState lastState, Animation animation) {
        if (stateHandler != null) {
            stateHandler.updatePlayer(player, lastState, animation);
        }
    }

    /**
     * Checks if the current movement state matches the specified state.
     *
     * @param state the state to check against
     * @return true if the current state matches, false otherwise
     */
    public boolean stateIs(MoveState state) {
        return moveState == state;
    }
}
