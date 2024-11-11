package com.example.platformerplain;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveEnemy {
    private Entity enemy;
    private ArrayList<Entity> entityMap;
    private final int gravity = 1;  //todo: inherit from move
    private boolean canJump;
    private int levelWidth;
    private HashMap<KeyCode, Boolean> keys;
    private final int maxFallSpeed = 20;
    private final int resistance = 2;
    private Coord2D velocity;


    public MoveEnemy(Entity enemy, ArrayList<Entity> platforms, int levelWidth, HashMap<KeyCode, Boolean> keys) {
        this.enemy = enemy;
        this.entityMap = platforms;
        this.levelWidth = levelWidth;
        this.keys = keys;
        this.velocity = new Coord2D(0, 0);
        this.canJump = true;
    }

    public void update() {

        velocity.reduce(resistance, 0);

        if (velocity.getY() < maxFallSpeed) {
            velocity.add(0, gravity);
        }

        canJump = Move.move(enemy, velocity);

    }
}
