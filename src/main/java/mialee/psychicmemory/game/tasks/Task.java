package mialee.psychicmemory.game.tasks;

public abstract class Task {
    protected int tick = 0;
    private final int length;
    private boolean complete = false;
    protected boolean loop = true;

    public Task(int length) {
        this.length = length;
    }

    public void tick() {
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

    public void refresh() {
        tick = 0;
        complete = false;
    }
}
