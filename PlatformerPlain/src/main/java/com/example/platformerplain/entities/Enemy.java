package com.example.platformerplain.entities;

import com.example.platformerplain.Assets;
import com.example.platformerplain.Constants;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.move.MoveStatus;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static com.example.platformerplain.View.GameScreen.getCollidableMap;


public class Enemy extends Entity {
    private Node hitBox;
    Image[] frames;
    private Canvas canvas;
    private GraphicsContext gc;
    public boolean isDead;
    private boolean deathAnimationSet = false;
    private MoveEnemy moveEnemyLogic;

    protected boolean isAnimated(){
        return true;
    };

    public Enemy(int x, int y, int w, int h) {
        hitBox = new Rectangle(Constants.ENEMY_SIZE, Constants.ENEMY_SIZE, Color.RED);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);
        this.moveEnemyLogic = new MoveEnemy(this, (ArrayList<Entity>) GameModel.getInstance().getCollidableMap(), LevelData.getLevelInformation.getLevelWidth());

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
        GameModel.getInstance().removeEnemy(this);
    }

    @Override
    public int[] size() {
        return new int[]{Constants.ENEMY_SIZE, Constants.ENEMY_SIZE};
    }

    @Override
    public Node canvas() {
        return canvas;
    }

    @Override
    public Node hitBox() {
        return hitBox;
    }

    @Override
    public EntityType getType() {
        return EntityType.ENEMY;
    }

    public MoveStatus getMoveStatus() {
        return moveEnemyLogic.getMoveStatus();
    }
}