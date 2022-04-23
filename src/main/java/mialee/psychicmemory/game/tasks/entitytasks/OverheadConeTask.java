package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.entities.enemies.EnemyBulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

/**
 * Randomly fires a cone of bullets above the entity.
 */
public class OverheadConeTask extends EntityTask {
    private final int cooldownMax;
    private final int count;
    private final double speed;
    private int cooldown;

    /**
     * @param owner Entity to fire the bullets from.
     * @param length Amount of time to perform the task.
     * @param cooldown Delay between firing.
     * @param count Amount of bullets to fire.
     * @param speed Velocity of the bullets.
     */
    public OverheadConeTask(Entity owner, int length, int cooldown, int count, double speed) {
        super(owner, length);
        this.cooldownMax = cooldown;
        this.count = count;
        this.speed = speed;
    }

    /**
     * Bullets are fired at a random rotation in a 240 degree arc upwards.
     */
    @Override
    public void tick() {
        super.tick();
        if (cooldown <= 0) {
            for (int i = 0; i < count; i++) {
                owner.world.getBank().addEntity(new EnemyBulletEntity(owner.world, owner.position.copy(), new Vec2d(0, -speed).rotate(PsychicMemory.RANDOM.nextDouble(240) - 120)), EntityFaction.ENEMY_BULLET);
            }
            cooldown = cooldownMax;
        }
        cooldown--;
    }
}
