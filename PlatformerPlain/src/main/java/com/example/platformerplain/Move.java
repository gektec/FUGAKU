package com.example.platformerplain;

import com.example.platformerplain.entities.Entity;

import java.util.ArrayList;

public class Move {
    private static ArrayList<Entity> entityMap;

    public Move(ArrayList<Entity> entityMap) {
        Move.entityMap = entityMap;
    }
    public static int detectRelativeLocation(Entity moveable, Entity collidable) {
        Coord2D playerCoord = new Coord2D((int)moveable.node().getTranslateX() + (Constants.PLAYER_SIZE/2), (int)moveable.node().getTranslateY() + (Constants.PLAYER_SIZE/2));
        Coord2D platformCoord = new Coord2D((int)collidable.node().getTranslateX() + (Constants.TILE_SIZE/2), (int)collidable.node().getTranslateY() + (Constants.TILE_SIZE/2));

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
    public static boolean move(Entity moveable, Coord2D velocity) {
        boolean canJump = false;
        int moveX = Math.abs(velocity.getX());
        int moveY = Math.abs(velocity.getY());
        if (Math.max(moveX, moveY) > 0) {
            moveable.node().setTranslateX(moveable.node().getTranslateX() + velocity.getX());
            moveable.node().setTranslateY(moveable.node().getTranslateY() + velocity.getY());
            for (Entity platform : entityMap) {
                if (moveable.node().getBoundsInParent().intersects(platform.node().getBoundsInParent())) {
                    int relativeLocation = detectRelativeLocation(moveable, platform);
                    if (relativeLocation == 1) {
                        moveable.node().setTranslateY(platform.node().getTranslateY() - Constants.PLAYER_SIZE);
                        velocity.setY(0);
                        canJump = true;
                    } else if (relativeLocation == 2) {
                        moveable.node().setTranslateX(platform.node().getTranslateX() - Constants.PLAYER_SIZE);
                        velocity.setX(0);
                    } else if (relativeLocation == 3) {
                        moveable.node().setTranslateY(platform.node().getTranslateY() + Constants.TILE_SIZE);
                        velocity.setY(0);
                    } else if (relativeLocation == 4) {
                        moveable.node().setTranslateX(platform.node().getTranslateX() + Constants.TILE_SIZE);
                        velocity.setX(0);
                    }
                }
            }
        }
        return canJump;
    }
}
