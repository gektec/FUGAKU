package com.example.platformerplain.entities;
import com.example.platformerplain.Constants;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Entity {
    private Node node;
    protected boolean isAnimated(){
        return true;
    }

    public Player(int x, int y, int w, int h) {
        node = new Rectangle(w, h, Color.BLUE);
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    @Override
    public Node hitBox() {
        return node;
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public Constants.EntityType getType() {
        return Constants.EntityType.PLAYER;
    }
}
