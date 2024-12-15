package com.example.platformerplain.entities;

import com.example.platformerplain.texture.Animation;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

public abstract class Entity {
    protected Node hitBox;
    protected Canvas canvas;

    protected abstract boolean isAnimated();

    public Node canvas(){
        return canvas;
    }

    public Node hitBox(){
        return hitBox;
    }

    public abstract EntityType getType();

    protected Animation animation;


    public Entity(){
        if(isAnimated()){
            animation = new Animation();
        }
    }

    public abstract void update();


    public abstract int[] size();


}
