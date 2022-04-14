package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.game.tasks.Task;
import mialee.psychicmemory.game.tasks.entitytasks.MoveWithVelocityTask;
import mialee.psychicmemory.math.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {
    public final World world;
    public final Vec2d position;
    public final Vec2d velocity;
    public final EntityType faction;

    protected String name;
    protected ImageIcon image;
    protected int hitRadius;
    protected int visualSize;

    private final ArrayList<Task> taskList = new ArrayList<>();
    private final ArrayList<Task> pendingTasks = new ArrayList<>();
    protected int age = 0;
    private boolean markedForDeletion = false;

    public Entity(World world, Vec2d position, Vec2d velocity, EntityType faction) {
        this.world = world;
        this.position = position;
        this.velocity = velocity;
        this.faction = faction;
        this.registerStats();
    }

    protected void initializeTasks() {
        addTask(new MoveWithVelocityTask(this, Integer.MAX_VALUE));
    }

    protected void registerStats() {
        this.name = "";
        this.hitRadius = 20;
        this.visualSize = 30;
    }

    public void tick() {
        if (age == 1) initializeTasks();
        age++;
        if (!taskList.isEmpty()) {
            Task task = taskList.get(0);
            task.tick();
            if (task.isComplete()) {
                if (task.shouldLoop()) {
                    pendingTasks.add(task);
                    task.refresh();
                }
                taskList.remove(0);
            }
        }
        taskList.addAll(pendingTasks);
        pendingTasks.clear();
    }

    public void render(Graphics graphics) {
        if (image == null) image = PsychicMemory.missingTexture;
        graphics.drawImage(image.getImage(), (int) (position.x - visualSize), (int) (position.y - visualSize), visualSize * 2, visualSize * 2, null);
    }

    public ImageIcon getImage() {
        return image;
    }

    public int getHitRadius() {
        return hitRadius;
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    public void markForDeletion() {
        this.markedForDeletion = true;
    }

    public double squaredDistanceTo(Entity entity) {
        return Math.sqrt(Math.pow(Math.abs((this.position.x - entity.position.x)), 2) +
                Math.pow(Math.abs((this.position.y - entity.position.y)), 2));
    }

    public double squaredHitboxes(Entity entity) {
        return this.hitRadius + entity.hitRadius;
    }

    public void addTask(Task task) {
        pendingTasks.add(task);
    }
}
