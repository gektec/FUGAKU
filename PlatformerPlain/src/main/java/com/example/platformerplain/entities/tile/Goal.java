package com.example.platformerplain.entities.tile;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a goal tile.
 * The goal acts as a target for the player to reach.
 *
 * @author Changyu Li
 * @date 2024/11/7
 */
public class Goal extends Tile {
    private GraphicsContext gc;
    private Image sprite;

    /**
     *
     * @param x The x-coordinate of the goal's position.
     * @param y The y-coordinate of the goal's position.
     * @param w The width of the goal's hitbox.
     * @param h The height of the goal's hitbox.
     */
    public Goal(int x, int y, int w, int h) {
        hitBox = new Rectangle(w, h, Color.GOLD);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        canvas = new Canvas(199, 240);
        gc = canvas.getGraphicsContext2D();
        Move.centerAlign(this,new Coord2D(10,-32));
        sprite = ImageScaler.nearestNeighborScale(Assets.GOAL,5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Returns the type of the entity, which is GOAL.
     *
     * @return The entity type of this goal.
     */
    @Override
    public EntityType getType() {
        return EntityType.GOAL;
    }
}
