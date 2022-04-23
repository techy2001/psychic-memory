package mialee.psychicmemory.game.entities.bosses;

import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.PlayerBulletEntity;
import mialee.psychicmemory.game.entities.core.EnemyEntity;
import mialee.psychicmemory.game.entities.visuals.ScoreTextEntity;
import mialee.psychicmemory.game.tasks.Task;
import mialee.psychicmemory.game.tasks.entitytasks.DeleteSelfTask;
import mialee.psychicmemory.game.tasks.entitytasks.FireBulletsRoundSpinTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveToPositionLerpTask;
import mialee.psychicmemory.game.tasks.tasks.WaitTask;
import mialee.psychicmemory.math.MathHelper;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * An instance of an {@link mialee.psychicmemory.game.entities.core.EnemyEntity} with more advanced features and a visible health bar.
 * BossEntities can also make use of {@link #lives}, similar to PlayerEntities.
 */
public class BossEntity extends EnemyEntity {
    public int lives;
    public int maxHealth;
    private final ArrayList<BossPhase> phases = new ArrayList<>();
    private int phase = 0;
    private int phaseCooldown = 0;
    private double healthOld = 0;
    private double healthVisual = 0;
    private int healthProgress = 0;
    private boolean inSubPhase = false;
    private boolean isEndBoss = false;
    private int armour = 0;

    public BossEntity(World board, Vec2d position) {
        super(board, position);
    }

    /**
     * More advanced tick logic is used for the BossEntity, as it uses a more complex task system, in this case being the Phase system.
     * Each individual phase can contain its own tasks, and the BossEntity will cycle through phases as it goes on and loses lives.
     * The boss also uses health variables to make the health bar displayed while alive a more fluid movement.
     */
    @Override
    public void tick() {
        age++;
        if (!taskList.isEmpty()) {
            Task task = taskList.get(0);
            task.tick();
            if (task.isComplete()) {
                taskList.remove(0);
            }
        } else if (phaseCooldown <= 0) {
            if (phases.size() > phase) {
                SubPhase current = !inSubPhase ? phases.get(phase) : phases.get(phase).getSubPhase();
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
        if (healthProgress < 10) healthProgress++;
        healthVisual = MathHelper.lerpDouble((float) healthProgress / 10, healthOld, health);
    }

    /**
     * Additional logic to draw the boss health bar.
     * @param graphics Graphics to draw to.
     */
    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.setFont(graphics.getFont().deriveFont(24f));
        graphics.setColor(new Color(19, 25, 41));
        graphics.drawString(String.valueOf(lives), 11, (9 + graphics.getFontMetrics().getHeight() / 2) - phaseCooldown);
        graphics.setColor(new Color(202, 199, 220));
        graphics.drawString(String.valueOf(lives), 10, (8 + graphics.getFontMetrics().getHeight() / 2) - phaseCooldown);
        graphics.setColor(inSubPhase ? new Color(219, 41, 41) : new Color(245, 87, 68));
        graphics.fillRect(32, 12 - phaseCooldown, (int) MathHelper.clampDouble(0, ((float) age / 80) * 580, ((float) healthVisual / maxHealth) * 580), 10);
        graphics.setColor(new Color(19, 25, 41));
        graphics.drawRect(32, 12 - phaseCooldown, (int) MathHelper.clampDouble(0, ((float) age / 80) * 580, ((float) healthVisual / maxHealth) * 580), 10);
    }

    /**
     * Additional logic is present to allow phases to use their subPhase mechanic.
     * The armour mechanic is also present here, armour gives the boss much more survivability when in its subPhases.
     * @param amount Amount of damage to take.
     * @return Returns true if damage has been taken.
     */
    @Override
    public boolean damage(int amount) {
        if (phaseCooldown >= 1) return false;
        healthProgress = 0;
        healthOld = healthVisual;
        if (phases.size() > phase) {
            BossPhase currentPhase = phases.get(phase);
            if (!inSubPhase && currentPhase.getSubPhaseTrigger() >= (float) health / maxHealth && currentPhase.getSubPhase() != null) {
                inSubPhase = true;
                world.getBank().clearBullets(true);
                addTask(new WaitTask(10));
            }
        }
        if (armour > 0) {
            armour--;
            return true;
        } else if (inSubPhase) {
            armour = 10;
        }
        return super.damage(amount);
    }

    /**
     * The death method for a BossEntity is much more advanced than on any normal entity.
     * For one, it has lives, and uses these similarly to how a player does.
     * When a boss loses a life it will give score and progress forward through a phase, as well as restore to full health.
     * The boss will also gain "phaseCooldown" which is similar to the "iFrames" of a player, ignoring damage and performing some visual effects such as moving the health bar.
     * If a boss is marked as an end boss it will also have a much fancier death animation on its final life and will end the game shortly after.
     */
    @Override
    protected void onDeath() {
        world.getBank().clearBullets(true);
        inSubPhase = false;
        if (lives > 0) {
            world.getBank().addEntity(new ScoreTextEntity(world, this.position.copy(), new Vec2d(0, -0.5), 60, (score / 10) / lives), EntityFaction.GRAPHIC);
            lives--;
            if (phases.get(phase).getEndTask() != null) this.addTask(phases.get(phase).getEndTask());
            phase++;
            phaseCooldown = 100;
            health = maxHealth;
        } else {
            if (isEndBoss) {
                addTask(new FireBulletsRoundSpinTask(this, 40, 0, 12, 6, 25) {
                    @Override
                    public void tick() {
                        if (tick == 0) refresh();
                        tick++;
                        if (tick >= length) {
                            complete = true;
                            world.getBank().addEntity(new ScoreTextEntity(world, owner.position.copy(), new Vec2d(0, -0.5), (int) (40 * (MathHelper.clampDouble(1f, 2f, (float) score / 100))), score), EntityFaction.GRAPHIC);
                        }
                        if (cooldown <= 0) {
                            for (int i = 0; i < count; i++) {
                                double f = Math.sin(((((float) 360 / count) * i) + (shot * spin)) * ((float) Math.PI / 180));
                                double h = Math.cos(((((float) 360 / count) * i) + (shot * spin)) * ((float) Math.PI / 180));
                                owner.world.getBank().addEntity(new PlayerBulletEntity(owner.world, owner.position.copy(), new Vec2d(f * speed, h * speed), 0), EntityFaction.PLAYER_BULLET);
                            }
                            cooldown = cooldownMax;
                            shot++;
                        }
                        cooldown--;
                    }
                });
                world.getBank().addEntity(new GameEndEntity(world, 280), EntityFaction.GRAPHIC);
            } else {
                addTask(new MoveToPositionLerpTask(this, new Vec2d(position.x, -100), 4, 120));
                world.getBank().addEntity(new ScoreTextEntity(world, this.position.copy(), new Vec2d(0, -0.5), (int) (40 * (MathHelper.clampDouble(1f, 2f, (float) score / 100))), score), EntityFaction.GRAPHIC);
            }
            phaseCooldown = 100;
            addTask(new DeleteSelfTask(this));
        }
    }

    /**
     * Adds a phase to the phase queue.
     * @param tasks Task list for the phase.
     */
    public void addPhase(ArrayList<Task> tasks) {
        phases.add(new BossPhase(tasks));
    }

    /**
     * Adds a phase to the phase queue, containing a subPhase.
     * @param tasks Task list for the phase.
     * @param subtasks Task list for the subPhase.
     * @param ratio Health ratio to reach before activating the subPhase.
     */
    public void addPhase(ArrayList<Task> tasks, ArrayList<Task> subtasks, float ratio) {
        BossPhase phase = new BossPhase(tasks);
        phase.setSubPhase(new SubPhase(subtasks), ratio);
        phases.add(phase);
    }

    /**
     * @return {@link #inSubPhase} getter.
     */
    public boolean isInSubPhase() {
        return inSubPhase;
    }

    /**
     * @param isEndBoss {@link #isEndBoss} setter.
     */
    public void setEndBoss(boolean isEndBoss) {
        this.isEndBoss = isEndBoss;
    }
}
