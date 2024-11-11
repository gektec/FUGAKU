package com.example.platformerplain;

import java.util.ArrayList;

public class Move {
    private static ArrayList<Entity> entityMap;

    public Move(ArrayList<Entity> entityMap) {
        Move.entityMap = entityMap;
    }
    public static int detectRelativeLocation(Entity player, Entity platform) {
        Coord2D playerCoord = new Coord2D((int)player.node().getTranslateX() + (Main.PLAYER_SIZE/2), (int)player.node().getTranslateY() + (Main.PLAYER_SIZE/2));
        Coord2D platformCoord = new Coord2D((int)platform.node().getTranslateX() + (Main.TILE_SIZE/2), (int)platform.node().getTranslateY() + (Main.TILE_SIZE/2));

        int xDiff = playerCoord.getX() - platformCoord.getX();
        int yDiff = playerCoord.getY() - platformCoord.getY();

        if (yDiff < xDiff && yDiff < -xDiff) {
            return 1;
        } else if (yDiff > xDiff && yDiff > -xDiff) {
            return 3;
        } else if (xDiff < 0) {
            return 2;
        } else {
            return 4;
        }
//            1
//        2       4
//            3

    }

//todo: restrict max speed to avoid clipping through walls
    public static boolean movePlayer(Entity player, Coord2D playerVelocity) {
        boolean canJump = false;
        int moveX = Math.abs(playerVelocity.getX());
        int moveY = Math.abs(playerVelocity.getY());
//        int movingDownUp = playerVelocity.getY() > 0 ? 1 : -1;
//        int movingRightLeft = playerVelocity.getX() > 0 ? 1 : -1;
        if (Math.max(moveX, moveY) > 0) {
            player.node().setTranslateX(player.node().getTranslateX() + playerVelocity.getX());
            player.node().setTranslateY(player.node().getTranslateY() + playerVelocity.getY());
            for (Entity platform : entityMap) {
                if (player.node().getBoundsInParent().intersects(platform.node().getBoundsInParent())) {
                    int relativeLocation = detectRelativeLocation(player, platform);
                    if (relativeLocation == 1) {
                        player.node().setTranslateY(platform.node().getTranslateY() - Main.PLAYER_SIZE);
                        playerVelocity.setY(0);
                        canJump = true;
                    } else if (relativeLocation == 2) {
                        player.node().setTranslateX(platform.node().getTranslateX() - Main.PLAYER_SIZE);
                        playerVelocity.setX(0);
                    } else if (relativeLocation == 3) {
                        player.node().setTranslateY(platform.node().getTranslateY() + Main.TILE_SIZE);
                        playerVelocity.setY(0);
                    } else if (relativeLocation == 4) {
                        player.node().setTranslateX(platform.node().getTranslateX() + Main.TILE_SIZE);
                        playerVelocity.setX(0);
                    }
                }
            }
        }
        return canJump;
    }
}
