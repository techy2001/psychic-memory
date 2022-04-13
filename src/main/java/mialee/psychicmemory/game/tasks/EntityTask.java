package mialee.psychicmemory.game.tasks;

import mialee.psychicmemory.game.entities.core.Entity;

public abstract class EntityTask extends Task {
    protected final Entity owner;

    public EntityTask(Entity owner, int length) {
        super(length);
        this.owner = owner;
    }
}
