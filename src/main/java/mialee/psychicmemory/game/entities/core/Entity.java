package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.tasks.Task;
import mialee.psychicmemory.game.tasks.entitytasks.MoveWithVelocityTask;
import mialee.psychicmemory.math.Vec2d;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * The basic entity class used for all entities which exist within the {@link mialee.psychicmemory.game.World}.
 */
public abstract class Entity {
    public final World world;
    public final Vec2d position;
    public final Vec2d velocity;

    public String name;
    public ImageIcon image;
    public int hitRadius;
    public int visualSize;

    protected final ArrayList<Task> taskList = new ArrayList<>();
    protected final ArrayList<Task> pendingTasks = new ArrayList<>();
    protected int age = 0;
    private boolean markedForDeletion = false;

    /**
     * Instantiates an Entity.
     * Also registers stats.
     * @param world The world to spawn the Entity within.
     * @param position The position to spawn at in the world.
     * @param velocity Velocity for use by certain tasks.
     */
    public Entity(World world, Vec2d position, Vec2d velocity) {
        this.world = world;
        this.position = position;
        this.velocity = velocity;
        this.registerStats();
    }

    /**
     * Sets up tasks for an entity to follow.
     */
    protected void initializeTasks() {
        addTask(new MoveWithVelocityTask(this, Integer.MAX_VALUE));
    }

    /**
     * Registers stats for the entity to have.
     */
    protected void registerStats() {
        this.name = "";
        this.hitRadius = 20;
        this.visualSize = 20;
    }

    /**
     * Allows the entity to perform its actions, runs 60 times a second.
     * Tasks are done in the order they appear on the task list, moving onto the next when the current one is complete.
     * Complete tasks are either removed or put to the end of the list.
     */
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

    /**
     * Images are drawn centered on the entity, which makes a lot of other code much easier to maintain.
     * @param graphics Graphics to draw the image to.
     */
    public void render(Graphics graphics) {
        if (image == null) image = PsychicMemory.missingTexture;
        graphics.drawImage(image.getImage(), (int) (position.x - visualSize), (int) (position.y - visualSize), visualSize * 2, visualSize * 2, null);
    }

    public int getHitRadius() {
        return hitRadius;
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    /**
     * Marks an entity to be removed, which will happen on the next tick.
     */
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

    /**
     * Adds a task to the pending tasks list.
     * This list is used to prevent {@link java.util.ConcurrentModificationException}s.
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        pendingTasks.add(task);
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}
