package com.example.platformerplain.entities;
import com.example.platformerplain.Constants;

import com.example.platformerplain.texture.CutSpriteSheet;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform extends Entity {
    private Node node;

    protected boolean isAnimated(){
        return false;
    };

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
    public Constants.EntityType getType() {
        return Constants.EntityType.PLATFORM;
    }
}