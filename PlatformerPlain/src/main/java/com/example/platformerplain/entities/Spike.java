package com.example.platformerplain.entities;

import com.example.platformerplain.AssetManager;
import com.example.platformerplain.Constants;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.Move;
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
        hitBox.setTranslateX(x - ( w - Constants.TILE_SIZE) / 2);
        hitBox.setTranslateY(y - ( h - Constants.TILE_SIZE) / 2);
        canvas = new Canvas(Constants.TILE_SIZE, Constants.TILE_SIZE);
        if(index>=16) {
            this.hitBox = hitBox;
            Move.centerAlign(this, new Coord2D(0,0));
        }else this.hitBox = new Rectangle(-1000,0,0,0);

        gc = canvas.getGraphicsContext2D();
        Image sprite = CutSpriteSheet.getSprite(EntityType.SPIKE, AssetManager.getSpikePosition(index)[0], AssetManager.getSpikePosition(index)[1]);
        sprite = ImageScaler.nearestNeighborScale(sprite,5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void playerDead(){
        Image sprite = CutSpriteSheet.getSprite(EntityType.SPIKE, AssetManager.getSpikePosition(index)[0] - 4, AssetManager.getSpikePosition(index)[1]);
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
    public EntityType getType() {
        return EntityType.SPIKE;
    }
}
