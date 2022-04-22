package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.entities.EnemyBulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

public class LargeTrailDownwardTask extends EntityTask {
    private final double rotation;
    private final double speed;

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
                    new Vec2d(0, PsychicMemory.RANDOM.nextDouble(speed - 2.2f) + 2)
                            .rotate(rotation + PsychicMemory.RANDOM.nextDouble(6) - 3)), EntityFaction.ENEMY_BULLET);
        }
    }
}

