package com.fugaku.platformer.move.state;

import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.entities.moveable.Player;
import com.fugaku.platformer.texture.Animation;

/**
 * @author Changyu Li
 * @date 2024-12-15
 **/
class JumpingState implements MoveStateHandler {
    @Override
    public void analyzeState(MoveData moveData) {
        if(moveData.isTouchingWall && moveData.velocity.getY() > 0) {
            moveData.setState(MoveState.SLIDING);
        }
        if(moveData.velocity.getY() > 0){
            moveData.setState(MoveState.FALLING);
        }
    }

    /**
     * @param player
     * @param lastState
     * @param animation
     */
    @Override
    public void updatePlayer(Player player, MoveState lastState, Animation animation) {
        if (lastState != MoveState.JUMPING) {
            animation.setFrames(Assets.PLAYER_JUMP_START[0]);
            animation.setDelay(2);
            player.setLastState(MoveState.JUMPING);
        }
    }
}
