package mialee.psychicmemory.game.entities.enemies;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.core.BulletEntity;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Color;

/**
 * Enemy bullets which will damage the player when near enough.
 * Comes prepackaged in the colour red.
 */
public class EnemyBulletEntity extends BulletEntity {
    public EnemyBulletEntity(World board, Vec2d position, Vec2d velocity) {
        super(board, position, velocity, 1);
        this.color = Color.RED;
    }

    /**
     * Damages the player if near enough.
     */
    @Override
    public void tick() {
        super.tick();
        PlayerEntity player = world.getPlayer();
        if (player != null) {
            if (player.squaredDistanceTo(this) < player.squaredHitboxes(this)) {
                if (player.damage(1)) {
                    markForDeletion();
                }
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
