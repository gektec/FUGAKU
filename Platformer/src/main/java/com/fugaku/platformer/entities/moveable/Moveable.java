package com.fugaku.platformer.entities.moveable;

import com.fugaku.platformer.entities.Entity;

/**
 *
 * @author Changyu Li
 * @date 2024-12-15
 **/
abstract class Moveable extends Entity {
//    private final ArrayList<Entity> children = new ArrayList<>();


//    public void add(Entity child) {
//        children.add(child);
//    }

    @Override
    protected boolean isAnimated() {
        return true;
    }


}
