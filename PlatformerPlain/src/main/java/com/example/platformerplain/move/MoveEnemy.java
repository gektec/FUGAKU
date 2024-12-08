package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.Enemy;
import com.example.platformerplain.entities.Entity;

import java.util.ArrayList;

import static com.example.platformerplain.Constants.MAX_FALL_SPEED;
import static com.example.platformerplain.Constants.RESISTANCE;

public class MoveEnemy {
    private Enemy enemy;
    private ArrayList<Entity> entityMap;
    private boolean moveLeft;
    private boolean canJump;
    //private int levelWidth;
    private Coord2D velocity;
    private MoveState enemyState;


    public MoveEnemy(Enemy enemy, ArrayList<Entity> platforms, int levelWidth) {
        this.enemy = enemy;
        this.entityMap = platforms;
        //this.levelWidth = levelWidth;
        this.velocity = new Coord2D(0, 0);
        this.canJump = true;
        this.enemyState = MoveState.IDLE;
    }
//todo
    //Rectangle leftEdgeSensor = new Rectangle(5, Constants.PLAYER_SIZE, Color.BLUE);

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
        MoveStatus moveStatus = new MoveStatus(enemyState, false,false, false, false, velocity);
        Move.move(enemy, moveStatus);

        moveLeft = moveStatus.faceLeft;
        canJump = moveStatus.canJump;

//        leftEdgeSensor.setX(enemy.hitBox().getTranslateX() - 5);
//        leftEdgeSensor.setY(enemy.hitBox().getTranslateY() - Constants.ENEMY_SIZE/2);
    }
    public MoveStatus getMoveStatus() {
        return new MoveStatus(enemyState, moveLeft, canJump, false, false, velocity);
    }
}
