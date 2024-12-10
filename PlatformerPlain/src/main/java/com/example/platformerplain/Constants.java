package com.example.platformerplain;

public class Constants {
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }

    public static final int TILE_SIZE = 60;
    public static final int PLAYER_SIZE = 30;
    public static final int PLAYER_HEIGHT = 30;
    public static final int PLAYER_WIDTH = 30;
    public static final int ENEMY_SIZE = 20;
    public static final int BACKGROUND_WIDTH = 1280;
    public static final int BACKGROUND_HEIGHT = 720;

    public static final int MAX_SPEED = TILE_SIZE / 2;
    public static final int MAX_MOVE_SPEED = 10;
    public static final int MAX_FALL_SPEED = 20;  // Maximum falling speed
    public static final int RESISTANCE = 2;  // Horizontal resistance
    public static final int GRAVITY = 1;  // Gravity constant

    public static final double DASH_DURATION = 0.25;
    public static final int DASH_SPEED = 30;
    public static final double SLIDE_JUMP_DURATION = 0.08;

    public static final int SLIDE_WALL_SPEED = 2;
    public static final int SLIDE_JUMP_SPEED = 15;


}
