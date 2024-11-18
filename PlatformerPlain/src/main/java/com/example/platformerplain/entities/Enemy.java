package com.example.platformerplain.entities;

import com.example.platformerplain.Constants;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy implements Entity {
    private Node node;

    public Enemy(int x, int y, int w, int h) {
        node = new Rectangle(w, h, Color.RED);
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public Constants.EntityType getType() {
        return Constants.EntityType.ENEMY;
    }
}