package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;

public class DeleteSelfTask extends EntityTask {
    public DeleteSelfTask(Entity owner) {
        super(owner, 1);
    }

    @Override
    public void tick() {
        owner.markForDeletion();
        super.tick();
    }
}
