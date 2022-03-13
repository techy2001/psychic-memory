package mialee.psychicmemory.game;

import mialee.psychicmemory.game.entities.Entity;

import java.awt.*;
import java.util.ArrayList;

public class Board {
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
