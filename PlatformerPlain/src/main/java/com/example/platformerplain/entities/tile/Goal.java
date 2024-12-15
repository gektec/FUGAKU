package com.example.platformerplain.entities.tile;
import com.example.platformerplain.AssetManager;

import com.example.platformerplain.Assets;
import com.example.platformerplain.entities.EntityType;
import com.example.platformerplain.model.GameModel;
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

public class Goal extends Tile {
    private GraphicsContext gc;
    private Image sprite;

    public Goal(int x, int y, int w, int h) {
        hitBox = new Rectangle(w, h, Color.GOLD);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        canvas = new Canvas(199, 240);
        gc = canvas.getGraphicsContext2D();
        Move.centerAlign(this,new Coord2D(10,-32));
        sprite = ImageScaler.nearestNeighborScale(Assets.GOAL,5);
        gc.drawImage(sprite, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public EntityType getType() {
        return EntityType.GOAL;
    }
}
