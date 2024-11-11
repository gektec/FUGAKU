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

    /**
     * Constructor to initialize the player movement logic class
     * @param player The player entity
     * @param platforms List of platform and goal entities
     * @param levelWidth Width of the level
     * @param keys Map to store the state of keyboard keys
     * @param mainApp The main application class instance
     */
    public MovePlayer(Entity player, ArrayList<Entity> platforms, int levelWidth, HashMap<KeyCode, Boolean> keys, Main mainApp) {
        this.player = player;
        this.entityMap = platforms;
        this.levelWidth = levelWidth;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.canJump = true;
        this.mainApp = mainApp;
    }

    /**
     * Check whether a specific key is pressed
     * @param key The key to check
     * @return Returns true if the key is pressed, otherwise returns false
     */
    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    /**
     * Update the player's position and velocity
     */
    public void update() {
        if (isPressed(KeyCode.W) && canJump) {
            // If the W key is pressed and the player can jump, give the player an initial upward velocity
            playerVelocity.add(0, -20);
        }
        if (isPressed(KeyCode.A) && playerVelocity.getX() >= -10) {
            // If the A key is pressed and the current velocity is less than or equal to -10, decrease the horizontal velocity
            playerVelocity.add(-5, 0);
        } else if (isPressed(KeyCode.D) && playerVelocity.getX() <= 10) {
            // If the D key is pressed and the current velocity is less than or equal to 10, increase the horizontal velocity
            playerVelocity.add(5, 0);
        } else {
            // If there is no horizontal movement input, reduce the velocity based on resistance
            playerVelocity.reduce(resistance, 0);
        }
        if (playerVelocity.getY() < maxFallSpeed) {
            // Apply gravity
            playerVelocity.add(0, gravity);
        }

        // Move the player and obtain whether the player can jump
        canJump = Move.movePlayer(player, playerVelocity);

        // Check if the player collides with the goal
        checkGoalCollision();

        // Check if the player dies (falls off the screen)
        checkDie();
    }

    /**
     * Check if the player collides with the goal
     */
    private void checkGoalCollision() {
        for (Entity entity : entityMap) {
            if (entity.getType() == EntityType.GOAL && player.node().getBoundsInParent().intersects(entity.node().getBoundsInParent())) {
                System.out.println("You win!");
                // When the player reaches the goal, switch back to the menu scene
                mainApp.switchToMenu();
            }
        }
    }

    /**
     * Check if the player dies (falls off the screen)
     */
    private void checkDie() {
        if (player.node().getTranslateY() > 720) {
            System.out.println("You lose!");
            // When the player falls off the screen, switch to the fail scene
            mainApp.switchToFail();
        }
    }
}
