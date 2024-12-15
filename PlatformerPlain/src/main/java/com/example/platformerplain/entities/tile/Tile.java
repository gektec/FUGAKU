package com.example.platformerplain.entities.tile;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.Entity;

import java.util.ArrayList;

/**
 *
 * @author Changyu Li
 * @date 2024-12-15
 **/
abstract class Tile extends Entity {
    private ArrayList<Entity> children = new ArrayList<>();

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
