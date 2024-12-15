package com.example.platformerplain.entities.moveable;
import com.example.platformerplain.Assets;
import com.example.platformerplain.Constants;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.view.GameScreen;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.MoveState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Player extends Moveable {
    Image[] frames;
    private GraphicsContext gc;
    private MoveState lastState = MoveState.IDLE;
    private int lastAfterimageFrame = 0;

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
        GameModel.getMovePlayerLogic().update();
        canvas.setTranslateY(hitBox.getTranslateY()-130);
        switch (GameModel.getMovePlayerLogic().getPlayerState()) {
            case MoveState.IDLE:
                if (lastState != MoveState.IDLE) {
                    frames = Assets.PLAYER_IDLE[0];
                    animation.setFrames(frames);
                    animation.setDelay(10);
                    lastState = MoveState.IDLE;
                }
                break;
            case MoveState.SLIDING:
                if(GameModel.getMovePlayerLogic().getMoveStatus().isFacingLeft) canvas.setTranslateX(canvas.getTranslateX()+20);
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
            case MoveState.DASHING:
                lastAfterimageFrame++;
                if (lastAfterimageFrame > 1) {
                    lastAfterimageFrame = 0;
                    generateAfterimage();
                }
                if (lastState != MoveState.DASHING && GameModel.getMovePlayerLogic().getMoveStatus().velocity.getX() != 0) {
                    frames = Assets.PLAYER_DASH[0];
                    animation.setFrames(frames);
                    animation.setDelay(3);
                    lastState = MoveState.DASHING;
                }
                break;
        }
        animation.update();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image sprite = animation.getImage(GameModel.getMovePlayerLogic().getMoveStatus().isFacingLeft);
        if (sprite != null) {
            gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    private void generateAfterimage() {
        ImageView afterimage = new ImageView(animation.getImage(GameModel.getMovePlayerLogic().getMoveStatus().isFacingLeft));
        afterimage.setFitWidth(192);
        afterimage.setFitHeight(192);
        afterimage.setTranslateX(canvas.getTranslateX());
        afterimage.setTranslateY(canvas.getTranslateY());
        afterimage.setOpacity(0.5);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        afterimage.setEffect(colorAdjust);

        GameScreen.getGameRoot().getChildren().add(afterimage);

        Timeline fadeOut = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> GameScreen.getGameRoot().getChildren().remove(afterimage)));
        fadeOut.play();
    }

    @Override
    public int[] size() {
        return new int[]{Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT};
    }


    @Override
    public EntityType getType() {
        return EntityType.PLAYER;
    }
}
