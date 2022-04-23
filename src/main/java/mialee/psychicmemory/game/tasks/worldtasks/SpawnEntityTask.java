package mialee.psychicmemory.game.tasks.worldtasks;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.bosses.BossEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.WorldTask;

/**
 * Task used by the world to summon an entity.
 * Integral to the level layout.
 */
public class SpawnEntityTask extends WorldTask {
    private final Entity entity;
    private final EntityFaction faction;

    public SpawnEntityTask(World world, Entity entity, EntityFaction faction) {
        super(world, 1);
        this.entity = entity;
        this.faction = faction;
    }

    /**
     * Marks the entity as the last boss if the entity is one.
     * Important for certain game states.
     */
    @Override
    public void tick() {
        world.getBank().addEntity(entity, faction);
        if (entity instanceof BossEntity boss) world.setLastBoss(boss);
        super.tick();
    }
}
