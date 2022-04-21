package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.entities.EnemyBulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

public class FireBulletsRoundSpinTask extends EntityTask {
    private final int cooldownMax;
    private final int count;
    private final double speed;
    private final double spin;
    private int offset = 0;
    private int cooldown;
    private int shot = 1;

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
                double f = Math.sin(((((float) 360 / count) * i) + offset + (shot * spin)) * ((float) Math.PI / 180));
                double h = Math.cos(((((float) 360 / count) * i) + offset + (shot * spin)) * ((float) Math.PI / 180));
                owner.world.getBank().addEntity(new EnemyBulletEntity(owner.world, owner.position.copy(), new Vec2d(f * speed, h * speed)), EntityFaction.ENEMY_BULLET);
            }
            cooldown = cooldownMax;
            shot++;
        }
        cooldown--;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
