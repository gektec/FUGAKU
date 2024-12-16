package com.example.platformerplain.entities.tile;

import com.example.platformerplain.data.Constants;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.texture.TextureMapping;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a ladder in the game that the player can use to climb.
 * The ladder's height may vary based on its position in the game.
 *
 * @author Changyu Li
 * @date 2024/11/26
 */
public class Ladder extends Tile {
    private final GraphicsContext gc;
    private final int index;

    /**
     * Constructs a Ladder instance with the specified position and dimensions.
     * The height of the ladder is lower when at the top.
     *
     * @param x     The x-coordinate of the ladder's position.
     * @param y     The y-coordinate of the ladder's position.
     * @param w     The width of the ladder's hitbox.
     * @param h     The height of the ladder's hitbox. Adjusted if index is 0.
     * @param index The index of the ladder, used to differentiate between multiple ladders.
     */
    public Ladder(int x, int y, int w, int h, int index) { //todo: remove useless parameters
        if(index == 0) {
            h = 20;  // Top of ladder is shorter
        }
        hitBox = new Rectangle(w, h, Color.PURPLE);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y + Constants.TILE_SIZE - h);

        canvas = new Canvas(Constants.TILE_SIZE, Constants.TILE_SIZE);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
        Image sprite = TextureMapping.getSprite(EntityType.LADDER, 0, index);
        sprite = ImageScaler.nearestNeighborScale(sprite, 5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());

        this.index = index;
    }

    /**
     * Returns the type of the entity, which is LADDER.
     *
     * @return The entity type of this ladder.
     */
    @Override
    public EntityType getType() {
        return EntityType.LADDER;
    }

    /**
     * Gets the index of the ladder, which can be used to detect the highest ladder.
     *
     * @return The index of the ladder.
     */
    public int getIndex() { // Used to detect the highest ladder
        return index;
    }
}
