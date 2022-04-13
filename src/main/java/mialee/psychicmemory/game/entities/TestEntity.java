package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.EntityType;
import mialee.psychicmemory.game.entities.core.LivingEntity;
import mialee.psychicmemory.game.tasks.entitytasks.DeleteSelfTask;
import mialee.psychicmemory.game.tasks.entitytasks.FireAtPlayerTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveToPositionTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveWithVelocityTask;
import mialee.psychicmemory.game.tasks.tasks.WaitTask;
import mialee.psychicmemory.math.Vec2d;

import java.awt.*;

public class TestEntity extends LivingEntity {
    public TestEntity(World board, Vec2d position, Vec2d velocity, EntityType faction) {
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
        addTask(new MoveToPositionTask(this, new Vec2d(300, 300), 50));
        addTask(new WaitTask(50));
//        addTask(new MoveWithVelocityTask(this, 50));
        addTask(new FireAtPlayerTask(this, 100, 50, 4, 10, 1.5f, true));
        addTask(new MoveToPositionTask(this, new Vec2d(100, 100), 100));
        addTask(new WaitTask(50));
        addTask(new FireAtPlayerTask(this, 100, 50, 6, 22.5, 1.5f, true));
//        addTask(new DeleteSelfTask(this));
    }

    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.setColor(Color.BLUE);
        graphics.fillOval((int) (position.x - (getHitRadius())), (int) (position.y - (getHitRadius())), getHitRadius() * 2, getHitRadius() * 2);
    }
}
