package mialee.psychicmemory.game.entities.enemies;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.EnemyEntity;
import mialee.psychicmemory.math.Vec2d;

public class FlyRouteEnemy extends EnemyEntity {
    public FlyRouteEnemy(World board, Vec2d position) {
        super(board, position);
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.health = 3;
    }
}
