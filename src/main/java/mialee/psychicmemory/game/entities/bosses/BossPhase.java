package mialee.psychicmemory.game.entities.bosses;

import mialee.psychicmemory.game.tasks.Task;

import java.util.ArrayList;

public class BossPhase {
    public final ArrayList<Task> taskList;
    public final ArrayList<Task> pendingTasks = new ArrayList<>();
    private Task endTask;

    public BossPhase(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public void setEndTask(Task endTask) {
        this.endTask = endTask;
    }

    public Task getEndTask() {
        return endTask;
    }
}
