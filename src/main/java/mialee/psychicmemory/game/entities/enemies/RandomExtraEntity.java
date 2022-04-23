package mialee.psychicmemory.game.entities.enemies;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.tasks.Task;
import mialee.psychicmemory.math.Vec2d;

/**
 * Variant of {@link mialee.psychicmemory.game.entities.enemies.BasicEnemy} which also holds a task which it will randomly do based on a chance every tick.
 */
public class RandomExtraEntity extends BasicEnemy {
    private Task task;
    private final float chance;

    public RandomExtraEntity(World board, Vec2d position, float chance) {
        super(board, position);
        this.chance = chance;
    }

    public void tick() {
        super.tick();
        if (PsychicMemory.RANDOM.nextFloat() <= chance) {
            if (task != null) task.tick();
        }
    }

    @Override
    protected void initializeTasks() {}

    public void setTask(Task task) {
        this.task = task;
    }
}
