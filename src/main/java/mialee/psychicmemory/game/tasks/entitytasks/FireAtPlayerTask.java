package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.entities.enemies.EnemyBulletEntity;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

/**
 * Basic task for firing at the player.
 */
public class FireAtPlayerTask extends EntityTask {
    private final int cooldownMax;
    private final int count;
    private final double deviation;
    private final double speed;
    private int cooldown;
    private PlayerEntity player;

    /**
     * Creates a new firing task aimed at the player.
     * @param owner Entity to fire the bullets from.
     * @param length Amount of time to perform the task.
     * @param cooldown Delay between firing.
     * @param count Amount of bullets to fire.
     * @param deviation Distance between bullets fired.
     * @param speed Velocity of the bullets.
     */
    public FireAtPlayerTask(Entity owner, int length, int cooldown, int count, double deviation, double speed) {
        super(owner, length);
        this.cooldownMax = cooldown;
        this.count = count;
        this.deviation = deviation;
        this.speed = speed;
    }

    /**
     * Takes the player position and owner position in order to get a vector towards the player and rotates based on the offset.
     */
    @Override
    public void tick() {
        super.tick();
        if (cooldown <= 0) {
            if (player != null) {
                double size = Math.abs(player.position.x - owner.position.x) + Math.abs(player.position.y - owner.position.y);
                Vec2d playerAngle = new Vec2d(player.position.x - owner.position.x, player.position.y - owner.position.y);
                playerAngle.divide(size);
                if (count % 2 == 0) {
                    int amount = count / 2;
                    for (double i = -(amount - 0.5); i <= (amount - 0.5) ; i++) {
                        owner.world.getBank().addEntity(new EnemyBulletEntity(owner.world, owner.position.copy(), playerAngle.copy().rotate(i * deviation).multiply(speed)), EntityFaction.ENEMY_BULLET);
                    }
                } else {
                    int amount = (count - 1) / 2;
                    for (double i = -amount; i <= amount; i++) {
                        owner.world.getBank().addEntity(new EnemyBulletEntity(owner.world, owner.position.copy(), playerAngle.copy().rotate(i * deviation).multiply(speed)), EntityFaction.ENEMY_BULLET);
                    }
                }
                cooldown = cooldownMax;
            } else {
                player = owner.world.getPlayer();
            }
        }
        cooldown--;
    }
}