package com.example.platformerplain.entities;

import com.example.platformerplain.Constants;
import javafx.scene.Node;

public class Spike extends Entity {
    protected boolean isAnimated(){
        return false;
    };

    public Spike() {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Node hitBox() {
        return null;
    }

    @Override
    public Node node() {
        return null;
    }

    @Override
    public Constants.EntityType getType() {
        return Constants.EntityType.SPIKE;
    }
}
