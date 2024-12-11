package com.example.platformerplain.texture;

import com.example.platformerplain.Assets;
import com.example.platformerplain.entities.EntityType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.Objects;

public class CutSpriteSheet {
    private static final int spriteWidth = 16;
    private static final int spriteHeight = 16;
    private static final int columns = 11;
    //private static final int backgroundWidth = 112;
    private static Image spriteSheet;
    //private static final Map<Integer, Image> spriteCache = new HashMap<>();


    public static Image getSprite(EntityType type, int row, int col) {
        if (type == EntityType.PLATFORM)  spriteSheet = new Image(Objects.requireNonNull(CutSpriteSheet.class.getResourceAsStream("/images/tile/Ground_Tiles.png")));
        else if (type == EntityType.LADDER) spriteSheet = new Image(Objects.requireNonNull(CutSpriteSheet.class.getResourceAsStream("/images/tile/Ladder.png")));
        else if (type == EntityType.SPIKE) spriteSheet = new Image(Objects.requireNonNull(CutSpriteSheet.class.getResourceAsStream("/images/tile/Spikes.png")));
        else if (type == EntityType.DECORATION) spriteSheet = Assets.DECORATION;
        else throw new IllegalArgumentException("Unknown entity type: " + type);
        PixelReader reader = spriteSheet.getPixelReader();
        WritableImage sprite = new WritableImage(reader, row * spriteWidth, col * spriteHeight, spriteWidth, spriteHeight);
        return sprite;
    }

    // New method to get sprite by index with caching
//    public static Image getSpriteByIndex(int index) {
//        if (!spriteCache.containsKey(index)) {
//            int row = index / columns;
//            int col = index % columns;
//            Image sprite = getSprite(col,row);
//            spriteCache.put(index, sprite);
//        }
//        return spriteCache.get(index);
//    }
}