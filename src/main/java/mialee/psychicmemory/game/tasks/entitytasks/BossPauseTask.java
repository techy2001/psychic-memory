package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.Main;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;

/**
 * A task used only by bosses in order to pause or unpause the game.
 */
public class BossPauseTask extends EntityTask {
    private final boolean pause;

    public BossPauseTask(Entity owner, boolean pause) {
        super(owner, 1);
        this.pause = pause;
    }

    @Override
    public void tick() {
        Main.gameState = pause ? GameState.BOSS_PAUSED : GameState.INGAME;
        super.tick();
    }
}
