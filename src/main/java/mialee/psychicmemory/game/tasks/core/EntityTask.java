package mialee.psychicmemory.game.tasks.core;

import mialee.psychicmemory.game.entities.core.Entity;

public class EntityTask extends Task {
    private final Entity owner;

    public EntityTask(Entity owner, int length) {
        super(length);
        this.owner = owner;
    }
}
