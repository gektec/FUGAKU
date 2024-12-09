package com.example.platformerplain.entities;
import com.example.platformerplain.Assets;
import com.example.platformerplain.Constants;

import com.example.platformerplain.move.MoveState;
import com.example.platformerplain.Main;
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
    private MoveState lastState = MoveState.IDLE;

    public Player(int x, int y, int w, int h) {
        hitBox = new Rectangle(w, h, Color.BLUE);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        frames = Assets.PLAYER_IDLE[0];
        animation.setFrames(frames);
        animation.setDelay(10);
        canvas = new Canvas(192,192);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(hitBox.getTranslateX());
        canvas.setTranslateY(hitBox.getTranslateY());
    }

    @Override
    public void update() {
        Main.getInstance().movePlayerLogic.update();
        canvas.setTranslateY(hitBox.getTranslateY()-130);
        switch (Main.getInstance().movePlayerLogic.getPlayerState()) {
            case MoveState.IDLE:
                if (lastState != MoveState.IDLE) {
                    frames = Assets.PLAYER_IDLE[0];
                    animation.setFrames(frames);
                    animation.setDelay(10);
                    lastState = MoveState.IDLE;
                }
                break;
            case MoveState.SLIDING:
                if(Main.getInstance().movePlayerLogic.getMoveStatus().faceLeft) canvas.setTranslateX(canvas.getTranslateX()+20);
                else canvas.setTranslateX(canvas.getTranslateX()-20);
                if (lastState != MoveState.SLIDING) {
                    frames = Assets.PLAYER_SLIDING[0];
                    animation.setFrames(frames);
                    animation.setDelay(10);
                    lastState = MoveState.SLIDING;
                }
                break;
            case MoveState.RUNNING:
                if (lastState != MoveState.RUNNING) {
                    frames = Assets.PLAYER_RUN[0];
                    animation.setFrames(frames);
                    animation.setDelay(5);
                    lastState = MoveState.RUNNING;
                }
                break;
            case MoveState.JUMPING:
                if (lastState != MoveState.JUMPING) {
                    frames = Assets.PLAYER_JUMP_START[0];
                    animation.setFrames(frames);
                    animation.setDelay(2);
                    lastState = MoveState.JUMPING;
                }
                break;
            case MoveState.FALLING:
                if (lastState != MoveState.FALLING) {
                    frames = Assets.PLAYER_JUMP_FALL[0];
                    animation.setFrames(frames);
                    animation.setDelay(5);
                    lastState = MoveState.FALLING;
                }
                break;
        }
        animation.update();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image sprite = animation.getImage(Main.getInstance().movePlayerLogic.getMoveStatus().faceLeft);
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
