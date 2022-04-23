package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.entities.enemies.EnemyBulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

/**
 * Task to fire bullets in a circle, the bullets will then rotate.
 */
public class FireBulletsRoundTask extends EntityTask {
    private final int cooldownMax;
    private final int count;
    private final double speed;
    private final int offset;
    private int cooldown;

    /**
     * Creates a new firing task.
     * This fires bullets around the owner, randomly twisted by {@link #offset}.
     * @param owner Entity to fire the bullets from.
     * @param length Amount of time to perform the task.
     * @param cooldown Delay between firing.
     * @param count Amount of bullets to fire.
     * @param speed Velocity of the bullets.
     */
    public FireBulletsRoundTask(Entity owner, int length, int cooldown, int count, double speed) {
        super(owner, length);
        this.cooldownMax = cooldown;
        this.count = count;
        this.speed = speed;
        this.offset = PsychicMemory.RANDOM.nextInt(360);
    }

    @Override
    public void tick() {
        super.tick();
        if (cooldown <= 0) {
            for (int i = 0; i < count; i++) {
                double f = Math.sin(((((float) 360 / count) * i) + offset) * ((float) Math.PI / 180));
                double h = Math.cos(((((float) 360 / count) * i) + offset) * ((float) Math.PI / 180));
                owner.world.getBank().addEntity(new EnemyBulletEntity(owner.world, owner.position.copy(), new Vec2d(f * speed, h * speed)), EntityFaction.ENEMY_BULLET);
            }
            cooldown = cooldownMax;
        }
        cooldown--;
    }
}
