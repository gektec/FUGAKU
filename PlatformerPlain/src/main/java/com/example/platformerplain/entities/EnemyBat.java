package com.example.platformerplain.entities;

import com.example.platformerplain.entities.moveable.Enemy;
import com.example.platformerplain.move.MoveEnemy;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p>Another enemy that can fly</p>
 * @date 2024-12-15 17:05
 **/
public class EnemyBat extends Enemy {
    private Node hitBox;
    Image[] frames;
    private Canvas canvas;
    private GraphicsContext gc;
    public boolean isDead;
    private boolean deathAnimationSet = false;
    private MoveEnemy moveEnemyLogic;
    public EnemyBat(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void update(){


    }

}
