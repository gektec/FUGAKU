package com.example.platformerplain.move.Command;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.move.Coord2D;

public class MoveRightCommand implements PlayCommand {
    private Entity player;
    private Coord2D velocity;

    public MoveRightCommand(Entity player, Coord2D velocity) {
        this.player = player;
        this.velocity = velocity;
    }

    @Override
    public void execute() {
        velocity.add(4, 0);  // 每次执行向右移动4个单位
    }
}

