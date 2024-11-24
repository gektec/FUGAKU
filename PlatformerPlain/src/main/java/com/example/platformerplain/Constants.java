package com.example.platformerplain;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }

    public static final int TILE_SIZE = 60;
    public static final int PLAYER_SIZE = 40;
    public static final int BACKGROUND_WIDTH = 1280;
    public static final int BACKGROUND_HEIGHT = 720;
    public static final int PLAYER_START_X = 0;
    public static final int PLAYER_START_Y = 600;

    public static final int MAX_SPEED = TILE_SIZE / 2;
    public static final int MAX_FALL_SPEED = 20;  // Maximum falling speed
    public static final int RESISTANCE = 2;  // Horizontal resistance

    public static final double DASH_DURATION = 0.2;
    public static final int DASH_SPEED = 36;
    public static final double SLIDE_JUMP_DURATION = 0.08;

    public static final int SLIDE_WALL_SPEED = 2;
    public static final int SLIDE_JUMP_SPEED = 15;

    public enum EntityType {
        PLATFORM,
        GOAL,
        PLAYER,
        ENEMY
    }

    private static final Map<Integer, int[]> adjacencyCodeToSprite;


    static {
        adjacencyCodeToSprite = new HashMap<>();
        // Populating the map with adjacencyCode as keys and sprite row/column as values
        // Example entries (these are illustrative and should be adjusted according to your actual sprite sheet)
        adjacencyCodeToSprite.put(0, new int[]{3, 3}); // No adjacency
        adjacencyCodeToSprite.put(1, new int[]{3, 2}); // Up
        adjacencyCodeToSprite.put(2, new int[]{0, 3}); // Right
        adjacencyCodeToSprite.put(3, new int[]{0, 2}); // Up + Right
        adjacencyCodeToSprite.put(4, new int[]{3, 0}); // Down
        adjacencyCodeToSprite.put(5, new int[]{3, 1}); // Up + Down
        adjacencyCodeToSprite.put(6, new int[]{0, 0}); // Right + Down
        adjacencyCodeToSprite.put(7, new int[]{0, 1}); // Up + Right + Down
        adjacencyCodeToSprite.put(8, new int[]{2, 3}); // Left
        adjacencyCodeToSprite.put(9, new int[]{2, 2}); // Up + Left
        adjacencyCodeToSprite.put(10, new int[]{1, 3}); // Right + Left
        adjacencyCodeToSprite.put(11, new int[]{1, 2}); // Up + Right + Left
        adjacencyCodeToSprite.put(12, new int[]{2, 0}); // Down + Left
        adjacencyCodeToSprite.put(13, new int[]{2, 1}); // Up + Down + Left
        adjacencyCodeToSprite.put(14, new int[]{1, 0}); // Right + Down + Left
        adjacencyCodeToSprite.put(15, new int[]{1, 1}); // All sides (Up + Right + Down + Left)

    }

    public static int[] getSpritePosition(int adjacencyCode) {
        return adjacencyCodeToSprite.get(adjacencyCode);
    }

}
