package mialee.psychicmemory.game.tasks.tasks;

import mialee.psychicmemory.game.tasks.Task;

/**
 * Task which does nothing but wait, incredibly important for all forms of timing.
 */
public class WaitTask extends Task {
    public WaitTask(int length) {
        super(length);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
