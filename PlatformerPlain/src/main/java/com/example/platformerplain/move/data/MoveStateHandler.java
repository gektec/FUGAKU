package com.example.platformerplain.move.data;

import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.texture.Animation;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p>Change MoveState according to current state</p>
 * @date 2024-12-15 19:38
 **/
interface MoveStateHandler {
    void analyzeState(MoveData moveData);

    void updatePlayer(Player player, MoveState lastState, Animation animation);
}

// Add other states as needed...
