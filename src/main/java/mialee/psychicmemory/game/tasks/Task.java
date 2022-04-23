package mialee.psychicmemory.game.tasks;

/**
 * Core task class.
 */
public abstract class Task {
    protected int tick = 0;
    protected final int length;
    protected boolean complete = false;
    protected boolean loop = true;

    /**
     * @param length The length of time a task should go on for.
     */
    public Task(int length) {
        this.length = length;
    }

    /**
     * Tasks will keep track of how many ticks they have lived through.
     * If it has ticked as long as it's length it will be marked as complete.
     */
    public void tick() {
        if (tick == 0) refresh();
        tick++;
        if (tick >= length) {
            complete = true;
        }
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean shouldLoop() {
        return loop;
    }

    /**
     * @param loop Sets if a task should loop.
     * @return Returns the task, allowing for more versatility.
     */
    public Task setLoop(boolean loop) {
        this.loop = loop;
        return this;
    }

    /**
     * Refreshes the tasks age, important for looping tasks.
     */
    public void refresh() {
        tick = 0;
        complete = false;
    }
}
