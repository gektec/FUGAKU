package com.fugaku.platformer.move.state;

import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.entities.moveable.Player;
import com.fugaku.platformer.texture.Animation;

/**
 * @author Changyu Li
 * @date 2024-12-15
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
        if(!moveData.isTouchingGround){
            if(moveData.velocity.getY() <= 0) moveData.setState(MoveState.JUMPING);
            else moveData.setState(MoveState.FALLING);
        }

    }

    /**
     * @param player
     * @param lastState
     * @param animation
     */
    @Override
    public void updatePlayer(Player player, MoveState lastState, Animation animation) {
        if (lastState != MoveState.IDLE) {
            animation.setFrames(Assets.PLAYER_IDLE[0]);
            animation.setDelay(10);
            player.setLastState(MoveState.IDLE);
        }
    }
}
