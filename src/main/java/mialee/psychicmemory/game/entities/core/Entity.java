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

    private final ArrayList<EntityTask> taskList = new ArrayList<>();
    private final ArrayList<EntityTask> pendingTasks = new ArrayList<>();
    protected int age = 0;
    private boolean markedForDeletion = false;

    public Entity(World world, Vec2d position, Vec2d velocity, EntityType faction) {
        this.world = world;
        this.position = position;
        this.velocity = velocity;
        this.faction = faction;
        this.registerStats();
        this.initializeTasks();
    }

    protected void initializeTasks() {
        addTask(new MoveWithVelocityTask(this, Integer.MAX_VALUE, true));
    }

    protected void registerStats() {
        this.name = "";
        this.hitRadius = 40;
        this.visualSize = 50;
    }

    public void tick() {
        age++;
        if (!taskList.isEmpty()) {
            EntityTask task = taskList.get(0);
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
        graphics.drawImage(image.getImage(),
                (int) (position.x - (visualSize / 2)),
                (int) (position.y - (visualSize / 2)),
                visualSize, visualSize, null);
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
        return (((this.position.x - entity.position.x) * (this.position.x - entity.position.x)) + ((this.position.y - entity.position.y) * (this.position.y - entity.position.y))) * 2;
    }

    public double squaredHitboxes(Entity entity) {
        return this.hitRadius * entity.hitRadius * 4;
    }

    public void addTask(Task task) {
        pendingTasks.add((EntityTask) task);
    }
}
