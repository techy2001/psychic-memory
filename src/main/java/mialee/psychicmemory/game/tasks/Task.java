package mialee.psychicmemory.game.tasks;

public abstract class Task {
    private int tick = 0;
    private final int length;
    private boolean complete = false;

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
}
