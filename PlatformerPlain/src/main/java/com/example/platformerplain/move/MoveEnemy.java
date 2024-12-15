package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.moveable.Enemy;
import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.move.command.*;
import com.example.platformerplain.move.data.MoveData;
import com.example.platformerplain.move.data.MoveState;

import java.util.ArrayList;

import static com.example.platformerplain.Constants.MAX_FALL_SPEED;
import static com.example.platformerplain.Constants.RESISTANCE;

/**
 * @author Changyu Li
 * @date 2024/11/24
 */
public class MoveEnemy {
    private Enemy enemy;
    private ArrayList<Entity> entityMap;
    private boolean isFacingLeft;
    private boolean isTouchingGround;
    private boolean isTouchingWall;
    private Coord2D velocity;
    private MoveState enemyState;
    private MoveData moveData;
    private double waitingTime = 0;


    /**
     * @param enemy
     * @param platforms
     * @param levelWidth
     */
    public MoveEnemy(Enemy enemy, ArrayList<Entity> platforms, int levelWidth) {
        this.enemy = enemy;
        this.entityMap = platforms;
        //this.levelWidth = levelWidth;
        this.velocity = new Coord2D(0, 0);
        this.isTouchingGround = true;
        this.enemyState = MoveState.IDLE;
        moveData = new MoveData(enemyState, isFacingLeft, isTouchingGround, isTouchingWall, velocity);
    }
//todo

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

        waitingTime -= 0.1;

        if (isTouchingGround && waitingTime <= 0) {
            jump.execute();
            isFacingLeft = !isFacingLeft;
            waitingTime = (Math.random() * 10);
        }

        if(moveData.stateIs(MoveState.JUMPING)) {
            if(isFacingLeft) {
                moveLeft.execute();
            } else {
                moveRight.execute();
            }
        }


        velocity.reduce(RESISTANCE, 0);

        if (velocity.getY() < MAX_FALL_SPEED) {
            velocity.add(0, Constants.GRAVITY);
        }

        Move.move(enemy, moveData);

        isTouchingGround = moveData.isTouchingGround;
        isTouchingWall = moveData.isTouchingWall;


    }
    public MoveData getMoveData() {
        return new MoveData(enemyState, isFacingLeft, isTouchingGround, false, velocity);
    }
}
