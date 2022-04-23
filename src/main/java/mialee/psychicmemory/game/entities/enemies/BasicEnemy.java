package mialee.psychicmemory.game.entities.enemies;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.EnemyEntity;
import mialee.psychicmemory.math.Vec2d;

/**
 * Basic enemy instance with 3 health.
 */
public class BasicEnemy extends EnemyEntity {
    public BasicEnemy(World board, Vec2d position) {
        super(board, position);
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.health = 3;
    }
}
