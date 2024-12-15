package com.example.platformerplain.move.state;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.texture.Animation;

import static com.example.platformerplain.data.Assets.DASH_SFX;

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
            if (GameModel.getMovePlayerLogic().getMoveData().velocity.getX() != 0) {
                animation.setFrames(Assets.PLAYER_DASH[0]);
                animation.setDelay(3);
            } else if (GameModel.getMovePlayerLogic().getMoveData().velocity.getY() > 0) {
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
