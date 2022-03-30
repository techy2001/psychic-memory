package mialee.psychicmemory.game.tasks.core;

import mialee.psychicmemory.game.World;

public class WorldTask extends Task {
    private final World owner;

    public WorldTask(World owner, int length) {
        super(length);
        this.owner = owner;
    }
}
