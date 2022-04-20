package mialee.psychicmemory.game;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.TestEntity;
import mialee.psychicmemory.game.tasks.Task;
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
        addTask(new SpawnEntityTask(this, new TestEntity(this, new Vec2d(0, 100), new Vec2d(3, 0)), EntityFaction.ENEMY));
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
