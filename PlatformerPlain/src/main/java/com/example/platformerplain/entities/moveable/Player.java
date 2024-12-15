package com.example.platformerplain.entities.moveable;
import com.example.platformerplain.Assets;
import com.example.platformerplain.Constants;

import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.move.data.MoveData;
import com.example.platformerplain.view.GameScreen;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.data.MoveState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    public Player(int x, int y, int w, int h) {
        hitBox = new Rectangle(w, h, Color.BLUE);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        animation.setFrames(Assets.PLAYER_IDLE[0]);
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

        GameModel.getMovePlayerLogic().getMoveData().updatePlayer(this, lastState, animation);

        animation.update();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image sprite = animation.getImage(GameModel.getMovePlayerLogic().getMoveData().isFacingLeft);
        if (sprite != null) {
            gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    public void generateAfterimage() {
        ImageView afterimage = new ImageView(animation.getImage(GameModel.getMovePlayerLogic().getMoveData().isFacingLeft));
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

    public MoveData getMoveData() {
        return GameModel.getMovePlayerLogic().getMoveData();
    }

    public void setLastState(MoveState lastState) {
        this.lastState = lastState;
    }
}
