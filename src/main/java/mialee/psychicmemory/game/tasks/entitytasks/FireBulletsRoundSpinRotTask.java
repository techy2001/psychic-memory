package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.entities.enemies.EnemyBulletLifetimeEntity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

/**
 * Task to fire bullets in a circle, rotate the circle for next firing, the bullets will then rotate.
 */
public class FireBulletsRoundSpinRotTask extends EntityTask {
    private final int cooldownMax;
    private final int count;
    private final double speed;
    private final double spin;
    private final double rotation;
    private int cooldown;
    private int shot = 1;

    /**
     * Creates a new firing task.
     * This fires bullets around the owner, turning by {@link #spin} degrees every time it fires.
     * These bullets will turn {@link #rotation} degrees shortly after being fired.
     * @param owner Entity to fire the bullets from.
     * @param length Amount of time to perform the task.
     * @param cooldown Delay between firing.
     * @param count Amount of bullets to fire.
     * @param speed Velocity of the bullets.
     * @param spin The amount to turn after each firing.
     * @param rotation The amount the bullets should rotate shortly after being fired.
     */
    public FireBulletsRoundSpinRotTask(Entity owner, int length, int cooldown, int count, double speed, double spin, double rotation) {
        super(owner, length);
        this.cooldownMax = cooldown;
        this.count = count;
        this.speed = speed;
        this.spin = spin;
        this.rotation = rotation;
    }

    /**
     * Uses {@link mialee.psychicmemory.game.entities.enemies.EnemyBulletLifetimeEntity} instead of {@link mialee.psychicmemory.game.entities.enemies.EnemyBulletEntity} due to bullets despawning off-screen before they get to turn back.
     */
    @Override
    public void tick() {
        super.tick();
        if (cooldown <= 0) {
            for (int i = 0; i < count; i++) {
                double f = Math.sin(((((float) 360 / count) * i) + (shot * spin)) * ((float) Math.PI / 180));
                double h = Math.cos(((((float) 360 / count) * i) + (shot * spin)) * ((float) Math.PI / 180));
                EnemyBulletLifetimeEntity enemyBullet = new EnemyBulletLifetimeEntity(owner.world, owner.position.copy(), new Vec2d(f * speed, h * speed), 520) {
                    @Override
                    protected void initializeTasks() {
                        addTask(new MoveWithVelocityTask(this, 120));
                        addTask(new RotateVelocityTask(this, rotation));
                        addTask(new MoveWithVelocityTask(this, Integer.MAX_VALUE));
                    }
                };
                owner.world.getBank().addEntity(enemyBullet, EntityFaction.ENEMY_BULLET);
            }
            cooldown = cooldownMax;
            shot++;
        }
        cooldown--;
    }
}
