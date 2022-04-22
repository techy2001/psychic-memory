package mialee.psychicmemory.game.tasks;

public abstract class Task {
    protected int tick = 0;
    protected final int length;
    protected boolean complete = false;
    protected boolean loop = true;

    public Task(int length) {
        this.length = length;
    }

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

    public Task setLoop(boolean loop) {
        this.loop = loop;
        return this;
    }

    public void refresh() {
        tick = 0;
        complete = false;
    }
}
