package mialee.psychicmemory.game.tasks;

import mialee.psychicmemory.game.World;

public abstract class WorldTask extends Task {
    protected final World owner;

    public WorldTask(World owner, int length) {
        super(length);
        this.owner = owner;
    }
}
