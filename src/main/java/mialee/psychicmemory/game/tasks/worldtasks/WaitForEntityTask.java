package mialee.psychicmemory.game.tasks.worldtasks;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.WorldTask;

/**
 * Task which waits until the given entity is defeated.
 */
public class WaitForEntityTask extends WorldTask {
    private final Entity target;

    public WaitForEntityTask(World world, Entity target) {
        super(world, 1);
        this.target = target;
    }

    @Override
    public void tick() {
        if (tick == 0) refresh();
        tick++;
    }

    public boolean isComplete() {
        return target == null || target.isMarkedForDeletion();
    }
}
