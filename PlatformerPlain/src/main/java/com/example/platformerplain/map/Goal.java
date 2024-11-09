package com.example.platformerplain.map;
import com.example.platformerplain.Entity;

import com.example.platformerplain.EntityType;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Goal implements Entity {
    private Node node;

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
    public EntityType getType() {
        return EntityType.GOAL;
    }
}
