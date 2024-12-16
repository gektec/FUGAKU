package com.example.platformerplain.move.state;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.texture.Animation;

/**
 * @author Changyu Li
 * @date 2024-12-15
 **/
class FallingState implements MoveStateHandler {
    @Override
    public void analyzeState(MoveData moveData) {
        // Implement Falling state-specific logic
        if(moveData.isTouchingGround){
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
        if (lastState != MoveState.FALLING) {
            animation.setFrames(Assets.PLAYER_JUMP_FALL[0]);
            animation.setDelay(5);
            player.setLastState(MoveState.FALLING);
        }
    }
}
