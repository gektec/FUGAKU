package com.example.platformerplain.entities.tile;

import com.example.platformerplain.data.AssetManager;
import com.example.platformerplain.data.Constants;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.texture.TextureMapping;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a spike tile that can harm the player.
 * The spike has a graphical representation and can change appearance
 * when the player dies.
 *
 * @author Changyu Li
 * @date 2024/12/4
 */
public class Spike extends Tile {
    private GraphicsContext gc;
    private int index;

    /**
     *
     * @param x     The x-coordinate of the spike's position.
     * @param y     The y-coordinate of the spike's position.
     * @param w     The width of the spike's hitbox.
     * @param h     The height of the spike's hitbox.
     * @param index The index used to determine the sprite's appearance.
     */
    public Spike(int x, int y, int w, int h, int index) {
        this.index = index;
        hitBox = new Rectangle(w, h, Color.RED);
        hitBox.setTranslateX(x - (w - Constants.TILE_SIZE) / 2);
        hitBox.setTranslateY(y - (h - Constants.TILE_SIZE) / 2);
        canvas = new Canvas(Constants.TILE_SIZE, Constants.TILE_SIZE);

        if (index >= 16) {
            Move.centerAlign(this, new Coord2D(0, 0));
        } else {
            canvas.setTranslateX(x);
            canvas.setTranslateY(y);
            this.hitBox = new Rectangle(-9999, -9999, 0, 0);
        }

        gc = canvas.getGraphicsContext2D();
        Image sprite = TextureMapping.getSprite(EntityType.SPIKE, AssetManager.getSpikePosition(index)[0], AssetManager.getSpikePosition(index)[1]);
        sprite = ImageScaler.nearestNeighborScale(sprite, 5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Updates the spike's appearance when the player dies.
     * This method draws a different sprite for the spike.
     */
    public void playerDead() {
        Image sprite = TextureMapping.getSprite(EntityType.SPIKE, AssetManager.getSpikePosition(index)[0] - 4, AssetManager.getSpikePosition(index)[1]);
        sprite = ImageScaler.nearestNeighborScale(sprite, 5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Updates the spike's state.
     */
    @Override
    public void update() {
        // todo
    }

    /**
     * Returns the size of the spike.
     *
     * @return An array containing the width and height of the spike.
     */
    @Override
    public int[] size() {
        return new int[]{Constants.SPIKE_SIZE, Constants.SPIKE_SIZE};
    }

    /**
     * Returns the type of the entity, which is SPIKE.
     *
     * @return The entity type of this spike.
     */
    @Override
    public EntityType getType() {
        return EntityType.SPIKE;
    }
}
