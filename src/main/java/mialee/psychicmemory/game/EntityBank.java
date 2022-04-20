package mialee.psychicmemory.game;

import mialee.psychicmemory.game.entities.ScoreTextEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.math.Vec2d;

import java.awt.*;
import java.util.ArrayList;

public class EntityBank {
    private final World world;
    // Enemy Entities
    private final ArrayList<Entity> enemies = new ArrayList<>();
    private final ArrayList<Entity> newEnemies = new ArrayList<>();
    // Enemy Bullets
    private final ArrayList<Entity> enemyBullets = new ArrayList<>();
    private final ArrayList<Entity> newEnemyBullets = new ArrayList<>();
    // Player Bullets
    private final ArrayList<Entity> playerBullets = new ArrayList<>();
    private final ArrayList<Entity> newPlayerBullets = new ArrayList<>();
    // Visuals
    private final ArrayList<Entity> visuals = new ArrayList<>();
    private final ArrayList<Entity> newVisuals = new ArrayList<>();

    public EntityBank(World world) {
        this.world = world;
    }

    public void tick() {
        for (Entity entity : enemies) {
            entity.tick();
        }
        for (Entity entity : enemyBullets) {
            entity.tick();
        }
        for (Entity entity : playerBullets) {
            entity.tick();
        }
        for (Entity entity : visuals) {
            entity.tick();
        }

        addNew(enemies, newEnemies);
        addNew(enemyBullets, newEnemyBullets);
        addNew(playerBullets, newPlayerBullets);
        addNew(visuals, newVisuals);
    }

    public void render(Graphics graphics) {
        for (int i = enemies.size() - 1; 0 <= i; i--) {
            if (i < enemies.size()) {
                Entity entity = enemies.get(i);
                if (entity != null) entity.render(graphics);
            }
        }
        for (int i = enemyBullets.size() - 1; 0 <= i; i--) {
            if (i < enemyBullets.size()) {
                Entity entity = enemyBullets.get(i);
                if (entity != null) entity.render(graphics);
            }
        }
        for (int i = playerBullets.size() - 1; 0 <= i; i--) {
            if (i < playerBullets.size()) {
                Entity entity = playerBullets.get(i);
                if (entity != null) entity.render(graphics);
            }
        }
        for (int i = visuals.size() - 1; 0 <= i; i--) {
            if (i < visuals.size()) {
                Entity entity = visuals.get(i);
                if (entity != null) entity.render(graphics);
            }
        }
    }

    public void addEntity(Entity entity, EntityFaction faction) {
        switch (faction) {
            case ENEMY -> newEnemies.add(entity);
            case ENEMY_BULLET -> newEnemyBullets.add(entity);
            case PLAYER_BULLET -> newPlayerBullets.add(entity);
            case GRAPHIC -> newVisuals.add(entity);
        }
    }

    public ArrayList<Entity> getEntities(EntityFaction faction) {
        return switch (faction) {
            case ENEMY -> newEnemies;
            case ENEMY_BULLET -> newEnemyBullets;
            case PLAYER_BULLET -> newPlayerBullets;
            default -> newVisuals;
        };
    }

    public void addNew(ArrayList<Entity> main, ArrayList<Entity> income) {
        main.addAll(income);
        income.clear();
        for (int i = 0; i < main.size(); i++) {
            Entity entity = main.get(i);
            if (entity != null) {
                if (entity.isMarkedForDeletion()) {
                    main.remove(entity);
                    i--;
                }
            }
        }
    }

    public void clearBullets(boolean points) {
        for (Entity entity : enemyBullets) {
            if (points) this.addEntity(new ScoreTextEntity(world, entity.position.copy(), new Vec2d(0, -0.5), 40, 10), EntityFaction.GRAPHIC);
            entity.markForDeletion();
        }
    }
}
