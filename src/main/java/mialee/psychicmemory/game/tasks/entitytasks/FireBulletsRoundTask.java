package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.entities.EnemyBulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

public class FireBulletsRoundTask extends EntityTask {
    private final int cooldownMax;
    private final int count;
    private final double deviation;
    private final double speed;
    private int cooldown;

    public FireBulletsRoundTask(Entity owner, int length, int cooldown, int count, double deviation, double speed, boolean loop) {
        super(owner, length);
        this.cooldownMax = cooldown;
        this.count = count;
        this.deviation = deviation;
        this.speed = speed;
        this.loop = loop;
    }

    @Override
    public void tick() {
        super.tick();
        if (cooldown <= 0) {
            int amount = (count - 1) / 2;
            for (double i = -(amount * deviation); i <= (amount * deviation); i += deviation) {
                double f = Math.sin(i * ((float) Math.PI / 180));
                double h = Math.cos(i * ((float) Math.PI / 180));
                owner.world.getBank().addEntity(new EnemyBulletEntity(owner.world, owner.position.copy(), new Vec2d(f * speed, h * speed)), EntityFaction.ENEMY_BULLET);
            }
            cooldown = cooldownMax;
        }
        cooldown--;
    }
}
