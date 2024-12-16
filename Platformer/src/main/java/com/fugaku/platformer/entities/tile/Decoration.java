package com.fugaku.platformer.entities.tile;

import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.entities.EntityType;
import com.fugaku.platformer.texture.TextureMapping;
import com.fugaku.platformer.texture.ImageScaler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a decorative tile.
 * The decoration does not interact with gameplay.
 *
 * @author Changyu Li
 * @date 2024/12/10
 */
public class Decoration extends Tile {
    private final GraphicsContext gc;

    /**
     *
     * @param x The x-coordinate of the decoration's position.
     * @param y The y-coordinate of the decoration's position.
     * @param w The width of the decoration's hitbox.
     * @param h The height of the decoration's hitbox.
     */
    public Decoration(int x, int y, int w, int h) { // todo: remove useless parameters
        hitBox = new Rectangle(w, h, Color.DARKBLUE);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        canvas = new Canvas(Constants.TILE_SIZE, Constants.TILE_SIZE);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
        Image sprite = TextureMapping.getSprite(EntityType.DECORATION, (int) (Math.random() * 5), (int) (Math.random() * 7));
        sprite = ImageScaler.nearestNeighborScale(sprite, 5); // todo: move to assets
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Returns the type of the entity, which is DECORATION.
     *
     * @return The entity type of this decoration.
     */
    @Override
    public EntityType getType() {
        return EntityType.DECORATION;
    }
}
