package com.example.platformerplain.move;

import com.example.platformerplain.data.Constants;
import com.example.platformerplain.data.LevelData;
import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.move.state.MoveData;
import com.example.platformerplain.move.state.MoveState;

import java.util.ArrayList;

/**
 * the move class is responsible for moving the player and detecting collisions
 * it is also responsible for changing the player's state based on the collision
 *
 * @author Changyu Li
 * @date 2024/11/23
 */

public class Move {
    private static ArrayList<Entity> collidableMap;
    private static Coord2D velocity;
    private static boolean isTouchingGround = false;
    private static boolean isTouchingLeftWall = false;
    private static boolean isTouchingRightWall = false;
    private static boolean isFacingLeft = false;

    public Move(ArrayList<Entity> collidableMap) {
        Move.collidableMap = collidableMap;
    }

    /**
     * @param moveable
     * @param collidable
     * @return int
     *             1
     *        2 PLATFORM 4
     *             3
     */
    private static int detectRelativeLocation(Entity moveable, Entity collidable) {
        Coord2D moveableCoord = new Coord2D((int) moveable.hitBox().getTranslateX() + (moveable.size()[0] / 2), (int) moveable.hitBox().getTranslateY() + (moveable.size()[1] / 2));
        Coord2D platformCoord = new Coord2D((int) collidable.hitBox().getTranslateX() + (Constants.TILE_SIZE / 2), (int) collidable.hitBox().getTranslateY() + (Constants.TILE_SIZE / 2));

        double xDiff = moveableCoord.getX() - platformCoord.getX();
        double yDiff = moveableCoord.getY() - platformCoord.getY();

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


    }

    public static MoveData move(Entity moveable, MoveData moveData){
        detectCollide(moveable, moveData);
        afterMove(moveable, moveData);
        detectCollide(moveable, moveData);
        setStatus(moveable, moveData);
        centerAlign(moveable, new Coord2D(0,0));
        return moveData;
    }

    /**
     * @param moveable
     * @param moveData
     */
    private static void detectCollide(Entity moveable, MoveData moveData){
        isTouchingGround = false;
        isTouchingLeftWall = false;
        isTouchingRightWall = false;
        velocity = moveData.velocity;
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


    /**
     * @param moveable
     * @param moveData
     */
    private static void afterMove(Entity moveable, MoveData moveData) {
        velocity = moveData.velocity;
        isTouchingGround = false;
        isTouchingLeftWall = false;
        isTouchingRightWall = false;
        if (velocity.getY() != 0 || velocity.getX() != 0) {
            moveable.hitBox().setTranslateX(moveable.hitBox().getTranslateX() + velocity.getX());
            moveable.hitBox().setTranslateY(moveable.hitBox().getTranslateY() + velocity.getY());
            if (velocity.getX() > 0) moveData.isFacingLeft = false;
            else if (velocity.getX() < 0) moveData.isFacingLeft = true;
            for (Entity platform : collidableMap) {
                //Next line may not improve performance
                //if (Math.max(Math.abs(moveable.hitBox().getTranslateX() - platform.hitBox().getTranslateX()), Math.abs(moveable.hitBox().getTranslateY() - platform.hitBox().getTranslateY())) <= moveable.size() + platform.size() + 1) {
                if (moveable.hitBox().getBoundsInParent().intersects(platform.hitBox().getBoundsInParent())) {
                    int relativeLocation = detectRelativeLocation(moveable, platform);
                    if (relativeLocation == 1) {
                        moveable.hitBox().setTranslateY(platform.hitBox().getTranslateY() - moveable.size()[1]);
                        velocity.setY(0);
                        isTouchingGround = true;
                    } else if (relativeLocation == 2) {
                        isTouchingRightWall = true;
                        moveable.hitBox().setTranslateX(platform.hitBox().getTranslateX() - moveable.size()[0]);
                        velocity.setX(0);
                        if (velocity.getY() > Constants.SLIDE_WALL_SPEED) {
                            isFacingLeft = false;
                            moveData.setState(MoveState.SLIDING);
                            velocity.setY(Constants.SLIDE_WALL_SPEED);
                        }
                    } else if (relativeLocation == 4) {
                        isTouchingLeftWall = true;
                        moveable.hitBox().setTranslateX(platform.hitBox().getTranslateX() + Constants.TILE_SIZE);
                        velocity.setX(0);
                        if (velocity.getY() > Constants.SLIDE_WALL_SPEED) {
                            isFacingLeft = true;
                            moveData.setState(MoveState.SLIDING);
                            velocity.setY(Constants.SLIDE_WALL_SPEED);
                        }
                    } else if (relativeLocation == 3) {
                        moveable.hitBox().setTranslateY(platform.hitBox().getTranslateY() + Constants.TILE_SIZE);
                        velocity.setY(0);
                    }
                }
            }
            moveData.isTouchingGround = isTouchingGround;
            moveData.isTouchingWall = isTouchingLeftWall || isTouchingRightWall;
        }
    }

    /**
     * @param moveable
     * @param moveData
     */

    //todo: use state pattern
    private static void setStatus(Entity moveable, MoveData moveData) {
            moveData.isTouchingWall = isTouchingLeftWall || isTouchingRightWall;

            moveData.analyzeState(moveData);
    }

    public static void centerAlign(Entity moveable, Coord2D offset){
        moveable.canvas().setTranslateX(moveable.hitBox().getTranslateX() + moveable.hitBox().getBoundsInParent().getWidth() / 2 - moveable.canvas().getBoundsInParent().getWidth() / 2 + offset.getX());
        moveable.canvas().setTranslateY(moveable.hitBox().getTranslateY() + moveable.hitBox().getBoundsInParent().getHeight() / 2 - moveable.canvas().getBoundsInParent().getHeight() / 2 + offset.getY());
    }
}