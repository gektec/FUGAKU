package com.example.platformerplain.move;

import com.example.platformerplain.entities.Entity;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p>Change MoveState according to current state</p>
 * @date 2024-12-15 19:38
 **/
interface MoveStateHandler {
    void handle(Coord2D velocity, MoveStatus moveStatus);
}

class IdleState implements MoveStateHandler {
    @Override
    public void handle(Coord2D velocity, MoveStatus moveStatus) {
        // Implement Idle state-specific logic
        // For example, if the entity is not touching a wall or the ground, transition to the appropriate state
        if (!moveStatus.isTouchingWall && !moveStatus.isTouchingGround && velocity.getY() > 0) {
            moveStatus.moveState = MoveState.FALLING;
        }
        // Additional logic as needed
    }
}

class SlidingState implements MoveStateHandler {
    @Override
    public void handle(Coord2D velocity, MoveStatus moveStatus) {
        // Implement Sliding state-specific logic
        if (!moveStatus.isTouchingWall) {
            moveStatus.moveState = MoveState.IDLE;
        }
    }
}

class FallingState implements MoveStateHandler {
    @Override
    public void handle(Coord2D velocity, MoveStatus moveStatus) {
        // Implement Falling state-specific logic
        if (velocity.getY() < 0) {
            moveStatus.moveState = MoveState.JUMPING;
        }
    }
}

class JumpingState implements MoveStateHandler {
    @Override
    public void handle(Coord2D velocity, MoveStatus moveStatus) {
        // Implement Jumping state-specific logic
        // For example, handling transitions to other states
    }
}
// Add other states as needed...
