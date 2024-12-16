package com.example.platformerplain.entities.tile;

import com.example.platformerplain.data.Assets;
import com.example.platformerplain.data.Constants;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.Move;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a collectible coin.
 * The coin can be collected by the player, which removes it from the game.
 *
 * @author Changyu Li
 * @date 2024/12/15
 */
public class Coin extends Tile {
    private final GraphicsContext gc;
    private final Image sprite;
    public boolean isCollected = false;

    /**
     *
     * @param x The x-coordinate of the coin's position.
     * @param y The y-coordinate of the coin's position.
     * @param w The width of the coin's hitbox.
     * @param h The height of the coin's hitbox.
     */
    public Coin(int x, int y, int w, int h) {
        hitBox = new Rectangle(w, h, Color.GOLD);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);
        canvas = new Canvas(40, 40);
        gc = canvas.getGraphicsContext2D();
        Move.centerAlign(this,new Coord2D(0,0));

        sprite = ImageScaler.nearestNeighborScale(Assets.COIN,3);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Updates the coin's state. If the coin has been collected, it removes itself from the game model.
     */
    @Override
    public void update() {
        if(isCollected){
            GameModel.removeEntity(this);
            GameModel.collectedCoin();
        }
    }

    /**
     * Returns the size of the coin.
     *
     * @return An array containing the width and height of the coin.
     */
    @Override
    public int[] size() {
        return new int[]{Constants.COIN_SIZE, Constants.COIN_SIZE};
    }

    /**
     * Returns the type of the entity, which is COIN.
     *
     * @return The entity type of this coin.
     */
    @Override
    public EntityType getType() {
        return EntityType.COIN;
    }
}
