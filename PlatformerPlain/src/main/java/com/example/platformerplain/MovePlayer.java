package com.example.platformerplain;

import com.example.platformerplain.entities.Entity;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

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
    private int levelWidth;  // Width of the level
    private HashMap<KeyCode, Boolean> keys;  // Map to store the state of keyboard keys
    private Coord2D playerVelocity; // Current velocity of the player
    private Main mainApp;  // Reference to the main application class
    private Move move;
    private Stage primaryStage;

    public MovePlayer(Entity player, ArrayList<Entity> platforms, ArrayList<Entity> enemies, int levelWidth, HashMap<KeyCode, Boolean> keys, Main main) {
        this.player = player;
        this.entityMap = platforms;
        this.levelWidth = levelWidth;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.canJump = true;
        this.enemies = enemies;
        this.mainApp = main;
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public void update() {
        if (isPressed(KeyCode.W) && canJump) {
            playerVelocity.add(0, -20);
        }
        if (isPressed(KeyCode.A) && playerVelocity.getX() >= -10) {
            playerVelocity.add(-5, 0);  //max speed: -15
        }
        else if (isPressed(KeyCode.D) && playerVelocity.getX() <= 10) {
            playerVelocity.add(5, 0);
        }
        else {
            playerVelocity.reduce(RESISTANCE, 0);
        }
        if (playerVelocity.getY() < MAX_FALL_SPEED) {
            playerVelocity.add(0, gravity);
        }
        //Move.movePlayerX(player, playerVelocity);

        canJump = Move.move(player, playerVelocity);

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