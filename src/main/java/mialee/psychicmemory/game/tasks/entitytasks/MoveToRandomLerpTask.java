package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.Main;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.math.Vec2d;

/**
 * Moves to a random position in the top half of the screen.
 */
public class MoveToRandomLerpTask extends MoveToPositionLerpTask {
    /**
     * @param owner Entity to move.
     * @param lerp Amount of ease in smoothing to add.
     * @param length Amount of time it should take to get there.
     */
    public MoveToRandomLerpTask(Entity owner, int lerp, int length) {
        super(owner, new Vec2d(0, 0), lerp, length);
        refresh();
    }

    /**
     * Chooses a new random position after every refresh.
     */
    @Override
    public void refresh() {
        super.refresh();
        destination.set(new Vec2d(Main.RANDOM.nextDouble(560) + 30, Main.RANDOM.nextDouble(200) + 30));
    }
}
