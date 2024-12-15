package com.example.platformerplain.move.state;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.texture.Animation;

/**
 * @author Changyu Li
 * @date 2024-12-15
 **/
class SlideJumpingState implements MoveStateHandler {
    /**
     * @param moveData
     */
    @Override
    public void analyzeState(MoveData moveData) {
    }

    /**
     * @param player
     * @param lastState
     * @param animation
     */
    @Override
    public void updatePlayer(Player player, MoveState lastState, Animation animation) {
        if (lastState != MoveState.SLIDE_JUMPING) {
            animation.setFrames(Assets.PLAYER_WALL_JUMPING[0]);
            animation.setDelay(10);
            player.setLastState(MoveState.SLIDE_JUMPING);
        }
    }
}
