package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {
    public final World board;
    public final Vec2d position;
    public final Vec2d velocity;
    public final EntityType faction;

    private String name;
    private ImageIcon image;
    private double hitRadius;
    private int visualSize;

    private ArrayList<EntityTask> taskList = new ArrayList<>();
    private ArrayList<EntityTask> pendingTasks = new ArrayList<>();
    private int age = 0;

    public Entity(World board, Vec2d position, Vec2d velocity, EntityType faction) {
        this.board = board;
        this.position = position;
        this.velocity = velocity;
        this.faction = faction;
        registerStats();
    }

    protected void registerStats() {
        this.name = "";
        this.hitRadius = 20;
        this.visualSize = 50;
        this.image = PsychicMemory.getIcon("cod.png");
    }

    public void tick() {
        age++;
        if (taskList.get(0).isComplete()) {
            taskList.remove(0);
        }
        taskList.get(0).tick();

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
