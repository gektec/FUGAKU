package com.example.platformerplain.data;

/**
 * Constants class to store all the constants used in the game
 * @author Changyu Li
 * @date 2024/11/18
 */
public class Constants {
    Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }

    public static final int TILE_SIZE = 60;
    public static final int COIN_SIZE = 30;
    public static final int SPIKE_SIZE = 56;
    public static final int PLAYER_SIZE = 30;
    public static final int PLAYER_HEIGHT = 30;
    public static final int PLAYER_WIDTH = 30;
    public static final int ENEMY_SIZE = 20;
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    public static final int MAX_SPEED = TILE_SIZE / 2;
    public static final int MAX_MOVE_SPEED = 10;
    public static final int MAX_FALL_SPEED = 20;  // Maximum falling speed
    public static final int MAX_CLIMB_SPEED = 6;
    public static final int RESISTANCE = 2;  // Horizontal resistance
    public static final int GRAVITY = 1;  // Gravity constant

    public static final double DASH_DURATION = 0.25;
    public static final int DASH_SPEED = 30;
    public static final double SLIDE_JUMP_DURATION = 0.15;

    public static final int SLIDE_WALL_SPEED = 2;
    public static final int SLIDE_JUMP_SPEED = 20;


}
