package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

import javax.swing.*;
import java.awt.*;

public abstract class Entity {
    private World board;
    public Vec2d position;
    public Vec2d velocity;
    private double rotation;

    public String name;
    public ImageIcon image;
    public double hitRadius;
    public int visualSize;
    public int health;

    public Entity createInstance(World board, Vec2d position, Vec2d velocity) {
        this.board = board;
        this.position = position;
        this.velocity = velocity;
        return this;
    }

    public Entity(String name, double hitRadius, int visualSize, int health, ImageIcon image) {
        this.name = name;
        this.hitRadius = hitRadius;
        this.visualSize = visualSize;
        this.health = health;
        this.image = image;
    }

    public void tick(float deltaTime) {
        position.add(velocity);
    }

    public void render(Graphics graphics) {
        if (image == null) return;
        graphics.drawImage(image.getImage(),
                (int) position.x - (image.getIconWidth() / 2),
                (int) position.y - (image.getIconHeight() / 2),
                visualSize, visualSize, null);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
