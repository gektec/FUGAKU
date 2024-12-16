package com.fugaku.platformer.move.state;

import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.entities.moveable.Player;
import com.fugaku.platformer.move.MovePlayer;
import com.fugaku.platformer.texture.Animation;

import static com.fugaku.platformer.data.Assets.DASH_SFX;

/**
 * @author Changyu Li
 * @date 2024-12-15
 **/
class DashingState implements MoveStateHandler {
    int lastAfterimageFrame = 0;
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
        lastAfterimageFrame++;
        if (lastAfterimageFrame > 1) {
            lastAfterimageFrame = 0;
            player.generateAfterimage();
        }
        if (lastState != MoveState.DASHING) {
            if (MovePlayer.getMoveData().velocity.getX() != 0) {
                animation.setFrames(Assets.PLAYER_DASH[0]);
                animation.setDelay(3);
            } else if (MovePlayer.getMoveData().velocity.getY() > 0) {
                animation.setFrames(Assets.PLAYER_JUMP_FALL[0]);
                animation.setDelay(3);
            } else {
                animation.setFrames(Assets.PLAYER_JUMP_START[0]);
                animation.setDelay(3);
            }
            DASH_SFX.play();
            player.setLastState(MoveState.DASHING);
        }
    }
}
