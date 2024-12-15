package com.example.platformerplain.entities.tile;

import com.example.platformerplain.data.AssetManager;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.texture.TextureMapping;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a platform in the game that can be used by the player to jump on or walk across.
 * The platform can have different textures, depending on its type.
 *
 * @author Changyu Li
 * @date 2024/11/7
 */
public class Platform extends Tile {
    private GraphicsContext gc;
    private Image sprite;


    /**
     * @param x     The x-coordinate of the platform's position.
     * @param y     The y-coordinate of the platform's position.
     * @param w     The width of the platform.
     * @param h     The height of the platform.
     * @param index The index used to retrieve the platform's texture from the AssetManager.
     */
    public Platform(int x, int y, int w, int h, int index) {
        hitBox = new Rectangle(w, h, Color.GREEN);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        canvas = new Canvas(w, h);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(hitBox.getTranslateX());
        canvas.setTranslateY(hitBox.getTranslateY());

        if (GameModel.getCurrentLevel() == 3) {
            sprite = TextureMapping.getSprite(EntityType.PLATFORM, AssetManager.getPlatformPosition(index)[0], AssetManager.getPlatformPosition(index)[1] + 5);
        } else {
            sprite = TextureMapping.getSprite(EntityType.PLATFORM, AssetManager.getPlatformPosition(index)[0], AssetManager.getPlatformPosition(index)[1]);
        }
        sprite = ImageScaler.nearestNeighborScale(sprite, 5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Returns the type of the entity, which is PLATFORM.
     *
     * @return The entity type of this platform.
     */
    @Override
    public EntityType getType() {
        return EntityType.PLATFORM;
    }
}
