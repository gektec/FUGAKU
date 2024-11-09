package com.example.platformerplain.map;
import com.example.platformerplain.Entity;

import com.example.platformerplain.EntityType;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform implements Entity {
    private Node node;

    public Platform(int x, int y, int w, int h) { //todo: remove useless parameters & use Pane instead of Rectangle
        node = new Rectangle(w, h, Color.GREEN);
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public EntityType getType() {
        return EntityType.PLATFORM;
    }
}