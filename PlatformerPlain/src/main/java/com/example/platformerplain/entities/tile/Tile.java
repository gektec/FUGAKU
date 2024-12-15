package com.example.platformerplain.entities.tile;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.Entity;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p>Composite node</p>
 * @date 2024-12-15 18:56
 **/
abstract class Tile extends Entity {
    @Override
    protected boolean isAnimated() {
        return false;
    }

    @Override
    public void update() {
    }

    @Override
    public int[] size() {
        return new int[]{Constants.TILE_SIZE, Constants.TILE_SIZE};
    }
}
