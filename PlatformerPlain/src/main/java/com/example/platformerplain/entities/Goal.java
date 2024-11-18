package com.example.platformerplain.entities;
import com.example.platformerplain.Constants;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Goal extends Entity {
    private Node node;

    protected boolean isAnimated(){
        return false;
    };

    public Goal(int x, int y, int w, int h) {
        node = new Rectangle(w, h, Color.GOLD);
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public Constants.EntityType getType() {
        return Constants.EntityType.GOAL;
    }
}
