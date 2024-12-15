package com.example.platformerplain.move.Command;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.MoveStatus;

import static com.example.platformerplain.Constants.MAX_MOVE_SPEED;

public class MoveLeftCommand implements PlayCommand {
    private MoveStatus moveStatus;

    public MoveLeftCommand(MoveStatus moveStatus) {
        this.moveStatus = moveStatus;
    }

    @Override
    public void execute() {
        moveStatus.velocity.smoothAdd(-2, 0, -MAX_MOVE_SPEED, 0);
    }
}
