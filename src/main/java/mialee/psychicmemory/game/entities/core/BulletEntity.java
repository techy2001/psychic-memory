package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.math.Vec2d;

import java.awt.*;

public abstract class BulletEntity extends Entity {
    protected int damage;
    protected Color color;

    public BulletEntity(World board, Vec2d position, Vec2d velocity, int damage) {
        super(board, position, velocity);
        this.damage = damage;
        this.color = Color.MAGENTA;
    }

    @Override
    public void tick() {
        super.tick();
        if (position.x > world.size.x + visualSize) {
            this.markForDeletion();
        }
        if (position.y > world.size.y + visualSize) {
            this.markForDeletion();
        }
        if (position.x + visualSize < 0) {
            this.markForDeletion();
        }
        if (position.y + visualSize < 0) {
            this.markForDeletion();
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillOval((int) (position.x - getHitRadius()), (int) (position.y - getHitRadius()), getHitRadius() * 2, getHitRadius() * 2);
        graphics.setColor(Color.WHITE);
        graphics.fillOval((int) (position.x - getHitRadius()) + 2, (int) (position.y - getHitRadius()) + 2, (getHitRadius() * 2) - 4, (getHitRadius() * 2) - 4);
    }
}
