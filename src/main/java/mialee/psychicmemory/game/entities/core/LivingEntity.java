package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

public class LivingEntity extends Entity {
    private int health;

    public LivingEntity(World board, Vec2d position, Vec2d velocity) {
        super(board, position, velocity);
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.health = 5;
    }
}
