package com.example.platformerplain.entities;

import com.example.platformerplain.Constants;
import com.example.platformerplain.Main;
import com.example.platformerplain.move.MoveEnemy;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class Enemy extends Entity {
    private Node rectangle;
    Image[] frames;
    private Canvas canvas;
    private GraphicsContext gc;
    public boolean isDead;
    boolean deathAnimationSet = false;

    protected boolean isAnimated(){
        return true;
    };

    public Enemy(int x, int y, int w, int h) {
        rectangle = new Rectangle(Constants.ENEMY_SIZE, Constants.ENEMY_SIZE, Color.RED);
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);

        frames = Constants.GHOST_IDLE[0];
        animation.setFrames(frames);
        animation.setDelay(10);

        canvas = new Canvas(96,96);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(rectangle.getTranslateX());
        canvas.setTranslateY(rectangle.getTranslateY());
    }


    @Override
    public void update() {
        if(isDead && !deathAnimationSet){
            frames = Constants.GHOST_DEATH[0];
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
                sprite = ImageScaler.nearestNeighborScale(sprite);
                gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
            }
        }
    }

    public void removeFromGame() {
        Main.getInstance().removeEnemy(this);
    }

    @Override
    public Node node() {
        return canvas;
    }

    @Override
    public Node hitBox() {
        return rectangle;
    }

    @Override
    public Constants.EntityType getType() {
        return Constants.EntityType.ENEMY;
    }
}