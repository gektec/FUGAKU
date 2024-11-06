// src/com/example/platformerplain/Move.java
package com.example.platformerplain;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

public class Move {
    private Node player;
    private ArrayList<Node> platforms;
    private final int gravity = 1;
    private boolean canJump;
    private int levelWidth;
    private HashMap<KeyCode, Boolean> keys;
    private final int maxFallSpeed = 20;
    private Vector2D playerVelocity;

    public Move(Node player, ArrayList<Node> platforms, int levelWidth, HashMap<KeyCode, Boolean> keys) {
        this.player = player;
        this.platforms = platforms;
        this.levelWidth = levelWidth;
        this.keys = keys;
        this.playerVelocity = new Vector2D(0, 0);
        this.canJump = true;
    }

    public void update() {
        if (isPressed(KeyCode.W) && player.getTranslateY() >= 5) {
            jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.getTranslateX() >= 5) {
            movePlayerX(-5);
        }
        if (isPressed(KeyCode.D) && player.getTranslateX() + 40 <= levelWidth - 5) {
            movePlayerX(5);
        }
        if (playerVelocity.getY() < maxFallSpeed) {
            playerVelocity.add(0, gravity);
        }
        movePlayerY((int) playerVelocity.getY());
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private void movePlayerX(int speed) {
        boolean movingRight = speed > 0;
        for (int i = 0; i < Math.abs(speed); i++) {
            for (Node platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (player.getTranslateY() >= platform.getTranslateY() - 39 && player.getTranslateY() <= platform.getTranslateY() + 59) {
                        if (movingRight) {
                            if (player.getTranslateX() + 40 == platform.getTranslateX()) {
                                return;
                            }
                        } else {
                            if (player.getTranslateX() == platform.getTranslateX() + 60) {
                                return;
                            }
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void movePlayerY(int speed) {
        boolean movingDown = speed > 0;
        for (int i = 0; i < Math.abs(speed); i++) {
            for (Node platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (player.getTranslateY() + 40 == platform.getTranslateY()) {
                            canJump = true;
                            playerVelocity.setY(0);
                            return;
                        }
                    } else {
                        if (player.getTranslateY() == platform.getTranslateY() + 60) {
                            playerVelocity.setY(0);
                            return;
                        }
                    }
                }
            }
            player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    private void jumpPlayer() {
        if (canJump) {
            playerVelocity.add(0, -20);
            canJump = false;
        }
    }
}
