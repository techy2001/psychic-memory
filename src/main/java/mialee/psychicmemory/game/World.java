package mialee.psychicmemory.game;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.core.BossEntity;
import mialee.psychicmemory.game.entities.enemies.FlyRouteEnemy;
import mialee.psychicmemory.game.entities.enemies.RandomExtraEntity;
import mialee.psychicmemory.game.tasks.Task;
import mialee.psychicmemory.game.tasks.entitytasks.*;
import mialee.psychicmemory.game.tasks.tasks.WaitTask;
import mialee.psychicmemory.game.tasks.worldtasks.SpawnEntityTask;
import mialee.psychicmemory.math.MathHelper;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.math.Vec2i;
import mialee.psychicmemory.window.PMRenderer;

import java.awt.*;
import java.util.ArrayList;

public class World {
    private final EntityBank entities = new EntityBank(this);
    private PlayerEntity player;
    public final Vec2i size = new Vec2i(620, 720);
    private int score = 0;
    private int scoreOld = 0;
    private int scoreVisual = 0;
    private int scoreProgress = 0;
    private int highScore = DataManager.readHighScore().score();
    private final ArrayList<Task> taskList = new ArrayList<>();
    private final ArrayList<Task> pendingTasks = new ArrayList<>();

    public World() {
        setPlayer(new PlayerEntity(this, new Vec2d(310, 550), new Vec2d(0, 0)));
        populateTasks();
    }

    protected void populateTasks() {
//        addTask(new WaitTask(150));

        BossEntity flyRouteEnemy = new BossEntity(this, new Vec2d(size.x / 2, 100));
        flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(150, 200), 10));
        flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(100, 200), 10));
        addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//
