package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.*;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.Command.*;
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

    public MovePlayer(Entity player, ArrayList<Entity> platforms, ArrayList<Enemy> enemies, ArrayList<Ladder> ladders , ArrayList<Spike> spikes, int levelWidth, HashMap<KeyCode, Boolean> keys) {
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

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

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
            //playerVelocity.reduce(RESISTANCE, 0);
            if (isPressed(KeyCode.A) == isPressed(KeyCode.D)) playerVelocity.smoothReduce(RESISTANCE,0,10,0);

            // apply gravity when not dashing
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

    private void checkGoal() {
        for (Entity entity : entityMap) {
            if (entity.getType() == EntityType.GOAL && player.hitBox().getBoundsInParent().intersects(entity.hitBox().getBoundsInParent())) {
                System.out.println("You win!");
                GameModel.getInstance().transitionToNextLevel();
                return;
            }
        }
    }

    private void checkFall() {
        //test
        if (player.hitBox().getTranslateY() > LevelData.getLevelInformation.getLevelHeight() + 50)
            Die();
    }

    private void checkEnemy() {
        for (Enemy enemy : enemies) {
            if (player.hitBox().getBoundsInParent().intersects(enemy.hitBox().getBoundsInParent()) && !enemy.isDead) {
                if (getMoveStatus().velocity.getY() > enemy.getMoveStatus().velocity.getY()) {
                    enemy.isDead = true;
                    playerVelocity.set(0, -15);
                    // test
                    System.out.println("enemy killed");
                } else Die();
            }
        }
    }

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

    private void Die() {
        System.out.println("You lose!");
        GameModel.getInstance().stopGameLoop(); // Stop game loop on player death
        GameModel.getInstance().exitGame();
    }

    public MoveState getPlayerState() {
        return playerState;
    }

    public MoveStatus getMoveStatus() {
        return moveStatus;
    }
}
