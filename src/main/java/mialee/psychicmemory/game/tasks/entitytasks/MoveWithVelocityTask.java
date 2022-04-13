package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;

public class MoveWithVelocityTask extends EntityTask {
    public MoveWithVelocityTask(Entity owner, int length, boolean loop) {
        super(owner, length);
        this.loop = loop;
    }

    @Override
    public void tick() {
        owner.position.add(owner.velocity);
        super.tick();
    }
}
