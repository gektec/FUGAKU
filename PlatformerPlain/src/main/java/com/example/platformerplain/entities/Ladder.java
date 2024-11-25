package com.example.platformerplain.entities;
import com.example.platformerplain.Constants;

import com.example.platformerplain.texture.CutSpriteSheet;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ladder extends Entity {
    private Node node;
    private Node hitBox;
    private Canvas canvas;
    private GraphicsContext gc;
    private int index;

    protected boolean isAnimated(){
        return false;
    };

    public Ladder(int x, int y, int w, int h, int index) { //todo: remove useless parameters
        if(index == 0) {
            h = 20;  //top of ladder is shorter
        }
        Rectangle hitBox = new Rectangle(w, h, Color.PURPLE);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y+ Constants.TILE_SIZE -h);

        canvas = new Canvas(Constants.TILE_SIZE,Constants.TILE_SIZE);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
        Image sprite = CutSpriteSheet.getSprite(Constants.EntityType.LADDER,0, index);
        sprite = ImageScaler.nearestNeighborScale(sprite,5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());

        this.node = canvas;
        this.hitBox = hitBox;
        this.index = index;

    }

    @Override
    public int size() {
        return Constants.TILE_SIZE;
    }

    @Override
    public Node hitBox() {
        return hitBox;
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public Constants.EntityType getType() {
        return Constants.EntityType.PLATFORM;
    }

    public int getIndex() { //used to detect highest ladder
        return index;
    }
}