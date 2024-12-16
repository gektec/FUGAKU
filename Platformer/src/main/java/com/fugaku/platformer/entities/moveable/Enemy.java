package com.fugaku.platformer.entities.moveable;

import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.data.LevelData;
import com.fugaku.platformer.entities.Entity;
import com.fugaku.platformer.entities.EntityType;
import com.fugaku.platformer.model.GameModel;
import com.fugaku.platformer.move.Coord2D;
import com.fugaku.platformer.move.Move;
import com.fugaku.platformer.move.MoveEnemy;
import com.fugaku.platformer.move.state.MoveData;
import com.fugaku.platformer.move.state.MoveState;
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
    private final GraphicsContext gc;
    public boolean isDead;
    private boolean deathAnimationSet = false;
    private final MoveEnemy moveEnemyLogic;
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
        hitBox = new Rectangle(w, h, Color.RED);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);
        this.moveEnemyLogic = new MoveEnemy(this, (ArrayList<Entity>) GameModel.getCollidableMap());
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
            Move.centerAlign(this, new Coord2D(0,-8));
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
