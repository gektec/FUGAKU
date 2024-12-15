package com.example.platformerplain.move.command;

import com.example.platformerplain.move.data.MoveData;

import static com.example.platformerplain.Constants.MAX_MOVE_SPEED;

public class MoveRightCommand implements PlayCommand {
    private MoveData moveData;

    public MoveRightCommand(MoveData moveData) {
        this.moveData = moveData;
    }

    @Override
    public void execute() {
        moveData.velocity.smoothAdd(2, 0, MAX_MOVE_SPEED, 0);
    }
}

