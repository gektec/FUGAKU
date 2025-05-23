package com.fugaku.platformer.move;

import com.fugaku.platformer.Main;
import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.data.LevelData;
import com.fugaku.platformer.entities.Entity;
import com.fugaku.platformer.entities.EntityType;
import com.fugaku.platformer.entities.moveable.Enemy;
import com.fugaku.platformer.entities.tile.Coin;
import com.fugaku.platformer.entities.tile.Ladder;
import com.fugaku.platformer.entities.tile.Spike;
import com.fugaku.platformer.model.GameModel;
import com.fugaku.platformer.move.command.*;
import com.fugaku.platformer.move.state.MoveState;
import com.fugaku.platformer.move.state.MoveData;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fugaku.platformer.data.Assets.COIN_SFX;
import static com.fugaku.platformer.data.Assets.JUMP_SFX;
import static com.fugaku.platformer.data.Constants.MAX_FALL_SPEED;
import static com.fugaku.platformer.data.Constants.RESISTANCE;

/**
 *
 * The class includes functionalities for jumping, moving left and right, dashing, climbing, and interacting with game entities like coins, enemies, and spikes.
 *
 * @author Changyu Li
 * @date 2024/12/15
 */
public class MovePlayer {
    private static Entity player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Entity> entityMap; // List to store all platform and goal entities
    private final ArrayList<Ladder> ladders;
    private final ArrayList<Spike> spikes;
    private final ArrayList<Coin> coinMap;
    private boolean isTouchingGround;  // Flag indicating whether the player can jump
    private boolean isFacingLeft;
    private boolean canDash = true;
    private boolean canClimb = false;
    private boolean isTouchingWall;
    private MoveState playerState;

    private final HashMap<KeyCode, Boolean> keys;  // Map to store the state of keyboard keys
    private final Coord2D playerVelocity; // Current velocity of the player
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
     * @param keys       A HashMap storing the state of keyboard keys.
     */
    public MovePlayer(Entity player, ArrayList<Entity> platforms, ArrayList<Enemy> enemies, ArrayList<Ladder> ladders, ArrayList<Spike> spikes, ArrayList<Coin> coinMap, HashMap<KeyCode, Boolean> keys) {
        MovePlayer.player = player;
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
        playerState = moveData.getState();
        isFacingLeft = moveData.isFacingLeft;
        isTouchingGround = moveData.isTouchingGround;
        isTouchingWall = moveData.isTouchingWall;
        canClimb = false;
        canDash = canDash || isTouchingGround;

        PlayCommand jump = new JumpCommand(moveData);
        PlayCommand slideJump = new WallJumpCommand(moveData);
        PlayCommand moveLeft = new MoveLeftCommand(moveData);
        PlayCommand moveRight = new MoveRightCommand(moveData);
        PlayCommand dash = new DashCommand(moveData);
        PlayCommand climb = new ClimbCommand(moveData);

        if (!isPressed(KeyCode.J)) {
            haveJKeyReleased = true;
        }
        if (!isTouchingGround && !isTouchingWall) {
            haveJKeyReleased = false;
        }
        if (!isPressed(KeyCode.K)) {
            haveKKeyReleased = true;
        }

        if (playerState == MoveState.DASHING || playerState == MoveState.SLIDE_JUMPING) {
            if (playerVelocity.getX() != 0 && playerVelocity.getY() != 0)
                playerVelocity.reduce((double) RESISTANCE / 2, (double) RESISTANCE / 2);
            else
                playerVelocity.reduce((RESISTANCE / 1.4), (RESISTANCE / 1.4));
        } else {
            if (isPressed(KeyCode.J) && haveJKeyReleased && isTouchingGround && playerState != MoveState.DASHING) {
                jump.execute();
                JUMP_SFX.play();
                haveJKeyReleased = false;
            }
            // Slide jump
            else if (isPressed(KeyCode.J) && haveJKeyReleased && isTouchingWall && !isTouchingGround && playerState != MoveState.SLIDE_JUMPING) {
                slideJump.execute();
                JUMP_SFX.play();
                haveJKeyReleased = false;
            }
            // Move left
            if (isPressed(KeyCode.A) && playerVelocity.getX() >= -Constants.MAX_MOVE_SPEED) {
                moveLeft.execute();
            }
            // Move right
            if (isPressed(KeyCode.D) && playerVelocity.getX() <= Constants.MAX_MOVE_SPEED) {
                moveRight.execute();
            }

            // Dash
            if (canDash && isPressed(KeyCode.K) && haveKKeyReleased) {
                dash.execute();
                canDash = false;
                haveKKeyReleased = false;
            }
            // Resistance
            if (isPressed(KeyCode.A) == isPressed(KeyCode.D)) playerVelocity.smoothReduce(RESISTANCE,0,10,0);

            // Apply gravity when not dashing
            if (playerVelocity.getY() < MAX_FALL_SPEED && playerState != MoveState.CLIMBING) {
                playerVelocity.smoothMinus(0, Constants.GRAVITY, 0, (double) MAX_FALL_SPEED /2);
            }
        }

        for (Ladder ladder : ladders) {
            if (player.hitBox().getBoundsInParent().intersects(ladder.hitBox().getBoundsInParent())) {
                climb.execute();
                canClimb = true;
            }
        }
        if(!canClimb && moveData.stateIs(MoveState.CLIMBING)) moveData.setState(MoveState.IDLE);

        Move.move(player, moveData);

        checkCoin();
        checkGoal();
        if(!GameModel.isDebugMode()) {
            checkEnemy();
            checkSpike();
        }
        checkFall();

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
                Main.putDebugInfo("Coin collected");
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
                Main.putDebugInfo("Level complete");
                GameModel.transitionToNextLevel();
                return;
            }
        }
    }

    /**
     * Checks if the player has fallen below the level bounds.
     */
    private void checkFall() {
        if (player.hitBox().getTranslateY() > LevelData.getLevelInformation.getLevelHeight() + 50) {
            Main.putDebugInfo("Player fell");
            Die();
        }
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
                    Main.putDebugInfo("Enemy killed");
                } else {
                    Main.putDebugInfo("Hit enemy");
                    Die();
                }
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
                    Main.putDebugInfo("Hit spike");
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
        Main.putDebugInfo("Player dead");
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
     * Gets the current move state of the player.
     *
     * @return The current MoveData of the player.
     */
    public static MoveData getMoveData() {
        return moveData;
    }
}
