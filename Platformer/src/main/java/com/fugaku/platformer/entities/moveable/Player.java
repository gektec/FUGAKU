package com.fugaku.platformer.entities.moveable;

import com.fugaku.platformer.data.Assets;
import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.entities.EntityType;
import com.fugaku.platformer.move.MovePlayer;
import com.fugaku.platformer.move.state.MoveData;
import com.fugaku.platformer.view.GameScreen;
import com.fugaku.platformer.model.GameModel;
import com.fugaku.platformer.move.state.MoveState;
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

/**
 * Represents a player character in the game.
 * The player can move and perform actions, with animations based on their movement state.
 *
 * @author Changyu Li
 * @date 2024/11/8
 */
public class Player extends Moveable {
    private final GraphicsContext gc;
    private MoveState lastState = MoveState.IDLE;

    /**
     * Constructs a Player object at specified coordinates and dimensions.
     *
     * @param x the x-coordinate of the player's position
     * @param y the y-coordinate of the player's position
     * @param w the width of the player's hitbox
     * @param h the height of the player's hitbox
     */
    public Player(int x, int y, int w, int h) {
        hitBox = new Rectangle(w, h, Color.BLUE);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        animation.setFrames(Assets.PLAYER_IDLE[0]);
        animation.setDelay(10);
        canvas = new Canvas(192, 192);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(hitBox.getTranslateX());
        canvas.setTranslateY(hitBox.getTranslateY());
    }

    /**
     * Updates the player's state and redraws the player on the canvas.
     */
    @Override
    public void update() {
        GameModel.getMovePlayerLogic().update();
        canvas.setTranslateY(hitBox.getTranslateY() - 130);

        MovePlayer.getMoveData().drawPlayer(this, lastState, animation);

        animation.update();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image sprite = animation.getImage(MovePlayer.getMoveData().isFacingLeft);
        if (sprite != null) {
            gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    /**
     * Generates an afterimage effect for the player to enhance visual feedback.
     */
    public void generateAfterimage() {
        ImageView afterimage = new ImageView(animation.getImage(MovePlayer.getMoveData().isFacingLeft));
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

    /**
     * Returns the size of the player.
     *
     * @return an array containing the width and height of the player
     */
    @Override
    public int[] size() {
        return new int[]{Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT};
    }

    /**
     * Returns the type of the entity.
     *
     * @return the EntityType of the player
     */
    @Override
    public EntityType getType() {
        return EntityType.PLAYER;
    }

    /**
     * Gets the MoveData associated with the player.
     *
     * @return the MoveData object
     */
    public MoveData getMoveData() {
        return MovePlayer.getMoveData();
    }

    /**
     * Sets the last state of the player.
     *
     * @param lastState the last MoveState of the player
     */
    public void setLastState(MoveState lastState) {
        this.lastState = lastState;
    }
}
