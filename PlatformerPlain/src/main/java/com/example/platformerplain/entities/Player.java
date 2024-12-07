package com.example.platformerplain.entities;
import com.example.platformerplain.Constants;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Entity {
    private Node hitBox;
    protected boolean isAnimated(){
        return true;
    }
    Image[] frames;
    private Canvas canvas;
    private GraphicsContext gc;

    public Player(int x, int y, int w, int h) {
        hitBox = new Rectangle(w, h, Color.BLUE);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        frames = Constants.PLAYER_IDLE[0];
        animation.setFrames(frames);
        animation.setDelay(10);
        canvas = new Canvas(192,192);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(hitBox.getTranslateX());
        canvas.setTranslateY(hitBox.getTranslateY());
    }

    @Override
    public void update() {
            // Update and draw
            animation.update();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            Image sprite = animation.getImage();
            if (sprite != null) {
                gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
            }
    }

    @Override
    public int[] size() {
        return new int[]{Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT};
    }


    @Override
    public Node hitBox() {
        return hitBox;
    }

    @Override
    public Node canvas() {
        return canvas;
    }

    @Override
    public Constants.EntityType getType() {
        return Constants.EntityType.PLAYER;
    }
}
