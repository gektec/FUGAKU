package com.example.platformerplain.entities;

import com.example.platformerplain.Constants;
import com.example.platformerplain.texture.Animation;
import javafx.scene.Node;

public abstract class Entity {

    protected abstract boolean isAnimated();

    public abstract Node node();

    public abstract Constants.EntityType getType();

    protected Animation animation;


    public Entity(){
        if(isAnimated()){
            animation = new Animation();
        }
    }

    public void update() {
        animation.update();
    }

    public void draw() {

    }


    public Node hitBox() {
        return this.hitBox();
    }

}
