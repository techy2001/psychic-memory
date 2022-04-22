package mialee.psychicmemory.game.entities.bosses;

import mialee.psychicmemory.game.tasks.Task;

import java.util.ArrayList;

public class BossPhase extends SubPhase{
    private Task endTask;
    private SubPhase subPhase;
    private float subPhaseTrigger = 20;

    public BossPhase(ArrayList<Task> taskList) {
        super(taskList);
    }

    public Task getEndTask() {
        return endTask;
    }

    public void setEndTask(Task endTask) {
        this.endTask = endTask;
    }

    public SubPhase getSubPhase() {
        return subPhase;
    }

    public float getSubPhaseTrigger() {
        return subPhaseTrigger;
    }

    public void setSubPhase(SubPhase subPhase, float subPhaseTrigger) {
        this.subPhase = subPhase;
        this.subPhaseTrigger = subPhaseTrigger;
    }
}
