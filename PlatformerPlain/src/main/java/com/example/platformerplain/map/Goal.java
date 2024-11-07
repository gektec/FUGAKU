package com.example.platformerplain.map;
import com.example.platformerplain.Entity;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Goal implements Entity {
    private Node node;

    public Goal(int x, int y, int w, int h, Color color) {
        node = new Rectangle(w, h, color);
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    @Override
    public Node getNode() {
        return node;
    }
}
