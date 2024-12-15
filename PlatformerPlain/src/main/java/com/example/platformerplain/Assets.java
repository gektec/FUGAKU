package com.example.platformerplain;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.text.Font;

/**
 * This class is responsible for storing all game assets.
 * @author Changyu Li
 * @date 2024/12/8
 */
public class Assets {

    // Backgrounds
    public static final Background MENU_BACKGROUND = new Background( new BackgroundImage(AssetManager.loadImage("/images/backgroundImage/Background.png", 5),null,null,null,null));
    public static final Background LEVEL_COMPLETE_BACKGROUND = new Background( new BackgroundImage(AssetManager.loadImage("/images/backgroundImage/Completed.png", 5),null,null,null,null));
    public static final Background LEVEL_FAILED_BACKGROUND = new Background( new BackgroundImage(AssetManager.loadImage("/images/backgroundImage/Gameover.png", 5),null,null,null,null));
    public static final Background LEVEL_TRANSITION_BACKGROUND = new Background( new BackgroundImage(AssetManager.loadImage("/images/backgroundImage/Transition.png", 5),null,null,null,null));

    // Textures
    public static final Image BACKGROUND_SKY = AssetManager.loadImage("/images/backgroundTexture/Sky.png");
    public static final Image BACKGROUND_CLOUD_1 = AssetManager.loadImage("/images/backgroundTexture/Cloud 1.png");
    public static final Image BACKGROUND_CLOUD_2 = AssetManager.loadImage("/images/backgroundTexture/Cloud 2.png");
    public static final Image BACKGROUND_CLOUD_3 = AssetManager.loadImage("/images/backgroundTexture/Cloud 3.png");
    public static final Image BACKGROUND_MOON = AssetManager.loadImage("/images/backgroundTexture/Moon.png");
    public static final Image DECORATION = AssetManager.loadImage("/images/object/Props.png");
    public static final Image[][] GHOST_IDLE = AssetManager.loadImage("/images/character/ghost/Ghost_Idle.png", 48, 48, 5);
    public static final Image[][] GHOST_DEATH = AssetManager.loadImage("/images/character/ghost/Ghost_Death.png", 48, 48, 5);
    public static final Image[][] PLAYER_IDLE = AssetManager.loadImage("/images/character/player/Player_Idle.png", 96, 96, 3);
    public static final Image[][] PLAYER_RUN = AssetManager.loadImage("/images/character/player/Player_Run.png", 96, 96, 3);
    public static final Image[][] PLAYER_DASH = AssetManager.loadImage("/images/character/player/Player_Dash.png", 96, 96, 3);
    public static final Image[][] PLAYER_JUMP_START = AssetManager.loadImage("/images/character/player/Player_Jump_Start.png", 96, 96, 3);
    public static final Image[][] PLAYER_JUMP_FALL = AssetManager.loadImage("/images/character/player/Player_Jump_Fall.png", 96, 96, 3);
    public static final Image[][] PLAYER_SLIDING = AssetManager.loadImage("/images/character/player/Player_Wall_Slide.png", 96, 96, 3);

    // Sounds
    public static final AssetManager.GameMediaPlayer COMPLETE_SOUND = new AssetManager.GameMediaPlayer("/sound/completed.mp3");
    public static final AssetManager.GameMediaPlayer VICTORY_SOUND = new AssetManager.GameMediaPlayer("/sound/victory.mp3");
    public static final AssetManager.GameMediaPlayer FAIL_SOUND = new AssetManager.GameMediaPlayer("/sound/defeat.mp3");
    public static final AssetManager.GameMediaPlayer BACKGROUND_MUSIC = new AssetManager.GameMediaPlayer("/sound/victory.mp3"); // todo: replace
    public static final AssetManager.GameMediaPlayer JUMP_SFX = new AssetManager.GameMediaPlayer("/sound/Jump.wav");
    public static final AssetManager.GameMediaPlayer DASH_SFX = new AssetManager.GameMediaPlayer("/sound/Dash.wav");

    // Fonts
    public static final Font baseFont = Font.loadFont(Assets.class.getResourceAsStream("/m6x11plus.ttf"), 20);
}
