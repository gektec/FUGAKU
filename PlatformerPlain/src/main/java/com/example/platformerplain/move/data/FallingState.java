package com.example.platformerplain.move.data;

import com.example.platformerplain.Assets;
import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.texture.Animation;

import static com.example.platformerplain.Assets.LANDING_SFX;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p></p>
 * @date 2024-12-15 19:50
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
