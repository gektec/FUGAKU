package com.fugaku.platformer.move.command;

import com.fugaku.platformer.move.state.MoveData;

import static com.fugaku.platformer.data.Constants.MAX_MOVE_SPEED;

/**
 * @author Zelin Xia
 * @date 2024/12/2
 */
public class MoveLeftCommand implements PlayCommand {
    private final MoveData moveData;

    public MoveLeftCommand(MoveData moveData) {
        this.moveData = moveData;
    }

    @Override
    public void execute() {
        moveData.velocity.smoothAdd(-2, 0, -MAX_MOVE_SPEED, 0);
    }
}
