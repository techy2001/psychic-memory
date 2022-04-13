package mialee.psychicmemory.game;

import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.math.Vec2i;

import java.awt.*;
import java.util.ArrayList;

public class World {
    public final ArrayList<Entity> entities = new ArrayList<>();
    public final Vec2i size;
    private int gameState;

    public World(Vec2i size) {
        this.size = size;
    }

    public void tick() {
        for (Entity entity : entities) {
            entity.tick();
        }
    }

    public void render(Graphics graphics) {
        for (Entity entity : entities) {
            entity.render(graphics);
        }
    }
}
