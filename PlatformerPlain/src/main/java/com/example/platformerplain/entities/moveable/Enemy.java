package com.example.platformerplain.entities.moveable;

import com.example.platformerplain.Assets;
import com.example.platformerplain.Constants;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.data.MoveData;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class Enemy extends Moveable {
    Image[] frames;
    private GraphicsContext gc;
    public boolean isDead;
    private boolean deathAnimationSet = false;
    private MoveEnemy moveEnemyLogic;

    public Enemy(int x, int y, int w, int h) {
        hitBox = new Rectangle(Constants.ENEMY_SIZE, Constants.ENEMY_SIZE, Color.RED);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);
        this.moveEnemyLogic = new MoveEnemy(this, (ArrayList<Entity>) GameModel.getCollidableMap(), LevelData.getLevelInformation.getLevelWidth());

        frames = Assets.GHOST_IDLE[0];
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
            frames = Assets.GHOST_DEATH[0];
            animation.setFrames(frames);
            animation.setDelay(10);
            deathAnimationSet = true;
            GameModel.killedEnemy();
        }
        if(isDead && animation.hasPlayedOnce()){
            removeFromGame();
        } else {
            // Update and draw
            animation.update();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            Image sprite = animation.getImage();
            if (sprite != null) {
                //sprite = ImageScaler.nearestNeighborScale(sprite);
                gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
            }
            moveEnemyLogic.update();

        }
    }

    public void removeFromGame() {
        GameModel.removeEntity(this);
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
        return moveEnemyLogic.getMoveStatus();
    }
}