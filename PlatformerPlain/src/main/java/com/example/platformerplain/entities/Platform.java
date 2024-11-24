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

public class Platform extends Entity {
    private Node node;
    private Node hitBox;
    private Canvas canvas;
    private GraphicsContext gc;

    protected boolean isAnimated(){
        return false;
    };

    public Platform(int x, int y, int w, int h, int index) { //todo: remove useless parameters & use Pane instead of Rectangle
        Rectangle rectangle = new Rectangle(w, h, Color.GREEN);
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);

        canvas = new Canvas(w, h);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(rectangle.getTranslateX());
        canvas.setTranslateY(rectangle.getTranslateY());
        Image sprite = CutSpriteSheet.getSprite(Constants.getSpritePosition(index)[0], Constants.getSpritePosition(index)[1]);
        sprite = ImageScaler.nearestNeighborScale(sprite);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());

        this.node = canvas;
        this.hitBox = rectangle;

    }
    @Override
    public Node hitBox() {
        return node;
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