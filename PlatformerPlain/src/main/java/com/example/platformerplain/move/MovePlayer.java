package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.entities.moveable.Enemy;
import com.example.platformerplain.entities.tile.Coin;
import com.example.platformerplain.entities.tile.Ladder;
import com.example.platformerplain.entities.tile.Spike;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.command.*;
import com.example.platformerplain.move.data.MoveState;
import com.example.platformerplain.move.data.MoveData;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.platformerplain.Assets.COIN_SFX;
import static com.example.platformerplain.Assets.JUMP_SFX;
import static com.example.platformerplain.Constants.MAX_FALL_SPEED;
import static com.example.platformerplain.Constants.RESISTANCE;

/**
 * <h3>PlatformerPlain</h3>
 *
 * <p>
 * The class includes functionalities for jumping, moving left and right, dashing, climbing, and interacting with game entities like coins, enemies, and spikes.
 * </p>
 *
 * @author Changyu Li
 * @date 2024/12/15
 */
public class MovePlayer {
    private static Entity player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Entity> entityMap; // List to store all platform and goal entities
    private ArrayList<Ladder> ladders;
    private ArrayList<Spike> spikes;
    private ArrayList<Coin> coinMap;
    private boolean isTouchingGround;  // Flag indicating whether the player can jump
    private boolean isFacingLeft;
    private boolean canDash = true;
    private boolean canClimb = false;
    private boolean isTouchingWall;
    private MoveState playerState;

    private HashMap<KeyCode, Boolean> keys;  // Map to store the state of keyboard keys
    private Coord2D playerVelocity; // Current velocity of the player
    private boolean haveJKeyReleased = true;
    private boolean haveKKeyReleased = true;
    private static MoveData moveData;

    /**
     * Constructor to initialize the MovePlayer object.
     *
     * @param player     The Entity object representing the player.
     * @param platforms  A list of platform entities in the game.
     * @param enemies    A list of enemy entities in the game.
     * @param ladders    A list of ladder entities in the game.
     * @param spikes     A list of spike entities in the game.
     * @param coinMap    A list of coin entities in the game.
     * @param levelWidth The width of the current level.
     * @param keys       A HashMap storing the state of keyboard keys.
     */
    public MovePlayer(Entity player, ArrayList<Entity> platforms, ArrayList<Enemy> enemies, ArrayList<Ladder> ladders, ArrayList<Spike> spikes, ArrayList<Coin> coinMap, int levelWidth, HashMap<KeyCode, Boolean> keys) {
        this.player = player;
        this.entityMap = platforms;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.isTouchingGround = true;
        this.enemies = enemies;
        this.spikes = spikes;
        this.ladders = ladders;
        this.coinMap = coinMap;
        this.playerState = MoveState.IDLE;
        moveData = new MoveData(playerState, isFacingLeft, isTouchingGround, isTouchingWall, playerVelocity);
    }

    /**
     * Checks if a given key is pressed.
     *
     * @param key The key to be checked.
     * @return True if the key is pressed, false otherwise.
     */
    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    /**
     * Updates the player movement and state based on the current inputs and interactions.
     * This method processes input for movement, jumping, dashing, and climbing.
     */
    public void update() {
        // Update player state and handle movement logic here
        playerState = moveData.getState();
        // Remaining implementation...
    }

    /**
     * Checks if the player has collected any coins.
     */
    private void checkCoin() {
        if (coinMap == null) return;
        for (Coin coin : coinMap) {
            if (player.hitBox().getBoundsInParent().intersects(coin.hitBox().getBoundsInParent())) {
                coin.isCollected = true;
                COIN_SFX.play();
                return;
            }
        }
    }

    /**
     * Checks if the player has reached the goal.
     */
    private void checkGoal() {
        for (Entity entity : entityMap) {
            if (entity.getType() == EntityType.GOAL && player.hitBox().getBoundsInParent().intersects(entity.hitBox().getBoundsInParent())) {
                System.out.println("You win!");
                GameModel.transitionToNextLevel();
                return;
            }
        }
    }

    /**
     * Checks if the player has fallen below the level bounds.
     */
    private void checkFall() {
        if (player.hitBox().getTranslateY() > LevelData.getLevelInformation.getLevelHeight() + 50)
            Die();
    }

    /**
     * Checks if the player has collided with an enemy.
     */
    private void checkEnemy() {
        for (Enemy enemy : enemies) {
            if (player.hitBox().getBoundsInParent().intersects(enemy.hitBox().getBoundsInParent()) && !enemy.isDead) {
                if (getMoveData().velocity.getY() > enemy.getMoveStatus().velocity.getY()) {
                    enemy.isDead = true;
                    playerVelocity.set(0, -15);
                    System.out.println("enemy killed");
                } else Die();
            }
        }
    }

    /**
     * Checks if the player has collided with a spike.
     */
    private void checkSpike() {
        for (Spike spike : spikes) {
            if (spike.hitBox() != null) {
                if (player.hitBox().getBoundsInParent().intersects(spike.hitBox().getBoundsInParent())) {
                    spike.playerDead();
                    Die();
                }
            }
        }
    }

    /**
     * Handles the player's death.
     * Stops the game loop and exits the game.
     */
    private void Die() {
        System.out.println("You lose!");
        GameModel.stopGameLoop(); // Stop game loop on player death
        GameModel.exitGame();
    }

    /**
     * Gets the current position of the player.
     *
     * @return The player's current position as a Coord2D object.
     */
    public static Coord2D getPlayerPosition() {
        return new Coord2D(player.hitBox().getTranslateX(), player.hitBox().getTranslateY());
    }

    /**
     * Gets the current move data of the player.
     *
     * @return The current MoveData of the player.
     */
    public MoveData getMoveData() {
        return moveData;
    }
}
