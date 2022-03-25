package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.game.Board;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.window.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    private final Board board;
    public final Vec2d position;
    public final Vec2d velocity;
    private double rotation;
    private final EntityType type;

    public Entity(EntityType type, Board board, Vec2d position, Vec2d velocity) {
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
        graphics.drawImage(type.image,
                (int) position.x - (type.image.getWidth() / 2),
                (int) position.y - (type.image.getHeight() / 2),
                type.visualSize, type.visualSize, null);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
