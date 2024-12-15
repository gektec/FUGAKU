package com.example.platformerplain.entities;

import com.example.platformerplain.entities.moveable.Enemy;
import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.entities.tile.*;
import com.example.platformerplain.entities.tile.Coin;

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
            case SPIKE:
                return new Spike(x, y, w, h, index);
            case LADDER:
                return new Ladder(x, y, w, h, index);
            case DECORATION:
                return new Decoration(x, y, w, h);
            case COIN:
                return new Coin(x, y, w, h, index);
            default:
                throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }
}
