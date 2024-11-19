package com.example.platformerplain;

public class Constants {

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
    public static final double SLIDE_JUMP_DURATION = 0.1;

    public static final int SLIDE_WALL_SPEED = 2;
    public static final int SLIDE_JUMP_SPEED = 15;

    public enum EntityType {
        PLATFORM,
        GOAL,
        PLAYER,
        ENEMY
    }



    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }

}
