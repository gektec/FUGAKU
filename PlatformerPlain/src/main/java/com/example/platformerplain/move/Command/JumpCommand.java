package com.example.platformerplain.move.Command;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.move.Coord2D;
import com.example.platformerplain.move.MoveStatus;
import com.example.platformerplain.Assets;

import static com.example.platformerplain.Assets.JUMP_SFX;

public class JumpCommand implements PlayCommand {
    private Entity player;
    private Coord2D velocity;
    private MoveStatus moveStatus;

    public JumpCommand(Entity player, Coord2D velocity, MoveStatus moveStatus) {
        this.player = player;
        this.velocity = velocity;
        this.moveStatus = moveStatus;
    }

    @Override
    public void execute() {
        velocity.setY(-20);  // 跳跃的速度
        moveStatus.canJump = false;  // 设置跳跃状态

        JUMP_SFX.play();

    }
}
