package mialee.psychicmemory.game.tasks.tasks;

import mialee.psychicmemory.game.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;

public class MultiTask extends Task {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public MultiTask(int length, Task... tasks) {
        super(length);
        Collections.addAll(this.tasks, tasks);
    }

    @Override
    public void tick() {
        super.tick();
        for (Task task : tasks) {
            if (!task.isComplete()) task.tick();
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        for (Task task : tasks) {
            task.refresh();
        }
    }
}
