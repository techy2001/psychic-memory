package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.BulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.entities.core.EntityFaction;
import mialee.psychicmemory.game.entities.core.LivingEntity;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Color;

public class EnemyBulletEntity extends BulletEntity {
    public EnemyBulletEntity(World board, Vec2d position, Vec2d velocity) {
        super(board, position, velocity, EntityFaction.ENEMY_BULLET, EntityFaction.PLAYER, 1);
        this.color = Color.RED;
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

    @Override
    protected void registerStats() {
        super.registerStats();
        this.hitRadius = 8;
        this.visualSize = 10;
    }
}
