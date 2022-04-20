package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.ScoreTextEntity;
import mialee.psychicmemory.math.Vec2d;

public class EnemyEntity extends LivingEntity {
    private int score = 100;

    public EnemyEntity(World board, Vec2d position) {
        super(board, position, new Vec2d(0, 0));
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

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    protected void onDeath() {
        world.getBank().addEntity(new ScoreTextEntity(world, this.position.copy(), new Vec2d(0, -0.5), 40, score), EntityFaction.GRAPHIC);
        super.onDeath();
    }

    public double squaredDamageBoxes(PlayerEntity entity) {
        int damageRadius = 12;
        return damageRadius + entity.hitRadius;
    }
}
