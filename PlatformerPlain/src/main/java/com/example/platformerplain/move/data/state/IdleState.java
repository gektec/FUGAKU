package com.example.platformerplain.move.data.state;

import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.data.MoveData;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p></p>
 * @date 2024-12-15 19:50
 **/
class IdleState implements MoveStateHandler {
    @Override
    public void handle(Coord2D velocity, MoveData moveData) {
        // Implement Idle state-specific logic
        // For example, if the entity is not touching a wall or the ground, transition to the appropriate state
        if (!moveData.isTouchingWall && !moveData.isTouchingGround && velocity.getY() > 0) {
            moveData.moveState = MoveState.FALLING;
        }
        // Additional logic as needed
    }
}
