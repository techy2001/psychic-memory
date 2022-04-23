package mialee.psychicmemory.game;

import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.entities.visuals.ScoreTextEntity;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * The world's entity bank.
 * This stores and manages all the world's entities, both rendering and ticking them.
 * Notably does not store the Player entity, as that is managed by the world itself.
 */
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

    /**
     * @param world The world who owns this bank.
     */
    public EntityBank(World world) {
        this.world = world;
    }

    /**
     * Ticks every entity and adds new entities to their main lists (from their pending lists).
     * Entities are kept in their own categories in order to majorly speed up certain tasks (for example, clearing all bullets in the enemy bullets bank is much quicker than checking every entity for if it is an enemy bullet then clearing it).
     */
    public void tick() {
        for (Entity entity : visuals) {
            entity.tick();
        }
        for (Entity entity : enemies) {
            entity.tick();
        }
        for (Entity entity : enemyBullets) {
            entity.tick();
        }
        for (Entity entity : playerBullets) {
            entity.tick();
        }

        addAllNew();
    }

    /**
     * Entities are drawn in order of importance, with enemy projectiles being the most important and visuals being the least.
     * @param graphics Graphics to draw the entities to.
     */
    public void render(Graphics graphics) {
        for (int i = visuals.size() - 1; 0 <= i; i--) {
            if (i < visuals.size()) {
                Entity entity = visuals.get(i);
                if (entity != null) entity.render(graphics);
            }
        }
        for (int i = enemies.size() - 1; 0 <= i; i--) {
            if (i < enemies.size()) {
                Entity entity = enemies.get(i);
                if (entity != null) entity.render(graphics);
            }
        }
        for (int i = playerBullets.size() - 1; 0 <= i; i--) {
            if (i < playerBullets.size()) {
                Entity entity = playerBullets.get(i);
                if (entity != null) entity.render(graphics);
            }
        }
        for (int i = enemyBullets.size() - 1; 0 <= i; i--) {
            if (i < enemyBullets.size()) {
                Entity entity = enemyBullets.get(i);
                if (entity != null) entity.render(graphics);
            }
        }
    }

    /**
     * @param entity Entity to add to it's appropriate ArrayList.
     * @param faction The faction dictates which list to add the entity to.
     */
    public void addEntity(Entity entity, EntityFaction faction) {
        switch (faction) {
            case ENEMY -> newEnemies.add(entity);
            case ENEMY_BULLET -> newEnemyBullets.add(entity);
            case PLAYER_BULLET -> newPlayerBullets.add(entity);
            case GRAPHIC -> newVisuals.add(entity);
        }
    }

    /**
     * @param faction Faction to grab.
     * @return Grabs an entity arraylist by faction.
     */
    public ArrayList<Entity> getEntities(EntityFaction faction) {
        return switch (faction) {
            case ENEMY -> enemies;
            case ENEMY_BULLET -> enemyBullets;
            case PLAYER_BULLET -> playerBullets;
            default -> newVisuals;
        };
    }

    /**
     * Dumps all the pending entities into their arraylists.
     */
    public void addAllNew() {
        addNew(enemies, newEnemies);
        addNew(enemyBullets, newEnemyBullets);
        addNew(playerBullets, newPlayerBullets);
        addNew(visuals, newVisuals);
    }

    /**
     * Adds all entities from the pending list into the primary list, clearing the pending list.
     * Also removes any entities marked for deletion.
     * @param main Primary list.
     * @param income Pending list.
     */
    private void addNew(ArrayList<Entity> main, ArrayList<Entity> income) {
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

    /**
     * Marks all enemy bullets for deletion, used mostly by bosses on phase changes.
     * @param points Should points be awarded for deleted bullets.
     */
    public void clearBullets(boolean points) {
        for (Entity entity : enemyBullets) {
            if (points) this.addEntity(new ScoreTextEntity(world, entity.position.copy(), new Vec2d(0, -0.5), 40, 10), EntityFaction.GRAPHIC);
            entity.markForDeletion();
        }
    }
}
