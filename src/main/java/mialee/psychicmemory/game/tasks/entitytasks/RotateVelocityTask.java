package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;

/**
 * Rotates the owner's velocity by {@link #rotation} degrees.
 */
public class RotateVelocityTask extends EntityTask {
    private final double rotation;

    public RotateVelocityTask(Entity owner, double rotation) {
        super(owner, 1);
        this.rotation = rotation;
    }

    @Override
    public void tick() {
        super.tick();
        owner.velocity.rotate(rotation);
    }
}
