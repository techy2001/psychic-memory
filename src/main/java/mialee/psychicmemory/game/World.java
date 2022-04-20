package mialee.psychicmemory.game;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.ScoreTextEntity;
import mialee.psychicmemory.game.entities.TestEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.entities.core.EntityFaction;
import mialee.psychicmemory.math.MathHelper;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.math.Vec2i;
import mialee.psychicmemory.window.PMRenderer;

import java.awt.*;
import java.util.ArrayList;

public class World {
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Entity> newEntities = new ArrayList<>();
    private PlayerEntity player;
    public final Vec2i size = new Vec2i(620, 720);
    private int score = 0;
    private int scoreOld = 0;
    private int scoreVisual = 0;
    private int scoreProgress = 0;
    private int highScore = DataManager.readHighScore().score();

    public World() {
        addEntity(new PlayerEntity(this, new Vec2d(310, 550), new Vec2d(0, 0), EntityFaction.PLAYER));
        populateTasks();
    }

    protected void populateTasks() {

        addEntity(new TestEntity(this, new Vec2d(0, 100), new Vec2d(3, 0), EntityFaction.ENEMY));
    }

    public void tick() {
        for (Entity entity : entities) {
            entity.tick();
        }
        entities.addAll(newEntities);
        newEntities.clear();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity != null) {
                if (entity.isMarkedForDeletion()) {
                    entities.remove(entity);
                    i--;
                }
            }
        }
        if (scoreProgress < 100) scoreProgress++;
        scoreVisual = MathHelper.lerpInt((float) scoreProgress / 100, scoreOld, score);
        if (scoreVisual > highScore) highScore = scoreVisual;
    }

    public void render(Graphics graphics) {
        for (int i = entities.size() - 1; 0 <= i; i--) {
            if (i < entities.size()) {
                Entity entity = entities.get(i);
                if (entity != null) entity.render(graphics);
            }
        }
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

    public void addEntity(Entity entity) {
        newEntities.add(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void clearBullets(boolean points) {
        for (Entity entity : entities) {
            if (entity.faction == EntityFaction.ENEMY_BULLET) {
                if (points) this.addEntity(new ScoreTextEntity(this, entity.position.copy(), new Vec2d(0, -0.5), 40, 10));
                entity.markForDeletion();
            }
        }
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
