package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;

public class BossPauseTask extends EntityTask {
    private final boolean pause;

    public BossPauseTask(Entity owner, boolean pause) {
        super(owner, 1);
        this.pause = pause;
    }

    @Override
    public void tick() {
        PsychicMemory.gameState = pause ? GameState.BOSS_PAUSED : GameState.INGAME;
        super.tick();
    }
}
