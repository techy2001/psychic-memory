package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.BulletEntity;
import mialee.psychicmemory.game.entities.core.EntityType;
import mialee.psychicmemory.math.Vec2d;

import java.awt.*;

public class EnemyBulletEntity extends BulletEntity {
    public EnemyBulletEntity(World board, Vec2d position, Vec2d velocity) {
        super(board, position, velocity, EntityType.ENEMY_BULLET, EntityType.PLAYER, 1);
        this.color = Color.RED;
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.hitRadius = 8;
        this.visualSize = 10;
    }
}