//        for (int i = 0; i < 8; i++) {
//            addTask(new WaitTask(10));
//            FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(50, -50));
//            flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(150, 200), 100));
//            flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(size.x + 50, 200), 140));
//            flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//            addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//        }
//        addTask(new WaitTask(10));
//
//        for (int i = 0; i < 8; i++) {
//            addTask(new WaitTask(10));
//            FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(size.x - 50, -50));
//            flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(size.x - 150, 200), 100));
//            flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(-50, 200), 140));
//            flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//            addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//        }
//        addTask(new WaitTask(10));
//
//
//        for (int i = 0; i < 8; i++) {
//            addTask(new WaitTask(20));
//            RandomExtraEntity randomExtra = new RandomExtraEntity(this, new Vec2d(-50, 60), 0.1f);
//            randomExtra.setTask(new FireAtPlayerTask(randomExtra, 1, 12, 1, 15, 3));
//            randomExtra.addTask(new MoveWithVelocityTask(randomExtra, 100));
//            randomExtra.velocity.set(new Vec2d(3, 1f));
//            addTask(new SpawnEntityTask(this, randomExtra, EntityFaction.ENEMY));
//        }
//
//        for (int i = 0; i < 8; i++) {
//            addTask(new WaitTask(20));
//            RandomExtraEntity randomExtra = new RandomExtraEntity(this, new Vec2d(size.x + 50, 60), 0.1f);
//            randomExtra.setTask(new FireAtPlayerTask(randomExtra, 1, 12, 1, 15, 3));
//            randomExtra.addTask(new MoveWithVelocityTask(randomExtra, 100));
//            randomExtra.velocity.set(new Vec2d(-3, 1f));
//            addTask(new SpawnEntityTask(this, randomExtra, EntityFaction.ENEMY));
//        }
//
//
//        for (int i = 0; i < 8; i++) {
//            {
//                addTask(new WaitTask(5));
//                FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(50, -50));
//                flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(150, 200), 100));
//                flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(size.x - 150, 200), 140));
//                flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(150, size.y + 50), 140));
//                flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//                addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//            }
//            {
//                addTask(new WaitTask(5));
//                FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(size.x - 50, -50));
//                flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(size.x - 150, 200), 100));
//                flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(150, 200), 140));
//                flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d((float) size.x - 150, size.y + 50), 140));
//                flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//                addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//            }
//        }
//        addTask(new WaitTask(160));
//
//        for (int i = 0; i < 16; i++) {
//            addTask(new WaitTask(30));
//            double x = PsychicMemory.RANDOM.nextDouble(size.x - 100) + 50;
//            FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(x, -50));
//            flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(x, 100), 80));
//            flyRouteEnemy.addTask(new FireBulletsRoundTask(flyRouteEnemy, 1, 0, 6, 2));
//            flyRouteEnemy.addTask(new MoveToPositionTask(flyRouteEnemy, new Vec2d(x + (PsychicMemory.RANDOM.nextBoolean() ? -80 : 80), size.y + 50), 220));
//            flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//            addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//        }
//        addTask(new WaitTask(120));
//
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 4; j++) {
//                {
//                    addTask(new WaitTask(10));
//                    RandomExtraEntity randomExtra = new RandomExtraEntity(this, new Vec2d(-50, 0), 0.01f);
//                    randomExtra.setTask(new FireAtPlayerTask(randomExtra, 1, 120, 1, 15, 3));
//                    randomExtra.addTask(new MoveToPositionTask(randomExtra, new Vec2d(size.x + 50, 100 + (20 * i) + (60 * j)), 200));
//                    randomExtra.addTask(new DeleteSelfTask(randomExtra));
//                    randomExtra.setHealth(2);
//                    addTask(new SpawnEntityTask(this, randomExtra, EntityFaction.ENEMY));
//                }
//                {
//                    addTask(new WaitTask(10));
//                    RandomExtraEntity randomExtra = new RandomExtraEntity(this, new Vec2d(size.x + 50, 0), 0.01f);
//                    randomExtra.setTask(new FireAtPlayerTask(randomExtra, 1, 120, 1, 15, 3));
//                    randomExtra.addTask(new MoveToPositionTask(randomExtra, new Vec2d(-50, 100 + (20 * i) + (60 * j)), 200));
//                    randomExtra.addTask(new DeleteSelfTask(randomExtra));
//                    randomExtra.setHealth(2);
//                    addTask(new SpawnEntityTask(this, randomExtra, EntityFaction.ENEMY));
//                }
//            }
//        }
    }

    public void tick() {
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

        entities.tick();
        player.tick();
        if (scoreProgress < 100) scoreProgress++;
        scoreVisual = MathHelper.lerpInt((float) scoreProgress / 100, scoreOld, score);
        if (scoreVisual > highScore) highScore = scoreVisual;
    }

    public void addTask(Task task) {
        pendingTasks.add(task);
    }

    public void render(Graphics graphics) {
        entities.render(graphics);
        player.render(graphics);
    }

    public void renderUI(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.fillRect(size.x, 0, PMRenderer.dimensions.x - size.x, PMRenderer.dimensions.y);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(size.x, 0, PMRenderer.dimensions.x - size.x, PMRenderer.dimensions.y);

        if (PsychicMemory.gameState == GameState.PAUSED) {
            graphics.setFont(PMRenderer.getBaseFont().deriveFont(72f));
            int xOffset = graphics.getFontMetrics().stringWidth("PAUSED") / 2;
            int yOffset = graphics.getFontMetrics().getHeight() / 2;
            graphics.setColor(Color.BLACK);
            graphics.drawString("PAUSED", 311 - xOffset, 361 - yOffset);
            graphics.setColor(Color.WHITE);
            graphics.drawString("PAUSED", 310 - xOffset, 360 - yOffset);
        }

        graphics.setFont(PMRenderer.getBaseFont().deriveFont(24f));
        graphics.setColor(Color.BLACK);
        graphics.drawString("HIGH-SCORE: " + highScore, 651, 201);
        graphics.setColor(Color.WHITE);
        graphics.drawString("HIGH-SCORE: " + highScore, 650, 200);
        graphics.setColor(Color.BLACK);
        graphics.drawString("SCORE: " + getScoreVisual(), 651, 231);
        graphics.setColor(Color.WHITE);
        graphics.drawString("SCORE: " + getScoreVisual(), 650, 230);

        if (player != null) {
            graphics.setColor(Color.BLACK);
            graphics.drawString("LIFE: ", 651, 301);
            graphics.setColor(Color.WHITE);
            graphics.drawString("LIFE: ", 650, 300);
            for (int i = 0; i < player.getLives(); i++) {
                graphics.setColor(Color.RED);
                graphics.fillOval(715 + (i * 30), 300 - 20, 24, 24);
                graphics.setColor(Color.ORANGE);
                graphics.fillOval(717 + (i * 30), 302 - 20, 20, 20);
            }
        }
    }

    public EntityBank getBank() {
        return entities;
    }

    public void addScore(int value) {
        scoreProgress = 0;
        scoreOld = scoreVisual;
        score += value;
    }

    public int getScore() {
        return score;
    }

    public int getScoreVisual() {
        return scoreVisual;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
