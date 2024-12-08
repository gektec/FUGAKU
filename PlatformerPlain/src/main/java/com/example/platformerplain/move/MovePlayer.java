package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.Main;
import com.example.platformerplain.entities.Enemy;
import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.Ladder;
import com.example.platformerplain.entities.Spike;
import com.example.platformerplain.move.Command.JumpCommand;
import com.example.platformerplain.move.Command.MoveLeftCommand;
import com.example.platformerplain.move.Command.MoveRightCommand;
import com.example.platformerplain.move.Command.PlayCommand;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.platformerplain.Constants.MAX_FALL_SPEED;
import static com.example.platformerplain.Constants.RESISTANCE;

public class MovePlayer {
    private static final int MAX_CLIMB_SPEED = 6;
    private Entity player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Entity> entityMap; // List to store all platform and goal entities
    private ArrayList<Ladder> ladders;
    private ArrayList<Spike> spikes;
    private boolean onGround;  // Flag indicating whether the player can jump
    private boolean moveLeft;
    private boolean canDash;
    private boolean onWall;
    private MoveState playerState;

    private HashMap<KeyCode, Boolean> keys;  // Map to store the state of keyboard keys
    private Coord2D playerVelocity; // Current velocity of the player
    private Timeline dashCooldownTimer;
    private Timeline slideJumpCooldownTimer;
    private boolean haveJKeyReleased = true;
    private boolean isPlayerDead = false;

    public MovePlayer(Entity player, ArrayList<Entity> platforms, ArrayList<Enemy> enemies, ArrayList<Ladder> ladders , ArrayList<Spike> spikes, int levelWidth, HashMap<KeyCode, Boolean> keys, Main main) {
        this.player = player;
        this.entityMap = platforms;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.onGround = true;
        this.enemies = enemies;
        this.spikes = spikes;
        this.ladders = ladders;
        this.playerState = MoveState.IDLE;

        dashCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.DASH_DURATION), event -> playerState = MoveState.IDLE));
        dashCooldownTimer.setCycleCount(1);

        slideJumpCooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Constants.SLIDE_JUMP_DURATION), event -> playerState = MoveState.IDLE));
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
        if (!onGround && !onWall) {
            haveJKeyReleased = false;
        }

        if (playerState == MoveState.DASHING || playerState == MoveState.SLIDE_JUMPING) {
            if (playerVelocity.getX() != 0 && playerVelocity.getY() != 0)
                playerVelocity.reduce((int) RESISTANCE / 2, (int) RESISTANCE / 2);
            else
                playerVelocity.reduce(RESISTANCE, RESISTANCE);
        } else {
            // Jump
            PlayCommand jump = new JumpCommand(player, playerVelocity, new MoveStatus(playerState,false, onGround, canDash, onWall, playerVelocity));
            // Slide jump
            PlayCommand slideJump = new JumpCommand(player, playerVelocity, new MoveStatus(playerState, false, false, canDash, false, playerVelocity));
            // Move left and right commands
            PlayCommand moveLeft = new MoveLeftCommand(player, playerVelocity);
            PlayCommand moveRight = new MoveRightCommand(player, playerVelocity);

            if (isPressed(KeyCode.J) && haveJKeyReleased && onGround && playerState != MoveState.DASHING) {
                jump.execute();
                haveJKeyReleased = false;
            }
            // Slide jump
            else if (isPressed(KeyCode.J) && haveJKeyReleased && onWall) {
                slideJump.execute();
                haveJKeyReleased = false;
                playerState = MoveState.SLIDE_JUMPING;
                slideJumpCooldownTimer.playFromStart();
            }
            // Move left
            if (isPressed(KeyCode.A) && playerVelocity.getX() >= -8) {
                moveLeft.execute();
            }
            // Move right
            if (isPressed(KeyCode.D) && playerVelocity.getX() <= 8) {
                moveRight.execute();
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
            // Resistance
            playerVelocity.reduce(RESISTANCE, 0);
        }

        // apply gravity when not dashing
        if (playerVelocity.getY() < MAX_FALL_SPEED) {
            playerVelocity.add(0, Constants.GRAVITY);
        }

        for (Ladder ladder : ladders) {
            if (player.hitBox().getBoundsInParent().intersects(ladder.hitBox().getBoundsInParent())) {
                if (isPressed(KeyCode.W) && playerVelocity.getY() >= -MAX_CLIMB_SPEED) {
                    playerVelocity.add(0, -4);
                    break;
                }
                if (isPressed(KeyCode.S) && playerVelocity.getY() <= MAX_CLIMB_SPEED) {
                    playerVelocity.add(0, 4);
                    break;
                }
            }
        }

        MoveStatus moveStatus = new MoveStatus(playerState, moveLeft, onGround, canDash, onWall, playerVelocity);
        Move.move(player, moveStatus);

        playerState = moveStatus.moveState;
        moveLeft = moveStatus.faceLeft;
        onGround = moveStatus.canJump;
        canDash = moveStatus.canDash;
        onWall = moveStatus.canSlideJump;

        checkGoalCollision();
        checkEnemy();
        checkSpike();
        checkFall();
    }

    private void checkGoalCollision() {
        for (Entity entity : entityMap) {
            if (entity.getType() == Constants.EntityType.GOAL && player.hitBox().getBoundsInParent().intersects(entity.hitBox().getBoundsInParent())) {
                System.out.println("You win!");
                Main.getInstance().transitionToNextLevel();
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
        Main.getInstance().stopGameLoop();  // Stop game loop on player death
        Main.getInstance().exitGame();
    }

    public MoveState getPlayerState() {
        return playerState;
    }

    public MoveStatus getMoveStatus() {
        return new MoveStatus(playerState, moveLeft, onGround, canDash, onWall, playerVelocity);
    }
}
