package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

import java.awt.*;

public class LivingEntity extends Entity {
    int health;

    public LivingEntity(BaseEntity type, World board, Vec2d position, Vec2d velocity) {
        super(type, board, position, velocity);
    }
}
