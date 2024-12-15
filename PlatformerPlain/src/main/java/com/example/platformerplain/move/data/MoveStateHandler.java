package com.example.platformerplain.move.data;

import com.example.platformerplain.move.Coord2D;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p>Change MoveState according to current state</p>
 * @date 2024-12-15 19:38
 **/
interface MoveStateHandler {
    void analyzeState(MoveData moveData);
}

// Add other states as needed...
