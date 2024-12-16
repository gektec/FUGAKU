package com.fugaku.platformer.move.state;

import com.fugaku.platformer.entities.moveable.Player;
import com.fugaku.platformer.texture.Animation;

/**
 * @author Changyu Li
 * @date 2024-12-15
 **/
interface MoveStateHandler {
    void analyzeState(MoveData moveData);

    void updatePlayer(Player player, MoveState lastState, Animation animation);
}

// Add other states as needed...
