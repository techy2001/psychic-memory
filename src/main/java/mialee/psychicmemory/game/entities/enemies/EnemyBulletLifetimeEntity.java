package mialee.psychicmemory.game.entities.enemies;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.tasks.Task;
import mialee.psychicmemory.math.Vec2d;

/**
 * Variant of {@link mialee.psychicmemory.game.entities.enemies.EnemyBulletEntity} that despawns after a delay instead of off of the screen edges.
 * Used for attacks where bullets can curve back on screen.
 */
public class EnemyBulletLifetimeEntity extends EnemyBulletEntity {
    private final int lifetime;

    public EnemyBulletLifetimeEntity(World board, Vec2d position, Vec2d velocity, int lifetime) {
        super(board, position, velocity);
        this.lifetime = lifetime;
    }

    @Override
    public void tick() {
        if (age == 1) initializeTasks();
        age++;
        if (age > lifetime) this.markForDeletion();
        if (!taskList.isEmpty()) {
            Task task = taskList.get(0);
            task.tick();
            if (task.isComplete()) {
                if (task.shouldLoop()) {
                    pendingTasks.add(task);
                    task.refresh();
                }
                taskList.remove(0);
            }
        }
        taskList.addAll(pendingTasks);
        pendingTasks.clear();
        PlayerEntity player = world.getPlayer();
        if (player != null) {
            if (player.squaredDistanceTo(this) < player.squaredHitboxes(this)) {
                player.damage(1);
                markForDeletion();
            }
        }
    }
}
