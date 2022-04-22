package mialee.psychicmemory.game.entities.bosses;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.Task;
import mialee.psychicmemory.game.tasks.tasks.WaitTask;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Graphics;

public class GameEndEntity extends Entity {
    private final int lifetime;

    public GameEndEntity(World board, int lifetime) {
        super(board, new Vec2d(0, 0), new Vec2d(0, 0));
        this.lifetime = lifetime;
    }

    @Override
    protected void initializeTasks() {
        addTask(new WaitTask(lifetime));
        addTask(new Task(1) {
            @Override
            public void tick() {
                super.tick();
                PsychicMemory.end(true);
            }
        });
    }

    @Override
    public void render(Graphics graphics) {}
}