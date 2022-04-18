package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.EntityFaction;
import mialee.psychicmemory.game.entities.core.LivingEntity;
import mialee.psychicmemory.game.tasks.entitytasks.FireAtPlayerTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveToPositionTask;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Color;
import java.awt.Graphics;

public class TestEntity extends LivingEntity {
    public TestEntity(World board, Vec2d position, Vec2d velocity, EntityFaction faction) {
        super(board, position, velocity, faction);
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.health = 5000;
    }

    public void tick() {
        super.tick();
        if (position.x > world.size.x) {
            position.x = 0;
        }
        if (position.y > world.size.y) {
            position.y = 0;
        }
        if (position.x < 0) {
            position.x = world.size.x;
        }
        if (position.y < 0) {
            position.y = world.size.y;
        }
    }

    @Override
    protected void initializeTasks() {
        addTask(new MoveToPositionTask(this, new Vec2d(PsychicMemory.RANDOM.nextInt(400), PsychicMemory.RANDOM.nextInt(400)), 20));
        addTask(new FireAtPlayerTask(this, 100, 50, 4, 10, 1.5f, true));
        addTask(new MoveToPositionTask(this, new Vec2d(PsychicMemory.RANDOM.nextInt(400), PsychicMemory.RANDOM.nextInt(200)), 20));
        addTask(new FireAtPlayerTask(this, 100, 50, 6, 22.5, 1.5f, true));
    }

    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.setColor(Color.BLUE);
        graphics.fillOval((int) (position.x - (getHitRadius())), (int) (position.y - (getHitRadius())), getHitRadius() * 2, getHitRadius() * 2);
    }
}
