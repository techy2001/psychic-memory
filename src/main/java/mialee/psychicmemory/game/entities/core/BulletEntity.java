package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

import java.awt.*;

public abstract class BulletEntity extends Entity {
    protected int damage;
    protected EntityType enemyFaction;

    public BulletEntity(World board, Vec2d position, Vec2d velocity, EntityType faction, EntityType enemyFaction, int damage) {
        super(board, position, velocity, faction);
        this.damage = damage;
        this.enemyFaction = enemyFaction;
    }

    @Override
    public void tick() {
        super.tick();
        if (position.x > world.size.x + visualSize) {
            this.markForDeletion();
        }
        if (position.y > world.size.y + visualSize) {
            this.markForDeletion();
        }
        if (position.x + visualSize < 0) {
            this.markForDeletion();
        }
        if (position.y + visualSize < 0) {
            this.markForDeletion();
        }
        for (Entity entity : world.getEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                if (entity.faction == enemyFaction) {
                    if (entity.squaredDistanceTo(this) < entity.squaredHitboxes(this)) {
                        livingEntity.damage(damage);
                        this.markForDeletion();
                    }
                }
            }
        }
    }
}
