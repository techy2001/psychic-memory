package mialee.psychicmemory.game;

import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.game.entities.ScoreTextEntity;
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
    public final Vec2i size;
    private int score = 0;
    private int scoreOld = 0;
    private int scoreVisual = 0;
    private int scoreProgress = 0;
    private int highScore = DataManager.readHighScore().score();

    public World(Vec2i size) {
        this.size = size;
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

        graphics.setFont(PMRenderer.getBaseFont().deriveFont(24f));
        graphics.setColor(Color.BLACK);
        graphics.drawString("HIGH-SCORE: " + highScore, 651, 101);
        graphics.setColor(Color.WHITE);
        graphics.drawString("HIGH-SCORE: " + highScore, 650, 100);
        graphics.setColor(Color.BLACK);
        graphics.drawString("SCORE: " + getScoreVisual(), 651, 151);
        graphics.setColor(Color.WHITE);
        graphics.drawString("SCORE: " + getScoreVisual(), 650, 150);
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
}
