package com.fugaku.platformer.move;

import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.entities.moveable.Enemy;
import com.fugaku.platformer.entities.Entity;
import com.fugaku.platformer.move.command.JumpCommand;
import com.fugaku.platformer.move.command.MoveLeftCommand;
import com.fugaku.platformer.move.command.MoveRightCommand;
import com.fugaku.platformer.move.command.PlayCommand;
import com.fugaku.platformer.move.state.MoveData;
import com.fugaku.platformer.move.state.MoveState;

import java.util.ArrayList;

import static com.fugaku.platformer.data.Constants.MAX_FALL_SPEED;
import static com.fugaku.platformer.data.Constants.RESISTANCE;

/**
 * This class is responsible for controlling the movement of enemy entities in the platformer game.
 * It handles the enemy's state, velocity, and interactions with the game environment.
 *
 * @author Changyu Li
 * @date 2024/11/11
 */
public class MoveEnemy {
    private final Enemy enemy;
    private boolean isFacingLeft;
    private boolean isTouchingGround;
    private boolean isTouchingWall;
    private final Coord2D velocity;
    private MoveState enemyState;
    private final MoveData moveData;
    private double waitingTime = 0;

    /**
     * Constructs a MoveEnemy instance for the given enemy and platform entities.
     *
     * @param enemy the enemy entity to be controlled
     * @param platforms the list of platform entities in the game
     */
    public MoveEnemy(Enemy enemy, ArrayList<Entity> platforms) {
        this.enemy = enemy;
        this.velocity = new Coord2D(0, 0);
        this.isTouchingGround = true;
        this.enemyState = MoveState.IDLE;
        moveData = new MoveData(enemyState, isFacingLeft, isTouchingGround, isTouchingWall, velocity);
    }

    /**
     * Updates the enemy's movement and state based on game logic.
     * <p>This method processes enemy actions such as jumping and moving left or right,
     * applies gravity, and updates the enemy's position.</p>
     * <p>It also handles waiting time for jump actions.</p>
     */
    public void update() {
        if (enemy.isDead) {
            return;
        }

        enemyState = moveData.getState();
        isTouchingGround = moveData.isTouchingGround;
        isTouchingWall = moveData.isTouchingWall;

        PlayCommand jump = new JumpCommand(moveData);
        PlayCommand moveLeft = new MoveLeftCommand(moveData);
        PlayCommand moveRight = new MoveRightCommand(moveData);

        if (isTouchingGround && waitingTime <= 0) {
            jump.execute();
            isFacingLeft = !isFacingLeft;
            waitingTime = (Math.random() * 6 + 3);
        }

        if(moveData.stateIs(MoveState.JUMPING)) {
            if(isFacingLeft) {
                moveLeft.execute();
            } else {
                moveRight.execute();
            }
        }
        else waitingTime -= 0.1;

        velocity.reduce(RESISTANCE, 0);

        if (velocity.getY() < MAX_FALL_SPEED) {
            velocity.add(0, Constants.GRAVITY);
        }

        Move.move(enemy, moveData);

        isTouchingGround = moveData.isTouchingGround;
        isTouchingWall = moveData.isTouchingWall;
    }

    /**
     * Gets the current movement state of the enemy.
     *
     * @return a MoveData object containing the current state and movement information of the enemy
     */
    public MoveData getMoveData() {
        return new MoveData(enemyState, isFacingLeft, isTouchingGround, false, velocity);
    }
}
