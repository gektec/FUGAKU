package com.fugaku.platformer.texture;

import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.entities.EntityType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * mapping entity types to their respective sprites
 * @author Changyu Li
 * @date 2024/12/11
 */
public class TextureMapping {
    private static final int spriteWidth = 16; // Width of each sprite in pixels
    private static final int spriteHeight = 16; // Height of each sprite in pixels
    private static Image spriteSheet; // The current sprite sheet image

    /**
     * Retrieves a sprite from the sprite sheet based on the entity type, row, and column.
     *
     * @param type The type of the entity (e.g., PLATFORM, LADDER, SPIKE, DECORATION).
     * @param row The row index of the sprite in the sprite sheet.
     * @param col The column index of the sprite in the sprite sheet.
     * @return A WritableImage representing the sprite corresponding to the specified entity type, row, and column.
     * @throws IllegalArgumentException If the entity type is unknown.
     */
    public static Image getSprite(EntityType type, int row, int col) {
        if (type == EntityType.PLATFORM)  spriteSheet = Assets.PLATFORM;
        else if (type == EntityType.LADDER) spriteSheet = Assets.LADDER;
        else if (type == EntityType.SPIKE) spriteSheet = Assets.SPIKE;
        else if (type == EntityType.DECORATION) spriteSheet = Assets.DECORATION;
        else throw new IllegalArgumentException("Unknown entity type: " + type);
        PixelReader reader = spriteSheet.getPixelReader();
        WritableImage sprite = new WritableImage(reader, row * spriteWidth, col * spriteHeight, spriteWidth, spriteHeight);
        return sprite;
    }
}
