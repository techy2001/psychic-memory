package mialee.psychicmemory.game;

import mialee.psychicmemory.game.entities.core.Entity;

import java.awt.*;
import java.util.ArrayList;

public class World {
    public final ArrayList<Entity> entities = new ArrayList<>();

    public void tick(float deltaTime) {
        for (Entity entity : entities) {
            entity.tick(deltaTime);
        }
    }

    public void render(Graphics graphics) {
        for (Entity entity : entities) {
            entity.render(graphics);
        }
    }
}
