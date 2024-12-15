package com.example.platformerplain.entities.moveable;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.data.Constants;
import com.example.platformerplain.data.LevelData;
import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.state.MoveData;
import com.example.platformerplain.move.state.MoveState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Represents an enemy entity in the platformer game that can move and perform actions.
 * The enemy can jump, random move, and die, with corresponding animations for each state.
 *
 * @author Changyu Li
 * @date 2024/11/11
 */
public class Enemy extends Moveable {
    private GraphicsContext gc;
    public boolean isDead;
    private boolean deathAnimationSet = false;
    private MoveEnemy moveEnemyLogic;
    private MoveState lastState;

    /**
     * Constructs an Enemy instance at the specified position and size.
     *
     * @param x the x-coordinate of the enemy's position
     * @param y the y-coordinate of the enemy's position
     * @param w the width of the enemy
     * @param h the height of the enemy
     */
    public Enemy(int x, int y, int w, int h) {
        hitBox = new Rectangle(Constants.ENEMY_SIZE, Constants.ENEMY_SIZE, Color.RED);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);
        this.moveEnemyLogic = new MoveEnemy(this, (ArrayList<Entity>) GameModel.getCollidableMap(), LevelData.getLevelInformation.getLevelWidth());
        animation.setFrames(Assets.FROG_IDLE[0]);
        animation.setDelay(10);

        canvas = new Canvas(96,96);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(hitBox.getTranslateX());
        canvas.setTranslateY(hitBox.getTranslateY());
    }

    /**
     * Updates the enemy's state, including animations and movement.
     * If the enemy is dead, it will handle the death animation and removal.
     */
    @Override
    public void update() {
        if(isDead && !deathAnimationSet){
            animation.setFrames(Assets.FROG_DEATH[0]);
            animation.setDelay(10);
            deathAnimationSet = true;
            GameModel.killedEnemy();
        }
        if(isDead && animation.hasPlayedOnce()){
            GameModel.removeEntity(this);
        } else {
            // Update and draw
            if(moveEnemyLogic.getMoveData().stateIs(MoveState.JUMPING) || moveEnemyLogic.getMoveData().stateIs(MoveState.FALLING)){
                if (lastState != MoveState.JUMPING) {
                    animation.setFrames(Assets.FROG_JUMP[0]);
                    animation.setDelay(3);
                    lastState = MoveState.JUMPING;
                }
            }
            else if (lastState != MoveState.IDLE) {
                animation.setFrames(Assets.FROG_IDLE[0]);
                animation.setDelay(10);
                lastState = MoveState.IDLE;
            }
            animation.update();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            Image sprite = animation.getImage(moveEnemyLogic.getMoveData().isFacingLeft);
            if (sprite != null) {
                gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
            }
            moveEnemyLogic.update();
        }
    }

    /**
     * Returns the size of the enemy.
     *
     * @return an array containing the width and height of the enemy
     */
    @Override
    public int[] size() {
        return new int[]{Constants.ENEMY_SIZE, Constants.ENEMY_SIZE};
    }

    /**
     * Returns the type of the entity.
     *
     * @return the entity type as ENEMY
     */
    @Override
    public EntityType getType() {
        return EntityType.ENEMY;
    }

    /**
     * Gets the current movement status of the enemy.
     *
     * @return the MoveData object representing the enemy's movement state
     */
    public MoveData getMoveStatus() {
        return moveEnemyLogic.getMoveData();
    }
}
