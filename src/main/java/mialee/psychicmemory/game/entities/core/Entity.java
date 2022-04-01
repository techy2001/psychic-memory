package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

import javax.swing.*;
import java.awt.*;

public abstract class Entity {
    public final World board;
    public final Vec2d position;
    public final Vec2d velocity;

    private String name;
    private ImageIcon image;
    private double hitRadius;
    private int visualSize;

    public Entity(World board, Vec2d position, Vec2d velocity) {
        this.board = board;
        this.position = position;
        this.velocity = velocity;
        registerStats();
    }

    protected void registerStats() {
        this.name = "";
        this.hitRadius = 20;
        this.visualSize = 50;
        this.image = PsychicMemory.getIcon("cod.png");
    }

    public void tick() {
        position.add(velocity);
    }

    public void render(Graphics graphics) {
        if (image == null) return;
        graphics.drawImage(image.getImage(),
                (int) position.x - (visualSize / 2),
                (int) position.y - (visualSize / 2),
                visualSize, visualSize, null);
    }

    public String getName() {
        return name;
    }

    public ImageIcon getImage() {
        return image;
    }

    public double getHitRadius() {
        return hitRadius;
    }

    public int getVisualSize() {
        return visualSize;
    }
}
