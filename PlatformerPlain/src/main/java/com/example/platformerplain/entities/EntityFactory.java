package com.example.platformerplain.entities;

import com.example.platformerplain.entities.moveable.Enemy;
import com.example.platformerplain.entities.moveable.Player;
import com.example.platformerplain.entities.tile.*;
import com.example.platformerplain.entities.tile.Coin;

/**
 * A factory class responsible for creating game entities.
 * This class provides a method to create different types of entities based on the specified type.
 *
 * @author Changyu Li
 * @date 2024/11/8
 */
public class EntityFactory {

    /**
     * Creates an entity of the specified type.
     *
     * @param type  The type of the entity to create (e.g., PLATFORM, GOAL, PLAYER, etc.).
     * @param x     The x-coordinate of the entity's position.
     * @param y     The y-coordinate of the entity's position.
     * @param w     The width of the entity.
     * @param h     The height of the entity.
     * @param index An additional parameter used for certain entity types (e.g., PLATFORM, SPIKE, LADDER).
     * @return The created entity of the specified type.
     * @throws IllegalArgumentException If the specified entity type is unknown.
     */
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
                return new Coin(x, y, w, h);
            default:
                throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }
}
