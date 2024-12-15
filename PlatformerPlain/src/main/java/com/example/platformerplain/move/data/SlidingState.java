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
 * @date 2024-12-15 19:50
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
