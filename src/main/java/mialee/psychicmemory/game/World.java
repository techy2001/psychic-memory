package mialee.psychicmemory.game;

import mialee.psychicmemory.game.entities.ScoreTextEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.entities.core.EntityType;
import mialee.psychicmemory.math.MathHelper;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.math.Vec2i;

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
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void render(Graphics graphics) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity != null) entity.render(graphics);
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
            if (entity.faction == EntityType.ENEMY_BULLET) {
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
