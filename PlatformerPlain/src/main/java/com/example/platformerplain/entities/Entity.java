package com.example.platformerplain.entities;

import com.example.platformerplain.texture.Animation;
import javafx.scene.Node;

public abstract class Entity {

    protected abstract boolean isAnimated();

    public abstract Node canvas();

    public abstract Node hitBox();

    public abstract EntityType getType();

    protected Animation animation;


    public Entity(){
        if(isAnimated()){
            animation = new Animation();
        }
    }

    public void update() {
    }


    public abstract int[] size();



}
