package com.example.platformerplain.move.command;

import com.example.platformerplain.move.state.MoveData;

import static com.example.platformerplain.data.Constants.MAX_MOVE_SPEED;

/**
 * @author Zelin Xia
 * @date 2024/12/2
 */
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

