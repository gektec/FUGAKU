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
    private int levelWidth;  // Width of the level
    private HashMap<KeyCode, Boolean> keys;  // Map to store the state of keyboard keys
    private Coord2D playerVelocity; // Current velocity of the player
    private Main mainApp;  // Reference to the main application class
    private Move move;
    private Stage primaryStage;
    private Timeline dashCooldownTimer;

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
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public void update() {
        int x=0,y=0;
        if(!isDashing){
            if (isPressed(KeyCode.J) && canJump) {
                playerVelocity.add(0, -20);
                canJump = false;
            }
            if (isPressed(KeyCode.A) && playerVelocity.getX() >= -10) {
                playerVelocity.add(-5, 0);  //max speed: -15
            }
            if (isPressed(KeyCode.D) && playerVelocity.getX() <= 10) {
                playerVelocity.add(5, 0);
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

        if (canDash &&! isDashing && isPressed(KeyCode.K)) {
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

        MoveStatus moveStatus = new MoveStatus(canJump, canDash);
        Move.move(player, playerVelocity, moveStatus);

        canJump = moveStatus.canJump;
        canDash = moveStatus.canDash;

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

    public MoveStatus(boolean canJump, boolean canDash) {
        this.canJump = canJump;
        this.canDash = canDash;
    }
}