package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.Main;
import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.entities.enemies.EnemyBulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

/**
 * Fires a large bullet followed by a crowd of smaller bullets.
 */
public class LargeTrailDownwardTask extends EntityTask {
    private final double rotation;
    private final double speed;

    /**
     * @param owner Entity to fire the bullets from.
     * @param rotation Amount to rotate the shot by, starting at straight downwards.
     * @param speed Speed for the bullets to fire at.
     */
    public LargeTrailDownwardTask(Entity owner, double rotation, double speed) {
        super(owner, 1);
        this.speed = speed;
        this.rotation = rotation;
    }

    @Override
    public void tick() {
        super.tick();
        EnemyBulletEntity leader = new EnemyBulletEntity(owner.world, owner.position.copy(), new Vec2d(0, speed).rotate(rotation));
        leader.visualSize *= 3;
        leader.hitRadius *= 3;
        owner.world.getBank().addEntity(leader, EntityFaction.ENEMY_BULLET);
        for (int i = 0; i < 20; i++) {
            owner.world.getBank().addEntity(new EnemyBulletEntity(owner.world, owner.position.copy(),
                    new Vec2d(0, Main.RANDOM.nextDouble(speed - 2.2f) + 2)
                            .rotate(rotation + Main.RANDOM.nextDouble(6) - 3)), EntityFaction.ENEMY_BULLET);
        }
    }
}

