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

public class Platform extends Entity {
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
        Image sprite = CutSpriteSheet.getSprite(EntityType.PLATFORM, Assets.getPlatformPosition(index)[0], Assets.getPlatformPosition(index)[1]);
        sprite = ImageScaler.nearestNeighborScale(sprite,5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());

        this.hitBox = rectangle;

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
        return EntityType.PLATFORM;
    }
}