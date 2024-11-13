package com.example.platformerplain.texture;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CutSpriteSheet {
    private static final int spriteWidth = 16;
    private static final int spriteHeight = 16;
    private static final int columns = 10;
    private static final int backgroundWidth = 112;
    private static Image spriteSheet;
    private static final Map<Integer, Image> spriteCache = new HashMap<>();

    static {
        spriteSheet = new Image(Objects.requireNonNull(CutSpriteSheet.class.getResourceAsStream("/images/sheet.png")));
    }

    public static Image getSprite(int row, int col) {
        PixelReader reader = spriteSheet.getPixelReader();
        WritableImage sprite = new WritableImage(reader, col * spriteWidth + backgroundWidth, row * spriteHeight, spriteWidth, spriteHeight);
        return sprite;
    }

    // New method to get sprite by index with caching
    public static Image getSpriteByIndex(int index) {
        if (!spriteCache.containsKey(index)) {
            int row = index / columns;
            int col = index % columns;
            Image sprite = getSprite(row, col);
            spriteCache.put(index, sprite);
        }
        return spriteCache.get(index);
    }
}