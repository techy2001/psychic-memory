package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.math.Vec2d;

public abstract class EnemyEntity extends LivingEntity {
    public EnemyEntity(World board, Vec2d position) {
        super(board, position, new Vec2d(0, 0), EntityFaction.ENEMY);
    }

    @Override
    public void tick() {
        super.tick();
        PlayerEntity player = world.getPlayer();
        if (player != null) {
            if (player.squaredDistanceTo(this) < player.squaredHitboxes(this)) {
                player.damage(1);
            }
        }
    }
}
