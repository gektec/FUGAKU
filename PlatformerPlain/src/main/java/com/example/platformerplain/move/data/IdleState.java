package com.example.platformerplain.move.data;

import com.example.platformerplain.move.Coord2D;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p></p>
 * @date 2024-12-15 19:50
 **/
class IdleState implements MoveStateHandler {
    @Override
    public void analyzeState(MoveData moveData) {

        if (!moveData.isTouchingWall && !moveData.isTouchingGround) {
            if(moveData.velocity.getY() > 0) moveData.setState(MoveState.FALLING);
            else moveData.setState(MoveState.JUMPING);
        }
        if(moveData.isTouchingGround && moveData.velocity.getX() != 0){
            moveData.setState(MoveState.RUNNING);
        }
    }
}
