package com.example.platformerplain.move.data;

import com.example.platformerplain.Assets;
import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.texture.Animation;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p></p>
 * @date 2024-12-15 20:17
 **/
class RunningState implements MoveStateHandler {
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
        if(moveData.isTouchingGround && moveData.velocity.getX() == 0){
            moveData.setState(MoveState.IDLE);
        }
    }

    /**
     * @param player
     * @param lastState
     * @param animation
     */
    @Override
    public void updatePlayer(Player player, MoveState lastState, Animation animation) {
        if (lastState != MoveState.RUNNING) {
            animation.setFrames(Assets.PLAYER_RUN[0]);
            animation.setDelay(5);
            player.setLastState(MoveState.RUNNING);
        }
    }
}
