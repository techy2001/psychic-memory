package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.game.Board;
import mialee.psychicmemory.math.Vec2d;

public class Entity {
    private final Board board;
    private final EntityType type;

    public final Vec2d position = new Vec2d(0, 0);
    public final Vec2d velocity = new Vec2d(0, 0);
    private double rotation;

    public Entity(Board board, EntityType type) {
        this.board = board;
        this.type = type;
    }

    public void tickMovement() {
        position.add(velocity);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
