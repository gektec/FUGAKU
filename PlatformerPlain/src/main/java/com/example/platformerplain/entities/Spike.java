package com.example.platformerplain.entities;

import com.example.platformerplain.Assets;
import com.example.platformerplain.Constants;
import com.example.platformerplain.texture.CutSpriteSheet;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Spike extends Entity {
    protected boolean isAnimated(){
        return false;
    };
    private Node hitBox;
    private Canvas canvas;
    private GraphicsContext gc;
    private int index;


    public Spike(int x, int y, int w, int h, int index) {
        this.index = index;
        Rectangle hitBox = new Rectangle(w, h, Color.RED);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);
        canvas = new Canvas(w, h);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(hitBox.getTranslateX());
        canvas.setTranslateY(hitBox.getTranslateY());
        Image sprite = CutSpriteSheet.getSprite(Constants.EntityType.SPIKE, Assets.getSpikePosition(index)[0], Assets.getSpikePosition(index)[1]);
        sprite = ImageScaler.nearestNeighborScale(sprite,5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());

        if(index>=16) {
            this.hitBox = hitBox;
        }else this.hitBox = null;
    }

    public void playerDead(){
        Image sprite = CutSpriteSheet.getSprite(Constants.EntityType.SPIKE, Assets.getSpikePosition(index)[0] - 4, Assets.getSpikePosition(index)[1]);
        sprite = ImageScaler.nearestNeighborScale(sprite,5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public int[] size() {
        return new int[]{Constants.TILE_SIZE, Constants.TILE_SIZE};
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
        return Constants.EntityType.SPIKE;
    }
}
