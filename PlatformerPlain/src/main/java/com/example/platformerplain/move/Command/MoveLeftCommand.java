package com.example.platformerplain.move.Command;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.move.Coord2D;

import static com.example.platformerplain.Constants.MAX_MOVE_SPEED;

public class MoveLeftCommand implements PlayCommand {
    private Entity player;
    private Coord2D velocity;

    public MoveLeftCommand(Entity player, Coord2D velocity) {
        this.player = player;
        this.velocity = velocity;
    }

    @Override
    public void execute() {
        velocity.smoothAdd(-2, 0, -MAX_MOVE_SPEED, 0);
    }
}
