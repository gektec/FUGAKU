package com.example.platformerplain.move;

import com.example.platformerplain.Constants;
import com.example.platformerplain.LevelData;
import com.example.platformerplain.entities.Entity;

import java.util.ArrayList;


//the move class is responsible for moving the player and detecting collisions
//it is also responsible for changing the player's state based on the collision
//todo: it should input player and command, return moveState and adjancy status

public class Move {
    private static ArrayList<Entity> collidableMap;
    private static Coord2D velocity;
    private static boolean isTouchingGround = false;
    private static boolean isTouchingLeftWall = false;
    private static boolean isTouchingRightWall = false;

    public Move(ArrayList<Entity> collidableMap) {
        Move.collidableMap = collidableMap;
    }

    private static int detectRelativeLocation(Entity moveable, Entity collidable) {
        Coord2D moveableCoord = new Coord2D((int) moveable.hitBox().getTranslateX() + (moveable.size()[0] / 2), (int) moveable.hitBox().getTranslateY() + (moveable.size()[1] / 2));
        Coord2D platformCoord = new Coord2D((int) collidable.hitBox().getTranslateX() + (Constants.TILE_SIZE / 2), (int) collidable.hitBox().getTranslateY() + (Constants.TILE_SIZE / 2));

        float xDiff = moveableCoord.getX() - platformCoord.getX();
        float yDiff = moveableCoord.getY() - platformCoord.getY();

        //test
        int totalSize = moveable.size()[0] + collidable.size()[0];

        if (Math.abs(xDiff) > totalSize/2 - 3 && Math.abs(yDiff) > totalSize/2 - 3) {
            return -1;
        }
        //

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

    public static MoveStatus move(Entity moveable, MoveStatus moveStatus){
        beforeMove(moveable,moveStatus);
        afterMove(moveable,moveStatus);
        setStatus(moveable,moveStatus);
        return moveStatus;
    }

    private static void beforeMove(Entity moveable, MoveStatus moveStatus){
        isTouchingGround = false;
        isTouchingLeftWall = false;
        isTouchingRightWall = false;
        velocity = moveStatus.velocity;
        for(Entity platform : collidableMap){
            if(moveable.hitBox().getBoundsInParent().intersects(platform.hitBox().getBoundsInParent())){
                int relativeLocation = detectRelativeLocation(moveable, platform);
                if(relativeLocation == 1){
                    isTouchingGround = true;
                }
                else if(relativeLocation == 2){
                    isTouchingRightWall = true;
                }
                else if(relativeLocation == 4){
                    isTouchingLeftWall = true;
                }
            }
        }
        if(isTouchingGround && velocity.getY() > 0)
            velocity.setY(0);
        if(isTouchingLeftWall && velocity.getX() < 0)
            velocity.setX(0);
        if(isTouchingRightWall && velocity.getX() > 0)
            velocity.setX(0);
        if((moveable.hitBox().getTranslateX() <= 0 && velocity.getX() < 0 ) || ( moveable.hitBox().getTranslateX() >= LevelData.getLevelInformation.getLevelWidth() - moveable.size()[0] && velocity.getX() > 0 ))
            velocity.setX(0); // Touching boundary
    }


    private static void afterMove(Entity moveable, MoveStatus moveStatus) {
        velocity = moveStatus.velocity;
        if (velocity.getY() != 0 || velocity.getX() != 0) {
            moveable.hitBox().setTranslateX(moveable.hitBox().getTranslateX() + velocity.getX());
            moveable.hitBox().setTranslateY(moveable.hitBox().getTranslateY() + velocity.getY());
            if (velocity.getX() > 0) moveStatus.isFacingLeft = false;
            else if (velocity.getX() < 0) moveStatus.isFacingLeft = true;
            for (Entity platform : collidableMap) {
                //Next line may not improve performance
                //if (Math.max(Math.abs(moveable.hitBox().getTranslateX() - platform.hitBox().getTranslateX()), Math.abs(moveable.hitBox().getTranslateY() - platform.hitBox().getTranslateY())) <= moveable.size() + platform.size() + 1) {
                if (moveable.hitBox().getBoundsInParent().intersects(platform.hitBox().getBoundsInParent())) {
                    int relativeLocation = detectRelativeLocation(moveable, platform);
                    if (relativeLocation == 1) {
                        isTouchingGround = true;
                        moveable.hitBox().setTranslateY(platform.hitBox().getTranslateY() - moveable.size()[1]);
                        velocity.setY(0);
                        if (moveStatus.moveState != MoveState.DASHING) {
                            if (velocity.getX() != 0) moveStatus.moveState = MoveState.RUNNING;
                            else moveStatus.moveState = MoveState.IDLE;
                        }
                        moveStatus.isTouchingGround = true;
                    } else if (relativeLocation == 2) {
                        isTouchingRightWall = true;
                        moveable.hitBox().setTranslateX(platform.hitBox().getTranslateX() - moveable.size()[0]);
                        velocity.setX(0);
                        if (moveStatus.moveState == MoveState.SLIDE_JUMPING) {
                            velocity.setX(-Constants.SLIDE_JUMP_SPEED);
                            moveable.hitBox().setTranslateX(moveable.hitBox().getTranslateX() - Constants.SLIDE_JUMP_SPEED);
                        } else if (velocity.getY() > Constants.SLIDE_WALL_SPEED) {
                            moveStatus.isFacingLeft = true;
                            moveStatus.moveState = MoveState.SLIDING;
                            velocity.setY(Constants.SLIDE_WALL_SPEED);
                        }
                    } else if (relativeLocation == 4) {
                        isTouchingLeftWall = true;
                        moveable.hitBox().setTranslateX(platform.hitBox().getTranslateX() + Constants.TILE_SIZE);
                        velocity.setX(0);
                        if (moveStatus.moveState == MoveState.SLIDE_JUMPING) {
                            velocity.setX(Constants.SLIDE_JUMP_SPEED);
                            moveable.hitBox().setTranslateX(moveable.hitBox().getTranslateX() + Constants.SLIDE_JUMP_SPEED);
                        } else if (velocity.getY() > Constants.SLIDE_WALL_SPEED) {
                            moveStatus.isFacingLeft = false;
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
        }
    }

    private static void setStatus(Entity moveable, MoveStatus moveStatus) {
            boolean isTouchingWall = isTouchingLeftWall || isTouchingRightWall;
            moveStatus.isTouchingWall = isTouchingWall;

//            if (!isTouchingWall && moveStatus.moveState == MoveState.SLIDING) {
//                moveStatus.moveState = MoveState.IDLE;
//            }
//            if (isTouchingGround && isTouchingWall) {
//                if (moveStatus.moveState == MoveState.SLIDING) {
//                    moveStatus.moveState = MoveState.IDLE;
//                }
//            }
//            if (!isTouchingWall && velocity.getY() > 0 && (moveStatus.moveState == MoveState.IDLE || moveStatus.moveState == MoveState.JUMPING || moveStatus.moveState == MoveState.RUNNING)) {
//                moveStatus.moveState = MoveState.FALLING;
//            }
//            else if(!isTouchingWall && velocity.getY() < 0 && (moveStatus.moveState == MoveState.IDLE || moveStatus.moveState == MoveState.RUNNING)) {
//                moveStatus.moveState = MoveState.JUMPING;
//            }
        // Set canvas position
        moveable.canvas().setTranslateX(moveable.hitBox().getTranslateX() + moveable.hitBox().getBoundsInParent().getWidth() / 2 - moveable.canvas().getBoundsInParent().getWidth() / 2);
        moveable.canvas().setTranslateY(moveable.hitBox().getTranslateY() + moveable.hitBox().getBoundsInParent().getHeight() / 2 - moveable.canvas().getBoundsInParent().getHeight() / 2);
    }
}