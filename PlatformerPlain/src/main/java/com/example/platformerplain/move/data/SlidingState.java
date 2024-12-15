package com.example.platformerplain.move.data;

import com.example.platformerplain.move.Coord2D;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p></p>
 * @date 2024-12-15 19:50
 **/
class SlidingState implements MoveStateHandler {
    @Override
    public void analyzeState(MoveData moveData) {
        if (!moveData.isTouchingWall) {
            moveData.setState(MoveState.IDLE);
        }
    }
}
