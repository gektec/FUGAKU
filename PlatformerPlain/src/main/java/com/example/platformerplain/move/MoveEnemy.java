package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.Enemy;
import com.example.platformerplain.entities.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.platformerplain.Constants.MAX_FALL_SPEED;
import static com.example.platformerplain.Constants.RESISTANCE;

public class MoveEnemy {
    private Enemy enemy;
    private ArrayList<Entity> entityMap;
    private final int gravity = 1;  //todo: inherit from move
    private boolean canJump;
    //private int levelWidth;
    private HashMap<KeyCode, Boolean> keys;
    private Coord2D velocity;
    private MoveState enemyState;


    public MoveEnemy(Enemy enemy, ArrayList<Entity> platforms, int levelWidth, HashMap<KeyCode, Boolean> keys) {
        this.enemy = enemy;
        this.entityMap = platforms;
        //this.levelWidth = levelWidth;
        this.keys = keys;
        this.velocity = new Coord2D(0, 0);
        this.canJump = true;
        this.enemyState = MoveState.DEFAULT;
    }
//todo
    //Rectangle leftEdgeSensor = new Rectangle(5, Constants.PLAYER_SIZE, Color.BLUE);

    public void update() {

        if (enemy.isDead) {
            return;
        }

        velocity.reduce(RESISTANCE, 0);

        if (velocity.getY() < MAX_FALL_SPEED) {
            velocity.add(0, gravity);
        }

        if(canJump){
            velocity.add(0, -20);
            canJump = false;
        }
        MoveStatus moveStatus = new MoveStatus(enemyState, false, false, false);
        Move.move(enemy, velocity, moveStatus);
        canJump = moveStatus.canJump;

//        leftEdgeSensor.setX(enemy.hitBox().getTranslateX() - 5);
//        leftEdgeSensor.setY(enemy.hitBox().getTranslateY() - Constants.ENEMY_SIZE/2);

    }
}
