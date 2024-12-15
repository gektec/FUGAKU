package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.entities.moveable.Enemy;
import com.example.platformerplain.entities.tile.Ladder;
import com.example.platformerplain.entities.tile.Spike;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.command.*;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.platformerplain.Constants.MAX_FALL_SPEED;
import static com.example.platformerplain.Constants.RESISTANCE;

/**
 * This class handles the movement logic and state management for the player character in the platformer game.
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
    private static MoveStatus moveStatus;

    /**
     * Constructor to initialize the MovePlayer object.
     *
     * @param player  The Entity object representing the player.
     * @param platforms A list of platform entities in the game.
     * @param enemies A list of enemy entities in the game.
     * @param ladders A list of ladder entities in the game.
     * @param spikes A list of spike entities in the game.
     * @param levelWidth The width of the current level.
     * @param keys A HashMap storing the state of keyboard keys.
     */
    public MovePlayer(Entity player, ArrayList<Entity> platforms, ArrayList<Enemy> enemies, ArrayList<Ladder> ladders, ArrayList<Spike> spikes, int levelWidth, HashMap<KeyCode, Boolean> keys) {
        this.player = player;
        this.entityMap = platforms;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.isTouchingGround = true;
        this.enemies = enemies;
        this.spikes = spikes;
        this.ladders = ladders;
        this.playerState = MoveState.IDLE;
        moveStatus = new MoveStatus(playerState, isFacingLeft, isTouchingGround, isTouchingWall, playerVelocity);
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
     */
    public void update() {
        playerState = moveStatus.moveState;
        isFacingLeft = moveStatus.isFacingLeft;
        isTouchingGround = moveStatus.isTouchingGround;
        canClimb = false;
        canDash = canDash || isTouchingGround;

        PlayCommand jump = new JumpCommand(moveStatus);
        PlayCommand slideJump = new WallJumpCommand(moveStatus);
        PlayCommand moveLeft = new MoveLeftCommand(moveStatus);
        PlayCommand moveRight = new MoveRightCommand(moveStatus);
        PlayCommand dash = new DashCommand(moveStatus);
        PlayCommand climb = new ClimbCommand(moveStatus);

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
                haveJKeyReleased = false;
            }
            // Slide jump
            else if (isPressed(KeyCode.J) && haveJKeyReleased && isTouchingWall) {
                slideJump.execute();
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
        if(!canClimb && moveStatus.stateIs(MoveState.CLIMBING)) moveStatus.moveState = MoveState.IDLE;

        Move.move(player, moveStatus);

        checkGoal();
        checkEnemy();
        checkSpike();
        checkFall();
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
                if (getMoveStatus().velocity.getY() > enemy.getMoveStatus().velocity.getY()) {
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
    private void checkSpike(){
        for (Spike spike : spikes) {
            if(spike.hitBox() != null){
                if (player.hitBox().getBoundsInParent().intersects(spike.hitBox().getBoundsInParent())) {
                    spike.playerDead();
                    Die();
                }
            }
        }
    }

    /**
     * Handles the player's death.
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
     * Gets the current state of the player.
     *
     * @return The current MoveState of the player.
     */
    public MoveState getPlayerState() {
        return playerState;
    }

    /**
     * Gets the current move status of the player.
     *
     * @return The current MoveStatus of the player.
     */
    public MoveStatus getMoveStatus() {
        return moveStatus;
    }
}
