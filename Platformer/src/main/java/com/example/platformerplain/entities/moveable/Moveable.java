package com.example.platformerplain.entities.moveable;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityType;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

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
