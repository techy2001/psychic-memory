package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.BaseEntity;
import mialee.psychicmemory.game.entities.core.LivingEntity;
import mialee.psychicmemory.math.Vec2d;

public class AlienEntity extends LivingEntity {
    public AlienEntity(BaseEntity type, World board, Vec2d position, Vec2d velocity) {
        super(type, board, position, velocity);
    }
}
