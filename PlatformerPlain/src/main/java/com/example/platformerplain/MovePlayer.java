package com.example.platformerplain;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

public class MovePlayer {
    private Entity player;
    private ArrayList<Entity> entityMap; // List to store all platform and goal entities
    private final int gravity = 1;  // Gravity constant
    private boolean canJump;  // Flag indicating whether the player can jump
    private int levelWidth;  // Width of the level
    private HashMap<KeyCode, Boolean> keys;  // Map to store the state of keyboard keys
    private final int maxFallSpeed = 20;  // Maximum falling speed
    private final int resistance = 2;  // Horizontal resistance
    private Coord2D playerVelocity; // Current velocity of the player
    private Main mainApp;  // Reference to the main application class
    private Move move;

    public MovePlayer(Entity player, ArrayList<Entity> platforms, ArrayList<Entity> enemies, int levelWidth, HashMap<KeyCode, Boolean> keys) {
        this.player = player;
        this.entityMap = platforms;
        this.levelWidth = levelWidth;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.canJump = true;
        this.enemies = enemies;
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
            playerVelocity.reduce(resistance, 0);
        }
        if (playerVelocity.getY() < maxFallSpeed) {
            playerVelocity.add(0, gravity);
        }
        //Move.movePlayerX(player, playerVelocity);

        canJump = Move.move(player, playerVelocity);

        checkGoalCollision();
        checkDie();
    }




    private void checkGoalCollision() {
        for (Entity entity : entityMap) {
            if (entity.getType() == EntityType.GOAL && player.node().getBoundsInParent().intersects(entity.node().getBoundsInParent())) {
                System.out.println("You win!");
                // When the player reaches the goal, switch back to the menu scene
                mainApp.switchToMenu();
            }
        }
    }

    private void checkDie() {
        if (player.node().getTranslateY() > 720) {
            System.out.println("You lose!");
            // When the player falls off the screen, switch to the fail scene
            mainApp.switchToFail();
        }
        for(Entity enemy : enemies) {
            if (player.node().getBoundsInParent().intersects(enemy.node().getBoundsInParent())) {
                System.out.println("You lose!");
                System.exit(0);
            }
        }

    }
}