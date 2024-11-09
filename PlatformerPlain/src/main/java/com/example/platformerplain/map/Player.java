package com.example.platformerplain.map;
import com.example.platformerplain.Entity;

import com.example.platformerplain.EntityType;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player implements Entity {
    private Node node;

    public Player(int x, int y, int w, int h) {
        node = new Rectangle(w, h, Color.BLUE);
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public EntityType getType() {
        return EntityType.PLAYER;
    }
}
