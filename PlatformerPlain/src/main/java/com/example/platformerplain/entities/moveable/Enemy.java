package com.example.platformerplain.entities.moveable;

import com.example.platformerplain.Assets;
import com.example.platformerplain.Constants;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.data.MoveData;
import com.example.platformerplain.move.data.MoveState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static com.example.platformerplain.Assets.JUMP_SFX;


public class Enemy extends Moveable {
    Image[] frames;
    private GraphicsContext gc;
    public boolean isDead;
    private boolean deathAnimationSet = false;
    private MoveEnemy moveEnemyLogic;
    private MoveState lastState;

    public Enemy(int x, int y, int w, int h) {
        hitBox = new Rectangle(Constants.ENEMY_SIZE, Constants.ENEMY_SIZE, Color.RED);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);
        this.moveEnemyLogic = new MoveEnemy(this, (ArrayList<Entity>) GameModel.getCollidableMap(), LevelData.getLevelInformation.getLevelWidth());

        frames = Assets.FROG_IDLE[0];
        animation.setFrames(frames);
        animation.setDelay(10);

        canvas = new Canvas(96,96);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(hitBox.getTranslateX());
        canvas.setTranslateY(hitBox.getTranslateY());
    }


    @Override
    public void update() {
        if(isDead && !deathAnimationSet){
            frames = Assets.FROG_DEATH[0];
            animation.setFrames(frames);
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

    @Override
    public int[] size() {
        return new int[]{Constants.ENEMY_SIZE, Constants.ENEMY_SIZE};
    }

    @Override
    public EntityType getType() {
        return EntityType.ENEMY;
    }

    public MoveData getMoveStatus() {
        return moveEnemyLogic.getMoveData();
    }
}