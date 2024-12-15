package com.example.platformerplain.entities.tile;
import com.example.platformerplain.Constants;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.texture.CutSpriteSheet;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Decoration extends Tile {
    private GraphicsContext gc;

    public Decoration(int x, int y, int w, int h) { //todo: remove useless parameters
        Rectangle hitBox = new Rectangle(w, h, Color.DARKBLUE);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        canvas = new Canvas(Constants.TILE_SIZE,Constants.TILE_SIZE);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
        Image sprite = CutSpriteSheet.getSprite(EntityType.DECORATION, (int) (Math.random() * 5), (int) (Math.random() * 7));
        sprite = ImageScaler.nearestNeighborScale(sprite,5); //todo: move to assets
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());

        this.hitBox = hitBox;

    }

    @Override
    public EntityType getType() {
        return EntityType.DECORATION;
    }
}