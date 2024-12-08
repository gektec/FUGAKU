package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.entities.Entity;

import java.util.ArrayList;

public class Move {
    private static ArrayList<Entity> collidableMap;

    public Move(ArrayList<Entity> collidableMap) {
        Move.collidableMap = collidableMap;
    }

    public static int detectRelativeLocation(Entity moveable, Entity collidable) {
        Coord2D moveableCoord = new Coord2D((int) moveable.hitBox().getTranslateX() + (moveable.size()[0] / 2), (int) moveable.hitBox().getTranslateY() + (moveable.size()[1] / 2));
        Coord2D platformCoord = new Coord2D((int) collidable.hitBox().getTranslateX() + (Constants.TILE_SIZE / 2), (int) collidable.hitBox().getTranslateY() + (Constants.TILE_SIZE / 2));

        int xDiff = moveableCoord.getX() - platformCoord.getX();
        int yDiff = moveableCoord.getY() - platformCoord.getY();

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
            for (Entity platform : collidableMap) {
                //Next line may not improve performance
                //if (Math.max(Math.abs(moveable.hitBox().getTranslateX() - platform.hitBox().getTranslateX()), Math.abs(moveable.hitBox().getTranslateY() - platform.hitBox().getTranslateY())) <= moveable.size() + platform.size() + 1) {
                    if (moveable.hitBox().getBoundsInParent().intersects(platform.hitBox().getBoundsInParent())) {
                        int relativeLocation = detectRelativeLocation(moveable, platform);
                        if (relativeLocation == 1) {
                            isTouchingGround = true;
                            moveable.hitBox().setTranslateY(platform.hitBox().getTranslateY() - moveable.size()[1]);
                            velocity.setY(0);
                            if(velocity.getX() != 0) moveStatus.moveState = MoveState.RUNNING;
                            else moveStatus.moveState = MoveState.IDLE;
                            moveStatus.canJump = true;
                            moveStatus.canDash = true;
                        } else if (relativeLocation == 2) {
                            isTouchingWall = true;
                            moveable.hitBox().setTranslateX(platform.hitBox().getTranslateX() - moveable.size()[0]);
                            velocity.setX(0);
                            if (moveStatus.moveState == MoveState.SLIDE_JUMPING) {
                                velocity.setX(-Constants.SLIDE_JUMP_SPEED);
                                moveable.hitBox().setTranslateX(moveable.hitBox().getTranslateX() - Constants.SLIDE_JUMP_SPEED);
                            } else if (velocity.getY() > Constants.SLIDE_WALL_SPEED) {
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
                            moveStatus.moveState = MoveState.IDLE;
                        }
                    }
                }
                moveStatus.canSlideJump = !isTouchingGround && isTouchingWall;
                if (!isTouchingWall && moveStatus.moveState == MoveState.SLIDING) {
                    moveStatus.moveState = MoveState.IDLE;
                }
                if (isTouchingGround && isTouchingWall) {
                    moveStatus.moveState = MoveState.IDLE;
                }
            }
        //}
        moveable.canvas().setTranslateX(moveable.hitBox().getTranslateX() + moveable.hitBox().getBoundsInParent().getWidth() / 2 - moveable.canvas().getBoundsInParent().getWidth() / 2);
        moveable.canvas().setTranslateY(moveable.hitBox().getTranslateY() + moveable.hitBox().getBoundsInParent().getHeight() / 2 - moveable.canvas().getBoundsInParent().getHeight() / 2);
    }
}