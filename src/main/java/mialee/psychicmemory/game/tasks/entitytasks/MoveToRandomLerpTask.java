package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.math.Vec2d;

public class MoveToRandomLerpTask extends MoveToPositionLerpTask {
    public MoveToRandomLerpTask(Entity owner, int lerp, int length) {
        super(owner, new Vec2d(0, 0), lerp, length);
        refresh();
    }

    @Override
    public void refresh() {
        super.refresh();
        destination.set(new Vec2d(PsychicMemory.RANDOM.nextDouble(560) + 30, PsychicMemory.RANDOM.nextDouble(200) + 30));
    }
}
