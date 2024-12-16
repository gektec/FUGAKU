package com.fugaku.platformer.entities.tile;

import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.entities.Entity;

import java.util.ArrayList;

/**
 *
 * @author Changyu Li
 * @date 2024-12-15
 **/
abstract class Tile extends Entity {
    //private final ArrayList<Entity> children = new ArrayList<>();

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
