package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

public class MoveToPositionLerpTask extends EntityTask {
    private Vec2d start;
    private final Vec2d destination;

    public MoveToPositionLerpTask(Entity owner, Vec2d destination, int length) {
        super(owner, length);
        this.destination = destination;
    }

    @Override
    public void tick() {
        super.tick();
        owner.position.set(start.lerp(destination, Math.pow((float) tick / length, 3)));
    }

    @Override
    public void refresh() {
        super.refresh();
        start = owner.position.copy();
    }
}
