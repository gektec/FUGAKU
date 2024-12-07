package com.example.platformerplain;

import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Constants {
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }

    public static final int TILE_SIZE = 60;
    public static final int PLAYER_SIZE = 40;
    public static final int PLAYER_HEIGHT = 60;
    public static final int PLAYER_WIDTH = 30;
    public static final int ENEMY_SIZE = 20;
    public static final int BACKGROUND_WIDTH = 1280;
    public static final int BACKGROUND_HEIGHT = 720;

    public static final int MAX_SPEED = TILE_SIZE / 2;
    public static final int MAX_FALL_SPEED = 20;  // Maximum falling speed
    public static final int RESISTANCE = 2;  // Horizontal resistance
    public static final int GRAVITY = 1;  // Gravity constant

    public static final double DASH_DURATION = 0.2;
    public static final int DASH_SPEED = 36;
    public static final double SLIDE_JUMP_DURATION = 0.08;

    public static final int SLIDE_WALL_SPEED = 2;
    public static final int SLIDE_JUMP_SPEED = 15;

    public enum EntityType {
        PLATFORM,
        GOAL,
        PLAYER,
        SPIKE, LADDER, ENEMY
    }

    public static final int SCALE_FACTOR = 5;

    public static final Image BACKGROUND_SKY = load("/images/backgrounds/Sky.png");
    public static final Image BACKGROUND_CLOUD_1 = load("/images/backgrounds/Cloud 1.png");
    public static final Image BACKGROUND_CLOUD_2 = load("/images/backgrounds/Cloud 2.png");
    public static final Image BACKGROUND_CLOUD_3 = load("/images/backgrounds/Cloud 3.png");
    public static final Image BACKGROUND_MOON = load("/images/backgrounds/Moon.png");

    public static final Image[][] GHOST_IDLE = load("/images/characters/ghost/Ghost_Idle.png", 48, 48, 5);
    public static final Image[][] GHOST_DEATH = load("/images/characters/ghost/Ghost_Death.png", 48, 48, 5);

    public static final Image[][] PLAYER_IDLE = load("/images/characters/player/Player_Idle.png", 96, 96, 3);
    public static final Image[][] PLAYER_RUN = load("/images/characters/player/Player_Run.png", 96, 96, 3);
    public static final Image[][] PLAYER_JUMP_START = load("/images/characters/player/Player_Jump_Start.png", 96, 96, 3);
    public static final Image[][] PLAYER_JUMP_FALL = load("/images/characters/player/Player_Jump_Fall.png", 96, 96, 3);


    public static Image[][] load(String s, int w, int h, int scale) {
        Image[][] ret;
        try {
            InputStream inputStream = Objects.requireNonNull(Constants.class.getResourceAsStream(s));
            Image spritesheet = new Image(inputStream);
            int width = (int) (spritesheet.getWidth() / w);
            int height = (int) (spritesheet.getHeight() / h);
            ret = new Image[height][width];

            PixelReader pixelReader = spritesheet.getPixelReader();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    ret[i][j] = ImageScaler.nearestNeighborScale( (new WritableImage(pixelReader, j * w, i * h, w, h)), scale);
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }

    //Overload
    public static Image load(String s) {
        try {
            InputStream inputStream = Objects.requireNonNull(Constants.class.getResourceAsStream(s));
            Image image = new Image(inputStream);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }



    private static final Map<Integer, int[]> adjacencyCodePlatform;
    private static final Map<Integer, int[]> adjacencyCodeSpike;

    static {
        adjacencyCodePlatform = new HashMap<>();
        // Populating the map with adjacencyCode as keys and sprite row/column as values
        // Example entries (these are illustrative and should be adjusted according to your actual sprite sheet)
        adjacencyCodePlatform.put(0, new int[]{3, 3}); // No adjacency
        adjacencyCodePlatform.put(1, new int[]{3, 2}); // Up
        adjacencyCodePlatform.put(2, new int[]{0, 3}); // Right
        adjacencyCodePlatform.put(3, new int[]{0, 2}); // Up + Right
        adjacencyCodePlatform.put(4, new int[]{3, 0}); // Down
        adjacencyCodePlatform.put(5, new int[]{3, 1}); // Up + Down
        adjacencyCodePlatform.put(6, new int[]{0, 0}); // Right + Down
        adjacencyCodePlatform.put(7, new int[]{0, 1}); // Up + Right + Down
        adjacencyCodePlatform.put(8, new int[]{2, 3}); // Left
        adjacencyCodePlatform.put(9, new int[]{2, 2}); // Up + Left
        adjacencyCodePlatform.put(10, new int[]{1, 3}); // Right + Left
        adjacencyCodePlatform.put(11, new int[]{1, 2}); // Up + Right + Left
        adjacencyCodePlatform.put(12, new int[]{2, 0}); // Down + Left
        adjacencyCodePlatform.put(13, new int[]{2, 1}); // Up + Down + Left
        adjacencyCodePlatform.put(14, new int[]{1, 0}); // Right + Down + Left
        adjacencyCodePlatform.put(15, new int[]{1, 1}); // All sides (Up + Right + Down + Left)


        adjacencyCodeSpike = new HashMap<>();
        adjacencyCodeSpike.put(1, new int[]{6, 1}); // Body is on top
        adjacencyCodeSpike.put(20, new int[]{6, 0}); // Peak is on bottom
        adjacencyCodeSpike.put(2, new int[]{4, 0}); // Body is on right
        adjacencyCodeSpike.put(24, new int[]{5, 0}); // Peak is on left
        adjacencyCodeSpike.put(4, new int[]{7, 0}); // Body is on bottom
        adjacencyCodeSpike.put(17, new int[]{7, 1}); // Peak is on top
        adjacencyCodeSpike.put(8, new int[]{6, 2}); // Body is on left
        adjacencyCodeSpike.put(18, new int[]{5, 1}); // Peak is on right
    }

    public static int[] getPlatformPosition(int adjacencyCode) {
        return adjacencyCodePlatform.get(adjacencyCode);
    }

    public static int[] getSpikePosition(int adjacencyCode) {
        return adjacencyCodeSpike.get(adjacencyCode);
    }

}
