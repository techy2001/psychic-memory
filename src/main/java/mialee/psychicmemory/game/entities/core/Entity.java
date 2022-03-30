package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

import java.awt.*;

public abstract class Entity {
    private final World board;
    public final Vec2d position;
    public final Vec2d velocity;
    private double rotation;
    private final BaseEntity type;

    public Entity(BaseEntity type, World board, Vec2d position, Vec2d velocity) {
        this.type = type;
        this.board = board;
        this.position = position;
        this.velocity = velocity;
    }

    public void tick(float deltaTime) {
        position.add(velocity);
    }

    public void render(Graphics graphics) {
        if (type.image == null) return;
        graphics.drawImage(type.image.getImage(),
                (int) position.x - (type.image.getIconWidth() / 2),
                (int) position.y - (type.image.getIconHeight() / 2),
                type.visualSize, type.visualSize, null);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }


}
