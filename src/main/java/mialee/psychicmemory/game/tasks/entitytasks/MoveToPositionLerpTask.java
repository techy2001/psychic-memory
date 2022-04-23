package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

/**
 * Moves the entity to the given position, using smoothing.
 */
public class MoveToPositionLerpTask extends EntityTask {
    protected int lerp;
    protected Vec2d start;
    protected final Vec2d destination;

    /**
     * @param owner Entity to move.
     * @param destination Location to move to.
     * @param lerp Amount of ease in smoothing to add.
     * @param length Amount of time it should take to get there.
     */
    public MoveToPositionLerpTask(Entity owner, Vec2d destination, int lerp, int length) {
        super(owner, length);
        this.destination = destination;
        this.lerp = lerp;
    }

    @Override
    public void tick() {
        super.tick();
        owner.position.set(start.lerp(destination, Math.pow((float) tick / length, lerp)));
    }

    @Override
    public void refresh() {
        super.refresh();
        start = owner.position.copy();
    }
}
