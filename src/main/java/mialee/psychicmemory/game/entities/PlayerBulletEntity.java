package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.BulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.entities.core.EntityFaction;
import mialee.psychicmemory.game.entities.core.LivingEntity;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Color;

public class PlayerBulletEntity extends BulletEntity {
    public PlayerBulletEntity(World board, Vec2d position, Vec2d velocity, int damage) {
        super(board, position, velocity, EntityFaction.PLAYER_BULLET, EntityFaction.ENEMY, damage);
        this.color = Color.CYAN;
    }

    @Override
    public void tick() {
        super.tick();
        for (Entity entity : world.getEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                if (entity.faction == enemyFaction) {
                    if (entity.squaredDistanceTo(this) < entity.squaredHitboxes(this)) {
                        if (livingEntity.damage(damage)) {
                            this.markForDeletion();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.hitRadius = 10;
        this.visualSize = 12;
    }
}
