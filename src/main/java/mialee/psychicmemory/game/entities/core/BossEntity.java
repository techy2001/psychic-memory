package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.ScoreTextEntity;
import mialee.psychicmemory.game.entities.bosses.BossPhase;
import mialee.psychicmemory.game.tasks.Task;
import mialee.psychicmemory.game.tasks.entitytasks.DeleteSelfTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveToPositionLerpTask;
import mialee.psychicmemory.math.MathHelper;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class BossEntity extends EnemyEntity {
    public int lives;
    public int maxHealth;
    private final ArrayList<BossPhase> phases = new ArrayList<>();
    private int phase = 0;
    private int phaseCooldown = 0;

    public BossEntity(World board, Vec2d position) {
        super(board, position);
    }

    @Override
    public void tick() {
        if (!taskList.isEmpty()) {
            Task task = taskList.get(0);
            task.tick();
            if (task.isComplete()) {
                taskList.remove(0);
            }
        } else if (phaseCooldown <= 0) {
            if (phases.size() > phase) {
                BossPhase current = phases.get(phase);
                if (!current.taskList.isEmpty()) {
                    Task task = current.taskList.get(0);
                    task.tick();
                    if (task.isComplete()) {
                        if (task.shouldLoop()) {
                            current.pendingTasks.add(task);
                            task.refresh();
                        }
                        current.taskList.remove(0);
                    }
                }
                current.taskList.addAll(current.pendingTasks);
                current.pendingTasks.clear();
            }
        } else {
            phaseCooldown--;
        }
        taskList.addAll(pendingTasks);
        pendingTasks.clear();
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.setFont(graphics.getFont().deriveFont(24f));
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(lives), 11, (9 + graphics.getFontMetrics().getHeight() / 2) - phaseCooldown);
        graphics.setColor(Color.WHITE);
        graphics.drawString(String.valueOf(lives), 10, (8 + graphics.getFontMetrics().getHeight() / 2) - phaseCooldown);
        graphics.setColor(Color.RED);
        graphics.fillRect(32, 12 - phaseCooldown, 580, 10);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(32, 12 - phaseCooldown, 580, 10);
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(32, 12 - phaseCooldown, (int) (((float) health / maxHealth) * 580), 10);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(32, 12 - phaseCooldown, (int) (((float) health / maxHealth) * 580), 10);
    }

    @Override
    public boolean damage(int amount) {
        if (phaseCooldown >= 1) return false;
        return super.damage(amount);
    }

    @Override
    protected void onDeath() {
        world.getBank().clearBullets(true);
        if (lives > 0) {
            world.getBank().addEntity(new ScoreTextEntity(world, this.position.copy(), new Vec2d(0, -0.5), 60, (score / 10) / lives), EntityFaction.GRAPHIC);
            lives--;
            if (phases.get(phase).getEndTask() != null) this.addTask(phases.get(phase).getEndTask());
            phase++;
            phaseCooldown = 100;
            health = maxHealth;
        } else {
            world.getBank().addEntity(new ScoreTextEntity(world, this.position.copy(), new Vec2d(0, -0.5), (int) (40 * (MathHelper.clampDouble(1f, 2f, (float) score / 100))), score), EntityFaction.GRAPHIC);
            phaseCooldown = 100;
            addTask(new MoveToPositionLerpTask(this, new Vec2d(position.x, -100), 120));
            addTask(new DeleteSelfTask(this));
        }
    }

    public void addPhase(ArrayList<Task> tasks) {
        phases.add(new BossPhase(tasks));
    }
}
