package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

public abstract class LivingEntity extends Entity {
    protected int health;

    public LivingEntity(World board, Vec2d position, Vec2d velocity, EntityType faction) {
        super(board, position, velocity, faction);
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.health = 1;
    }

    public boolean damage(int amount) {
        health -= amount;
        if (health <= 0) {
            this.onDeath();
        }
        return true;
    }

    protected void onDeath() {
        this.markForDeletion();
    }
}
