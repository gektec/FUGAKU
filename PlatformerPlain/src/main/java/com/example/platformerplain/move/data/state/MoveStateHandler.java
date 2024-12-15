package com.example.platformerplain.move.data.state;

import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.data.MoveData;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p>Change MoveState according to current state</p>
 * @date 2024-12-15 19:38
 **/
interface MoveStateHandler {
    void handle(Coord2D velocity, MoveData moveData);
}

// Add other states as needed...
