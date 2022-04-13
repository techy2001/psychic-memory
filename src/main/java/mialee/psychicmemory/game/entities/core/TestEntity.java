package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.tasks.entitytasks.FireAtPlayerTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveWithVelocityTask;
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
        addTask(new MoveWithVelocityTask(this, 50, true));
        addTask(new FireAtPlayerTask(this, 100, 50, 5, 22.5, 1.5f, true));
        addTask(new MoveWithVelocityTask(this, 50, true));
        addTask(new FireAtPlayerTask(this, 100, 50, 3, 10, 1.5f, true));
    }

    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.setColor(Color.BLUE);
        graphics.fillOval((int) (position.x - (getHitRadius() / 2)), (int) (position.y - (getHitRadius() / 2)), (int) getHitRadius(), (int) getHitRadius());
    }
}
