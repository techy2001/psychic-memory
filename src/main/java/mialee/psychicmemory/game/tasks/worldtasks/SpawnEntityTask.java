package mialee.psychicmemory.game.tasks.worldtasks;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.WorldTask;

public class SpawnEntityTask extends WorldTask {
    private final Entity entity;
    private final EntityFaction faction;

    public SpawnEntityTask(World world, Entity entity, EntityFaction faction) {
        super(world, 1);
        this.entity = entity;
        this.faction = faction;
    }

    @Override
    public void tick() {
        world.getBank().addEntity(entity, faction);
        super.tick();
    }
}
