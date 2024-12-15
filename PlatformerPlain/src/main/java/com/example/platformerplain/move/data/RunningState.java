package com.example.platformerplain.move.data;

import com.example.platformerplain.move.Coord2D;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p></p>
 * @date 2024-12-15 20:17
 **/
public class RunningState implements MoveStateHandler {
    /**
     * @param moveData
     */
    @Override
    public void analyzeState(MoveData moveData) {
        if(!moveData.isTouchingGround){
            moveData.setState(MoveState.FALLING);
        }
        if(moveData.isTouchingGround && moveData.isTouchingWall){
            moveData.setState(MoveState.IDLE);
        }
    }
}
