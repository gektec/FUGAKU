package com.example.platformerplain.entities;

import com.example.platformerplain.Constants;
import javafx.animation.Animation;
import javafx.scene.Node;

public abstract class Entity {

    protected abstract boolean isAnimated();

    public abstract Node node();

    public abstract Constants.EntityType getType();

//    public void update() {
//        if(isAnimated()) {
//            Animation.update();
//        }
//    }

}
