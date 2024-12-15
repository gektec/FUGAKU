package com.example.platformerplain.entities.tile;

import com.example.platformerplain.Assets;
import com.example.platformerplain.Constants;
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

public class Coin extends Tile {
    private GraphicsContext gc;
    private Image sprite;
    public boolean isCollected = false;


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

    @Override
    public void update() {
        if(isCollected){
            GameModel.removeEntity(this);
            GameModel.collectedCoin();
        }
    }

    @Override
    public int[] size() {
        return new int[]{Constants.COIN_SIZE, Constants.COIN_SIZE};
    }

    @Override
    public EntityType getType() {
        return EntityType.COIN;
    }
}
