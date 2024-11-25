package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.Main;
import com.example.platformerplain.entities.Enemy;
import com.example.platformerplain.entities.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.platformerplain.Constants.MAX_FALL_SPEED;
import static com.example.platformerplain.Constants.RESISTANCE;

public class MovePlayer {
    private Entity player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Entity> entityMap; // List to store all platform and goal entities
    private final int gravity = 1;  // Gravity constant
    private boolean canJump;  // Flag indicating whether the player can jump
    private boolean canDash;
    private boolean canSlideJump;
    private MoveState playerState;

    //    private int levelWidth;  // Width of the level
    private HashMap<KeyCode, Boolean> keys;  // Map to store the state of keyboard keys
    private Coord2D playerVelocity; // Current velocity of the player
    //    private Main mainApp;  // Reference to the main application class
//    private Move move;
//    private Stage primaryStage;
    private Timeline dashCooldownTimer;
    private Timeline slideJumpCooldownTimer;
    private boolean haveJKeyReleased = true;
    private boolean isPlayerDead = false;

    public MovePlayer(Entity player, ArrayList<Entity> platforms, ArrayList<Enemy> enemies, int levelWidth, HashMap<KeyCode, Boolean> keys, Main main) {
        this.player = player;
        this.entityMap = platforms;
        //this.levelWidth = levelWidth;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.canJump = true;
        this.enemies = enemies;
        //this.mainApp = main;
        this.playerState = MoveState.DEFAULT;


        dashCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.DASH_DURATION), event -> playerState = MoveState.DEFAULT));
        dashCooldownTimer.setCycleCount(1);


        slideJumpCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.SLIDE_JUMP_DURATION), event -> playerState = MoveState.DEFAULT));
        slideJumpCooldownTimer.setCycleCount(1);

    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public void update() {
        int x = 0, y = 0;

        if (!isPressed(KeyCode.J)) {
            haveJKeyReleased = true;
        }
        if (!canJump && !canSlideJump) {
            haveJKeyReleased = false;
        }
        if (playerState == MoveState.DASHING || playerState == MoveState.SLIDE_JUMPING) {
            if (playerVelocity.getX() != 0 && playerVelocity.getY() != 0)
                playerVelocity.reduce((int) RESISTANCE / 2, (int) RESISTANCE / 2);
            else
                playerVelocity.reduce(RESISTANCE, RESISTANCE);
        } else {
            // Jump
            if (isPressed(KeyCode.J) && haveJKeyReleased && canJump && playerState == MoveState.DEFAULT) {
                playerVelocity.setY(-20);
                canJump = false;
                haveJKeyReleased = false;
            }
            // Slide jump
            else if (isPressed(KeyCode.J) && haveJKeyReleased && canSlideJump) {
                playerVelocity.setY(-10);
                canJump = false;
                haveJKeyReleased = false;
                playerState = MoveState.SLIDE_JUMPING;
                slideJumpCooldownTimer.playFromStart();
            }
            // Move left and right
            if (isPressed(KeyCode.A) && playerVelocity.getX() >= -8) {
                playerVelocity.add(-4, 0);  //max speed: -12
            }
            if (isPressed(KeyCode.D) && playerVelocity.getX() <= 8) {
                playerVelocity.add(4, 0);
            }

            // Dash
            if (canDash && isPressed(KeyCode.K)) {
                if (isPressed(KeyCode.A)) {
                    x -= Constants.DASH_SPEED;
                }
                if (isPressed(KeyCode.D)) {
                    x += Constants.DASH_SPEED;
                }
                if (isPressed(KeyCode.W)) {
                    y -= Constants.DASH_SPEED;
                }
                if (isPressed(KeyCode.S)) {
                    y += Constants.DASH_SPEED;
                }
                if (x != 0 || y != 0) {
                    if (x != 0 && y != 0)
                        playerVelocity.set((int) (x / 1.6), (int) (y / 1.6));
                    else
                        playerVelocity.set(x, y);
                    canDash = false;
                    playerState = MoveState.DASHING;
                    dashCooldownTimer.playFromStart();
                }
            }
            //Resistance
            playerVelocity.reduce(RESISTANCE, 0);
        }
        // apply gravity when not dashing
        if (playerVelocity.getY() < MAX_FALL_SPEED) {
            playerVelocity.add(0, gravity);
        }


        MoveStatus moveStatus = new MoveStatus(playerState, canJump, canDash, canSlideJump);
        Move.move(player, playerVelocity, moveStatus);

        playerState = moveStatus.moveState;
        canJump = moveStatus.canJump;
        canDash = moveStatus.canDash;
        canSlideJump = moveStatus.canSlideJump;

        checkGoalCollision();

        checkEnemy();

        checkFall();

        checkDie();
    }


    private void checkGoalCollision() {
        for (Entity entity : entityMap) {
            if (entity.getType() == Constants.EntityType.GOAL && player.hitBox().getBoundsInParent().intersects(entity.hitBox().getBoundsInParent())) {
                System.out.println("You win!");
                Main.getInstance().transitionToLevel2();
                return;
            }
        }
    }

    private void checkFall() {
        isPlayerDead = player.hitBox().getTranslateY() > Constants.TILE_SIZE * LevelData.Levels[Main.getInstance().getLevel()].length + 50;
    }

    private void checkEnemy() {

        for (Enemy enemy : enemies) {
            if (player.hitBox().getBoundsInParent().intersects(enemy.hitBox().getBoundsInParent())) {
                if (player.hitBox().getTranslateY() + player.hitBox().getBoundsInParent().getHeight() <= enemy.hitBox().getTranslateY() + 10) {
                    ((Enemy) enemy).isDead = true;
                    playerVelocity.add(0, -20);
                    //test
                    System.out.println("enemy killed");
                } else isPlayerDead = true;
            }
        }
    }

    private void checkDie() {
        if (isPlayerDead) {
            System.out.println("You lose!");
            Main.getInstance().stopGameLoop();  // Stop game loop on player death
            Main.getInstance().exitGame();
        }
    }
}


