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
class SlidingState implements MoveStateHandler {
    @Override
    public void handle(Coord2D velocity, MoveData moveData) {
        // Implement Sliding state-specific logic
        if (!moveData.isTouchingWall) {
            moveData.moveState = MoveState.IDLE;
        }
    }
}
