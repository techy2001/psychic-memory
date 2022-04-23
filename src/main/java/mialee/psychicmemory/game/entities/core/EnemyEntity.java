package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.visuals.ScoreTextEntity;
import mialee.psychicmemory.math.MathHelper;
import mialee.psychicmemory.math.Vec2d;

/**
 * A form of LivingEntity which contains a score, which will be given to the player if the enemy is killed.
 * Also has a damage box, meaning it can damage the player on contact.
 */
public abstract class EnemyEntity extends LivingEntity {
    public int score;

    public EnemyEntity(World board, Vec2d position) {
        super(board, position, new Vec2d(0, 0));
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.score = 100;
    }

    @Override
    public void tick() {
        super.tick();
        PlayerEntity player = world.getPlayer();
        if (player != null) {
            if (player.squaredDistanceTo(this) < this.squaredDamageBoxes(player)) {
                player.damage(1);
            }
        }
        if (position.x > world.size.x * 2 || position.y > world.size.y * 2 || position.x < -world.size.x || position.y < -world.size.y) {
            this.markForDeletion();
        }
    }

    @Override
    protected void onDeath() {
        world.getBank().addEntity(new ScoreTextEntity(world, this.position.copy(), new Vec2d(0, -0.5), (int) (40 * (MathHelper.clampDouble(1f, 2f, (float) score / 100))), score), EntityFaction.GRAPHIC);
        super.onDeath();
    }

    public double squaredDamageBoxes(PlayerEntity entity) {
        int damageRadius = 12;
        return damageRadius + entity.hitRadius;
    }
}
