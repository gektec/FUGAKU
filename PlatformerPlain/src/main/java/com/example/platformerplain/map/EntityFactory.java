package com.example.platformerplain.map;

import com.example.platformerplain.Entity;
import com.example.platformerplain.EntityType;
import javafx.scene.paint.Color;

public class EntityFactory {

    public static Entity createEntity(EntityType type, int x, int y, int w, int h, int index) {
        switch (type) {
            case PLATFORM:
                return new Platform(x, y, w, h, index);
            case GOAL:
                return new Goal(x, y, w, h);
            case PLAYER:
                return new Player(x, y, w, h);
            case ENEMY:
                return new Enemy(x, y, w, h);
            default:
                throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }
}
