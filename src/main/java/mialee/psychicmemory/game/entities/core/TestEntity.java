package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

import java.awt.*;

public class TestEntity extends Entity {
    public TestEntity(World board, Vec2d position, Vec2d velocity, EntityType faction) {
        super(board, position, velocity, faction);
    }

    public void tick() {
        position.add(velocity);
        if (position.x > board.size.x) {
            position.x = 0;
        }
        if (position.y > board.size.y) {
            position.y = 0;
        }
        if (position.x < 0) {
            position.x = board.size.x;
        }
        if (position.y < 0) {
            position.y = board.size.y;
        }
    }

    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.setColor(Color.BLUE);
        graphics.fillOval((int) (position.x - (getHitRadius() / 2)), (int) (position.y - (getHitRadius() / 2)), (int) getHitRadius(), (int) getHitRadius());
    }
}
