package mialee.psychicmemory.game.entities.bosses;

import mialee.psychicmemory.game.tasks.Task;

import java.util.ArrayList;

/**
 * A bossPhase contains the tasks which a boss will make use of for a single phase.
 * Can also contain a subPhase which triggers when the boss is at low enough health.
 */
public class BossPhase extends SubPhase {
    private Task endTask;
    private SubPhase subPhase;
    private float subPhaseTrigger = 0;

    /**
     * @param taskList Task list for the phase to make use of.
     */
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
