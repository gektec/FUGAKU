package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.Entity;

import java.util.ArrayList;

public class Move {
    private static ArrayList<Entity> entityMap;

    public Move(ArrayList<Entity> entityMap) {
        Move.entityMap = entityMap;
    }

    public static int detectRelativeLocation(Entity moveable, Entity collidable) {
        Coord2D playerCoord = new Coord2D((int) moveable.hitBox().getTranslateX() + (Constants.PLAYER_SIZE / 2), (int) moveable.hitBox().getTranslateY() + (Constants.PLAYER_SIZE / 2));
        Coord2D platformCoord = new Coord2D((int) collidable.hitBox().getTranslateX() + (Constants.TILE_SIZE / 2), (int) collidable.hitBox().getTranslateY() + (Constants.TILE_SIZE / 2));

        int xDiff = playerCoord.getX() - platformCoord.getX();
        int yDiff = playerCoord.getY() - platformCoord.getY();

        if (xDiff == yDiff || xDiff == -yDiff) {    //Diagonal collision
            return -1;
        } else if (yDiff < xDiff && yDiff < -xDiff) {
            return 1;
        } else if (yDiff > xDiff && yDiff > -xDiff) {
            return 3;
        } else if (xDiff < 0) {
            return 2;
        } else {
            return 4;
        }
//             1
//        2 PLATFORM 4
//             3

    }

    //todo: restrict max speed to avoid clipping through walls
    public static void move(Entity moveable, Coord2D velocity, MoveStatus moveStatus) {
        boolean isTouchingGround = false;
        boolean isTouchingWall = false;
        moveStatus.canJump = false;
        if (velocity.getY() != 0 || velocity.getX() != 0) {
            moveable.hitBox().setTranslateX(moveable.hitBox().getTranslateX() + velocity.getX());
            moveable.hitBox().setTranslateY(moveable.hitBox().getTranslateY() + velocity.getY());
            for (Entity platform : entityMap) {
                if (moveable.hitBox().getBoundsInParent().intersects(platform.hitBox().getBoundsInParent())) {
                    int relativeLocation = detectRelativeLocation(moveable, platform);
                    if (relativeLocation == 1) {
                        isTouchingGround = true;
                        moveable.hitBox().setTranslateY(platform.hitBox().getTranslateY() - Constants.PLAYER_SIZE);
                        velocity.setY(0);
                        moveStatus.canJump = true;
                        moveStatus.canDash = true;
                    } else if (relativeLocation == 2) {
                        isTouchingWall = true;
                        moveable.hitBox().setTranslateX(platform.hitBox().getTranslateX() - Constants.PLAYER_SIZE);
                        velocity.setX(0);
                        if (moveStatus.moveState == MoveState.SLIDE_JUMPING) {
                            velocity.setX(-Constants.SLIDE_JUMP_SPEED);
                            moveable.hitBox().setTranslateX(moveable.hitBox().getTranslateX() - Constants.SLIDE_JUMP_SPEED);
                            }
                        else if (velocity.getY() > Constants.SLIDE_WALL_SPEED) {
                            moveStatus.moveState = MoveState.SLIDING;
                            velocity.setY(Constants.SLIDE_WALL_SPEED);
                        }
                    } else if (relativeLocation == 4) {
                        isTouchingWall = true;
                        moveable.hitBox().setTranslateX(platform.hitBox().getTranslateX() + Constants.TILE_SIZE);
                        velocity.setX(0);
                        if (moveStatus.moveState == MoveState.SLIDE_JUMPING) {
                            velocity.setX(Constants.SLIDE_JUMP_SPEED);
                            moveable.hitBox().setTranslateX(moveable.hitBox().getTranslateX() + Constants.SLIDE_JUMP_SPEED);
                        } else if (velocity.getY() > Constants.SLIDE_WALL_SPEED) {
                            moveStatus.moveState = MoveState.SLIDING;
                            velocity.setY(Constants.SLIDE_WALL_SPEED);
                        }
                    } else if (relativeLocation == 3) {
                        moveable.hitBox().setTranslateY(platform.hitBox().getTranslateY() + Constants.TILE_SIZE);
                        velocity.setY(0);
                        moveStatus.moveState = MoveState.DEFAULT;

                    }
                }
            }
            moveStatus.canSlideJump = !isTouchingGround && isTouchingWall;
            if(!isTouchingWall && moveStatus.moveState == MoveState.SLIDING){
                moveStatus.moveState = MoveState.DEFAULT;
            }
            if(isTouchingGround && isTouchingWall){
                moveStatus.moveState = MoveState.DEFAULT;
            }
        }
    }
}