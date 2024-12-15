package com.example.platformerplain.entities.tile;
import com.example.platformerplain.Constants;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityType;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Goal extends Tile {
    private Node node;

    public Goal(int x, int y, int w, int h) {
        node = new Rectangle(w, h, Color.GOLD);
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    @Override
    public Node hitBox() {
        return node;
    }

    @Override
    public Node canvas() {
        return node;
    }

    @Override
    public EntityType getType() {
        return EntityType.GOAL;
    }
}
