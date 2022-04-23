package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

/**
 * Any entity with health, allowing it to take damage and be defeated.
 */
public abstract class LivingEntity extends Entity {
    public int health;

    /**
     * Instantiates a LivingEntity.
     * @param world The world to spawn the LivingEntity within.
     * @param position The position to spawn at in the world.
     * @param velocity Velocity for use by certain tasks.
     */
    public LivingEntity(World world, Vec2d position, Vec2d velocity) {
        super(world, position, velocity);
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.health = 1;
    }

    /**
     * Takes damage, reducing health by amount and triggering {@link #onDeath()} if health is reduced to or below 0.
     * @param amount Amount of damage to take.
     * @return Returns true if damage was taken.
     */
    public boolean damage(int amount) {
        health -= amount;
        if (health <= 0) {
            this.onDeath();
        }
        return true;
    }

    /**
     * Marks the entity for deletion, overridden on more complex entities.
     */
    protected void onDeath() {
        this.markForDeletion();
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
