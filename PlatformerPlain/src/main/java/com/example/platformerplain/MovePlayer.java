package com.example.platformerplain;

import com.example.platformerplain.entities.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.platformerplain.Constants.MAX_FALL_SPEED;
import static com.example.platformerplain.Constants.RESISTANCE;

public class MovePlayer {
    private Entity player;
    private ArrayList<Entity> enemies;
    private ArrayList<Entity> entityMap; // List to store all platform and goal entities
    private final int gravity = 1;  // Gravity constant
    private boolean canJump;  // Flag indicating whether the player can jump
    private boolean canDash;
    private boolean isDashing;
    private boolean isSliding;
    private boolean slideJump;
    private boolean isSlideJumping;
    private int levelWidth;  // Width of the level
    private HashMap<KeyCode, Boolean> keys;  // Map to store the state of keyboard keys
    private Coord2D playerVelocity; // Current velocity of the player
    private Main mainApp;  // Reference to the main application class
    private Move move;
    private Stage primaryStage;
    private Timeline dashCooldownTimer;
    private Timeline slideJumpCooldownTimer;
    private boolean haveJKeyReleased;

    public MovePlayer(Entity player, ArrayList<Entity> platforms, ArrayList<Entity> enemies, int levelWidth, HashMap<KeyCode, Boolean> keys, Main main) {
        this.player = player;
        this.entityMap = platforms;
        this.levelWidth = levelWidth;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.canJump = true;
        this.enemies = enemies;
        this.mainApp = main;

        dashCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.DASH_DURATION), event -> isDashing = false));
        dashCooldownTimer.setCycleCount(1);


        slideJumpCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.SLIDE_JUMP_DURATION), event -> isSlideJumping = false));
        slideJumpCooldownTimer.setCycleCount(1);

    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public void update() {
        int x=0,y=0;

        if(!isPressed(KeyCode.J)) {
            haveJKeyReleased = true;
        }

        if(!isDashing) {
            if(!isSlideJumping) {
                if (isPressed(KeyCode.J) && haveJKeyReleased && canJump && !isSliding) {
                    playerVelocity.setY(-20);
                    canJump = false;
                    haveJKeyReleased = false;
                }
                if (isPressed(KeyCode.J) && haveJKeyReleased && isSliding) {
                    playerVelocity.setY(-10);
                    slideJumpCooldownTimer.playFromStart();
                    slideJump = true;
                    isSliding = false;
                    canJump = false;
                    isSlideJumping = true;
                    haveJKeyReleased = false;
                } else slideJump = false;
                if (isPressed(KeyCode.A) && playerVelocity.getX() >= -8) {
                    playerVelocity.add(-4, 0);  //max speed: -12
                }
                if (isPressed(KeyCode.D) && playerVelocity.getX() <= 8) {
                    playerVelocity.add(4, 0);
                }
            }
            playerVelocity.reduce(RESISTANCE, 0);
            if (playerVelocity.getY() < MAX_FALL_SPEED) {
                playerVelocity.add(0, gravity);
            }
        }
        else {
            if(playerVelocity.getX() != 0 && playerVelocity.getY() != 0)
                playerVelocity.reduce((int) RESISTANCE/2,(int) RESISTANCE/2);
            else
                playerVelocity.reduce(RESISTANCE,RESISTANCE);
        }

        if (canDash && !isDashing && isPressed(KeyCode.K)) {
            if(isPressed(KeyCode.A)) {
                x-=Constants.DASH_SPEED;
            }
            if(isPressed(KeyCode.D)) {
                x+=Constants.DASH_SPEED;
            }
            if(isPressed(KeyCode.W)) {
                y-=Constants.DASH_SPEED;
            }
            if(isPressed(KeyCode.S)) {
                y+=Constants.DASH_SPEED;
            }

            if(x!=0 && y!=0)
                playerVelocity.set((int) (x/1.6), (int) (y/1.6));
            else
                playerVelocity.set(x, y);
            canDash = false;
            isDashing = true;
            dashCooldownTimer.playFromStart();
        }

        MoveStatus moveStatus = new MoveStatus(canJump, canDash, isPressed(KeyCode.A), isPressed(KeyCode.D), isSliding, slideJump);
        Move.move(player, playerVelocity, moveStatus);

        canJump = moveStatus.canJump;
        canDash = moveStatus.canDash;
        isSliding = moveStatus.isSliding;

        checkGoalCollision();
        checkDie();
    }




    private void checkGoalCollision() {
        for (Entity entity : entityMap) {
            if (entity.getType() == Constants.EntityType.GOAL && player.node().getBoundsInParent().intersects(entity.node().getBoundsInParent())) {
                System.out.println("You win!");
                // When the player reaches the goal, switch back to the menu scene

            }
        }
    }

    private void checkDie() {
        boolean isPlayerDead = player.node().getTranslateY() > 720;

        for (Entity enemy : enemies) {
            if (player.node().getBoundsInParent().intersects(enemy.node().getBoundsInParent())) {
                isPlayerDead = true;
            }
        }

        if (isPlayerDead) {
            System.out.println("You lose!");
            Main.getInstance().stopGameLoop();  // Stop game loop on player death
            Main.getInstance().exitGame();
        }
    }
}

class MoveStatus {
    public boolean canJump;
    public boolean canDash;
    public boolean isAPressed;
    public boolean isDPressed;
    public boolean isSliding;
    public boolean slideJump;

    public MoveStatus(boolean canJump, boolean canDash, boolean isAPressed, boolean isDPressed, boolean isSliding, boolean slideJump) {
        this.canJump = canJump;
        this.canDash = canDash;
        this.isAPressed = isAPressed;
        this.isDPressed = isDPressed;
        this.isSliding = isSliding;
        this.slideJump = slideJump;
    }
}