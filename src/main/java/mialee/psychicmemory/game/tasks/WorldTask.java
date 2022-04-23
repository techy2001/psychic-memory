package mialee.psychicmemory.game.tasks;

import mialee.psychicmemory.game.World;

/**
 * A task which stores the world it belongs to.
 * Does not loop by default.
 */
public abstract class WorldTask extends Task {
    protected final World world;

    public WorldTask(World world, int length) {
        super(length);
        this.world = world;
        this.loop = false;
    }
}
