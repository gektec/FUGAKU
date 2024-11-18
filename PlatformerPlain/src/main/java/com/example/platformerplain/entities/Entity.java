package com.example.platformerplain.entities;

import com.example.platformerplain.Constants;
import javafx.scene.Node;

public interface Entity {
    Node node();
    Constants.EntityType getType();
}
