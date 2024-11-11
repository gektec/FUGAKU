package com.example.platformerplain;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.platformerplain.Move;

public class MovePlayer {
    private Entity player;
    private ArrayList<Entity> entityMap;
    private final int gravity = -1;
    private boolean canJump;
    private int levelWidth;
    private HashMap<KeyCode, Boolean> keys;
    private final int maxFallSpeed = -20;
    private final int resistance = 1;
    private Coord2D playerVelocity;

    public MovePlayer(Entity player, ArrayList<Entity> platforms, int levelWidth, HashMap<KeyCode, Boolean> keys) {
        this.player = player;
        this.entityMap = platforms;
        this.levelWidth = levelWidth;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.canJump = true;
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public void update() {
        if (isPressed(KeyCode.W) && canJump) {
            playerVelocity.add(0, 20);
        }
        if (isPressed(KeyCode.A) && playerVelocity.getX() >= -10) {
            playerVelocity.add(-5, 0);  //max speed: -15
        }
        else if (isPressed(KeyCode.D) && playerVelocity.getX() <= 10) {
            playerVelocity.add(5, 0);
        }
        else {
            playerVelocity.reduce(resistance, 0);
        }
        if (playerVelocity.getY() > maxFallSpeed) {
            playerVelocity.add(0, gravity);
        }
        Move.movePlayerX(player, playerVelocity);
        canJump = Move.movePlayerY(player, playerVelocity, canJump);
        checkGoalCollision();
    }




    private void checkGoalCollision() {
        for (Entity entity : entityMap) {
            if (entity.getType() == EntityType.GOAL && player.node().getBoundsInParent().intersects(entity.node().getBoundsInParent())) {
                System.out.println("You win!");
                System.exit(0);
            }
        }
    }
}