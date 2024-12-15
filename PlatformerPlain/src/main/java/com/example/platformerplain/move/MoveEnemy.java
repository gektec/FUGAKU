package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.moveable.Enemy;
import com.example.platformerplain.entities.Entity;

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
    private boolean moveLeft;
    private boolean canJump;
    //private int levelWidth;
    private Coord2D velocity;
    private MoveState enemyState;


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
        this.canJump = true;
        this.enemyState = MoveState.IDLE;
    }
//todo

    public void update() {

        if (enemy.isDead) {
            return;
        }

        velocity.reduce(RESISTANCE, 0);

        if (velocity.getY() < MAX_FALL_SPEED) {
            velocity.add(0, Constants.GRAVITY);
        }

        if(canJump){
            velocity.add(0, -20);
            canJump = false;
        }
        MoveStatus moveStatus = new MoveStatus(enemyState, false,false, false, velocity);
        Move.move(enemy, moveStatus);

        moveLeft = moveStatus.isFacingLeft;
        canJump = moveStatus.isTouchingGround;

//        leftEdgeSensor.setX(enemy.hitBox().getTranslateX() - 5);
//        leftEdgeSensor.setY(enemy.hitBox().getTranslateY() - Constants.ENEMY_SIZE/2);
    }
    public MoveStatus getMoveStatus() {
        return new MoveStatus(enemyState, moveLeft, canJump, false, velocity);
    }
}
