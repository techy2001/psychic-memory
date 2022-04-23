package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;

/**
 * Clears all enemy bullets on the screen, either granting or not granting points for these bullets.
 */
public class ClearBulletsTask extends EntityTask {
    private final boolean points;

    public ClearBulletsTask(Entity owner, boolean points) {
        super(owner, 1);
        this.points = points;
    }

    @Override
    public void tick() {
        owner.world.getBank().clearBullets(points);
        super.tick();
    }
}