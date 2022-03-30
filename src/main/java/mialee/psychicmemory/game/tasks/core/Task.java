package mialee.psychicmemory.game.tasks.core;

public class Task {
    private int tick = 0;
    private final int length;
    private boolean complete = false;

    public Task(int length) {
        this.length = length;
    }

    private void doAction() {
        tick++;

        if (tick >= length) {
            complete = true;
        }
    }

    public boolean isComplete() {
        return complete;
    }
}
