package com.fugaku.platformer.entities;

import com.fugaku.platformer.texture.Animation;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

/**
 * Abstract class representing a game entity.
 * This class serves as a base for all game entities, providing common properties and methods.
 * Entities can have a visual representation (canvas) and a hitbox for collision detection.
 *
 * @author Changyu Li
 * @date 2024/11/7
 */
public abstract class Entity {
    protected Node hitBox;
    protected Canvas canvas;
    protected Animation animation;

    /**
     * Constructor for the Entity class.
     * Initializes the animation if the entity is animated.
     */
    public Entity() {
        if(isAnimated()) {
            animation = new Animation();
        }
    }

    /**
     * Checks if the entity is animated.
     *
     * @return true if the entity has animations; false otherwise.
     */
    protected abstract boolean isAnimated();

    /**
     * Returns the canvas associated with this entity.
     *
     * @return The canvas node used for rendering the entity.
     */
    public Node canvas() {
        return canvas;
    }

    /**
     * Returns the hitbox of the entity.
     *
     * @return The Node representing the hitbox for collision detection.
     */
    public Node hitBox() {
        return hitBox;
    }

    /**
     * Returns the type of the entity.
     *
     * @return The EntityType enumeration value representing the type of this entity.
     */
    public abstract EntityType getType();

    /**
     * Updates the entity's state.
     * This method should be implemented to define how the entity behaves over time.
     */
    public abstract void update();

    /**
     * Returns the size of the entity.
     *
     * @return An array containing the width and height of the entity.
     */
    public abstract int[] size();
}
