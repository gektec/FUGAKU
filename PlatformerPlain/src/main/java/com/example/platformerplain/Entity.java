package com.example.platformerplain;

import javafx.scene.Node;

public interface Entity {
    Node node();
    EntityType getType();
}
