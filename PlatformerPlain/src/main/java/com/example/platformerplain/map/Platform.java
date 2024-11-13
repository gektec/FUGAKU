package com.example.platformerplain.map;
import com.example.platformerplain.Entity;

import com.example.platformerplain.EntityType;
import com.example.platformerplain.texture.CutSpriteSheet;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform implements Entity {
    private Node node;

    public Platform(int x, int y, int w, int h, int index) { //todo: remove useless parameters & use Pane instead of Rectangle
        Rectangle rect = new Rectangle(w, h, Color.GREEN);
        rect.setTranslateX(x);
        rect.setTranslateY(y);

        Image sprite = CutSpriteSheet.getSpriteByIndex(index);
        rect.setFill(ImageScaler.nearestNeighborScale(sprite));
        this.node = rect;

    }
    @Override
    public Node node() {
        return node;
    }

    @Override
    public EntityType getType() {
        return EntityType.PLATFORM;
    }
}