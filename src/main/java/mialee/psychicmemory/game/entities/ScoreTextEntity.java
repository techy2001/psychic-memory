package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.entitytasks.DeleteSelfTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveWithVelocityTask;
import mialee.psychicmemory.math.MathHelper;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.PMRenderer;

import java.awt.Color;
import java.awt.Graphics;

public class ScoreTextEntity extends Entity {
    private final int lifetime;
    private final int value;

    public ScoreTextEntity(World board, Vec2d position, Vec2d velocity, int lifetime, int value) {
        super(board, position, velocity);
        this.lifetime = lifetime;
        this.value = value;
    }

    @Override
    protected void initializeTasks() {
        addTask(new MoveWithVelocityTask(this, lifetime));
        addTask(new DeleteSelfTask(this));
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.hitRadius = 0;
        this.visualSize = 12;
    }

    @Override
    public void markForDeletion() {
        world.addScore(value);
        super.markForDeletion();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setFont(PMRenderer.getBaseFont().deriveFont((float) (12f * (MathHelper.clampDouble(1f, 2f, (float) Math.abs(value) / 100)))));
        int width = graphics.getFontMetrics().stringWidth(String.valueOf(value));
        int height = graphics.getFontMetrics().getHeight();
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(value), (int) position.x - (width / 2) - 1, (int) position.y - (height / 2) - 1);
        graphics.setColor(value >= 0 ? Color.WHITE: Color.RED);
        graphics.drawString(String.valueOf(value), (int) position.x - (width / 2), (int) position.y - (height / 2));
    }
}
