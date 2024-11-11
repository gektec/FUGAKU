package com.example.platformerplain;

import java.util.ArrayList;

public class Move {
    private static ArrayList<Entity> entityMap;

    public Move(ArrayList<Entity> entityMap) {
        Move.entityMap = entityMap;
    }

    public static void movePlayerX(Entity player, Coord2D playerVelocity) {
        int speed = playerVelocity.getX();
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

    public static boolean movePlayerY(Entity player, Coord2D playerVelocity, boolean canJump) {
        int speed = playerVelocity.getY();
        boolean movingDown = speed < 0;
        for (int i = 0; i < Math.abs(speed); i++) {
            for (Entity platform : entityMap) {
                if (player.node().getBoundsInParent().intersects(platform.node().getBoundsInParent())) {
                    if (movingDown) {
                        if (player.node().getTranslateY() + 40 == platform.node().getTranslateY()) { // Touch ground
                            canJump = true;
                            playerVelocity.setY(0);
                            return canJump;
                        }else canJump = false;
                    } else {
                        canJump = false;
                        if (player.node().getTranslateY() == platform.node().getTranslateY() + 60) { // Touch ceiling
                            playerVelocity.setY(0);
                            return canJump;
                        }
                    }
                }
            }
            player.node().setTranslateY(player.node().getTranslateY() + (movingDown ? 1 : -1));
        }
        return canJump;
    }

}
