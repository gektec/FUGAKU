package com.fugaku.platformer.move.state;

import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.entities.moveable.Player;
import com.fugaku.platformer.texture.Animation;

/**
 * @author Changyu Li
 * @date 2024-12-15
 **/
class SlidingState implements MoveStateHandler {
    @Override
    public void analyzeState(MoveData moveData) {
        if (!moveData.isTouchingWall) {
            moveData.setState(MoveState.IDLE);
        }
        if(moveData.isTouchingGround){
            moveData.setState(MoveState.IDLE);
        }
    }

    public void updatePlayer(Player player, MoveState lastState, Animation animation) {
        if (lastState != MoveState.SLIDING) {
            animation.setFrames(Assets.PLAYER_SLIDING[0]);
            animation.setDelay(10);
            player.setLastState(MoveState.SLIDING);
        }
        if (player.getMoveData().isFacingLeft) {
            player.canvas().setTranslateX(player.canvas().getTranslateX() - 20);
        } else {
            player.canvas().setTranslateX(player.canvas().getTranslateX() + 20);
        }
    }
}
