package com.fugaku.platformer.move.state;

import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.entities.moveable.Player;
import com.fugaku.platformer.texture.Animation;
import javafx.util.Duration;

import static com.fugaku.platformer.data.Assets.RUN_SFX;

/**
 * @author Changyu Li
 * @date 2024-12-15
 **/
class RunningState implements MoveStateHandler {
    /**
     * @param moveData
     */
    @Override
    public void analyzeState(MoveData moveData) {
        if(!moveData.isTouchingGround){
            moveData.setState(MoveState.FALLING);
        }
        if(moveData.isTouchingGround && moveData.isTouchingWall){
            moveData.setState(MoveState.IDLE);
        }
        if(moveData.isTouchingGround && moveData.velocity.getX() == 0){
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
        if (lastState != MoveState.RUNNING) {
            animation.setFrames(Assets.PLAYER_RUN[0]);
            player.setLastState(MoveState.RUNNING);
        }
        animation.setDelay( 13 - (int) Math.abs (player.getMoveData().velocity.getX()));
        RUN_SFX.cyclePlay(Duration.millis(600 - Math.abs (player.getMoveData().velocity.getX() * 20)));
    }
}
