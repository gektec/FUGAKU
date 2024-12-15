package com.example.platformerplain.move.data;

import com.example.platformerplain.Assets;
import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.texture.Animation;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p></p>
 * @date 2024-12-15 20:18
 **/
public class DashingState implements MoveStateHandler {
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
                player.setLastState(MoveState.DASHING);
            } else if (GameModel.getMovePlayerLogic().getMoveData().velocity.getY() > 0) {
                animation.setFrames(Assets.PLAYER_JUMP_FALL[0]);
                animation.setDelay(3);
                player.setLastState(MoveState.DASHING);
            } else {
                animation.setFrames(Assets.PLAYER_JUMP_START[0]);
                animation.setDelay(3);
                player.setLastState(MoveState.DASHING);
            }
        }
    }
}
