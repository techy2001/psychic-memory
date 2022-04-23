package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;

/**
 * Simple task to move the owner by their velocity every tick for {@link #length}.
 */
public class MoveWithVelocityTask extends EntityTask {
    public MoveWithVelocityTask(Entity owner, int length) {
        super(owner, length);
    }

    @Override
    public void tick() {
        owner.position.add(owner.velocity);
        super.tick();
    }
}
