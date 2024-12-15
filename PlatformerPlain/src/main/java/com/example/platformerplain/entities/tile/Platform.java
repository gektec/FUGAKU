package com.example.platformerplain.entities.tile;
import com.example.platformerplain.AssetManager;
import com.example.platformerplain.Constants;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.model.GameModel;
import com.example.platformerplain.texture.CutSpriteSheet;
import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform extends Tile {
    private GraphicsContext gc;
    private Image sprite;

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
        if(GameModel.getCurrentLevel() == 3 ) sprite = CutSpriteSheet.getSprite(EntityType.PLATFORM, AssetManager.getPlatformPosition(index)[0], AssetManager.getPlatformPosition(index)[1] + 5);
        else sprite = CutSpriteSheet.getSprite(EntityType.PLATFORM, AssetManager.getPlatformPosition(index)[0], AssetManager.getPlatformPosition(index)[1]);
        sprite = ImageScaler.nearestNeighborScale(sprite,5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());

        this.hitBox = rectangle;

    }

    @Override
    public EntityType getType() {
        return EntityType.PLATFORM;
    }
}