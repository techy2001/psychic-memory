package mialee.psychicmemory.game.entities.bosses;

import mialee.psychicmemory.game.tasks.Task;

import java.util.ArrayList;

public class SubPhase {
    public final ArrayList<Task> taskList;
    public final ArrayList<Task> pendingTasks = new ArrayList<>();

    public SubPhase(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
