package com.example.platformerplain;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

public class Move {
    private Entity player;
    private ArrayList<Entity> entityMap;
    private final int gravity = 1;
    private boolean canJump;
    private int levelWidth;
    private HashMap<KeyCode, Boolean> keys;
    private final int maxFallSpeed = 20;
    private Coord2D playerVelocity;

    public Move(Entity player, ArrayList<Entity> platforms, int levelWidth, HashMap<KeyCode, Boolean> keys) {
        this.player = player;
        this.entityMap = platforms;
        this.levelWidth = levelWidth;
        this.keys = keys;
        this.playerVelocity = new Coord2D(0, 0);
        this.canJump = true;
    }

    public void update() {
        if (isPressed(KeyCode.W) && player.node().getTranslateY() >= 5) {
            jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.node().getTranslateX() >= 5) {
            movePlayerX(-5);
        }
        if (isPressed(KeyCode.D) && player.node().getTranslateX() + 40 <= levelWidth - 5) {
            movePlayerX(5);
        }
        if (playerVelocity.getY() < maxFallSpeed) {
            playerVelocity.add(0, gravity);
        }
        movePlayerY((int) playerVelocity.getY());
        checkGoalCollision();
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private void movePlayerX(int speed) {
        boolean movingRight = speed > 0;
        for (int i = 0; i < Math.abs(speed); i++) {
            for (Entity platform : entityMap) {
                if (player.node().getBoundsInParent().intersects(platform.node().getBoundsInParent())) {
                    if (player.node().getTranslateY() >= platform.node().getTranslateY() - 39 && player.node().getTranslateY() <= platform.node().getTranslateY() + 59) {
                        if (movingRight) {
                            if (player.node().getTranslateX() + 40 == platform.node().getTranslateX()) {
                                return;
                            }
                        } else {
                            if (player.node().getTranslateX() == platform.node().getTranslateX() + 60) {
                                return;
                            }
                        }
                    }
                }
            }
            player.node().setTranslateX(player.node().getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void movePlayerY(int speed) {
        boolean movingDown = speed > 0;
        for (int i = 0; i < Math.abs(speed); i++) {
            for (Entity platform : entityMap) {
                if (player.node().getBoundsInParent().intersects(platform.node().getBoundsInParent())) {
                    if (movingDown) {
                        if (player.node().getTranslateY() + 40 == platform.node().getTranslateY()) { // Touch ground
                            canJump = true;
                            playerVelocity.setY(0);
                            return;
                        }
                    } else {
                        if (player.node().getTranslateY() == platform.node().getTranslateY() + 60) { // Touch ceiling
                            playerVelocity.setY(0);
                            return;
                        }
                    }
                }
            }
            player.node().setTranslateY(player.node().getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    private void jumpPlayer() {
        if (canJump) {
            playerVelocity.add(0, -20);
            canJump = false;
        }
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