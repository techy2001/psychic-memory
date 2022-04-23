package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.entities.enemies.EnemyBulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

/**
 * Task to fire bullets in a circle and rotate the circle for next firing.
 */
public class FireBulletsRoundSpinTask extends EntityTask {
    protected final int cooldownMax;
    protected final int count;
    protected final double speed;
    protected final double spin;
    protected int cooldown;
    protected int shot = 1;

    /**
     * Creates a new firing task.
     * This fires bullets around the owner, turning by {@link #spin} degrees every time it fires.
     * @param owner Entity to fire the bullets from.
     * @param length Amount of time to perform the task.
     * @param cooldown Delay between firing.
     * @param count Amount of bullets to fire.
     * @param speed Velocity of the bullets.
     * @param spin The amount to turn after each firing.
     */
    public FireBulletsRoundSpinTask(Entity owner, int length, int cooldown, int count, double speed, double spin) {
        super(owner, length);
        this.cooldownMax = cooldown;
        this.count = count;
        this.speed = speed;
        this.spin = spin;
    }

    @Override
    public void tick() {
        super.tick();
        if (cooldown <= 0) {
            for (int i = 0; i < count; i++) {
                double f = Math.sin(((((float) 360 / count) * i) + (shot * spin)) * ((float) Math.PI / 180));
                double h = Math.cos(((((float) 360 / count) * i) + (shot * spin)) * ((float) Math.PI / 180));
                owner.world.getBank().addEntity(new EnemyBulletEntity(owner.world, owner.position.copy(), new Vec2d(f * speed, h * speed)), EntityFaction.ENEMY_BULLET);
            }
            cooldown = cooldownMax;
            shot++;
        }
        cooldown--;
    }
}
