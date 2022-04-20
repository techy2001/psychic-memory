package mialee.psychicmemory.game.entities.core;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.ScoreTextEntity;
import mialee.psychicmemory.math.Vec2d;

import java.awt.*;

public class BossEntity extends EnemyEntity {
    private int lives = 1;
    private int maxHealth;

    public BossEntity(World board, Vec2d position) {
        super(board, position);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.name = "Boss";
        this.hitRadius = 20;
        this.visualSize = 18;
        this.image = PsychicMemory.getIcon("entities/tropical_fish.png");
        this.health = 2;
        this.maxHealth = 20;
        this.setScore(10000);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.setFont(graphics.getFont().deriveFont(24f));
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(lives), 11, 9 + graphics.getFontMetrics().getHeight() / 2);
        graphics.setColor(Color.WHITE);
        graphics.drawString(String.valueOf(lives), 10, 8 + graphics.getFontMetrics().getHeight() / 2);
        graphics.setColor(Color.RED);
        graphics.fillRect(32, 12, 580, 10);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(32, 12, 580, 10);
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(32, 12, (int) (((float) health / maxHealth) * 580), 10);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(32, 12, (int) (((float) health / maxHealth) * 580), 10);
    }

    @Override
    protected void onDeath() {
        if (lives > 0) {
            world.getBank().addEntity(new ScoreTextEntity(world, this.position.copy(), new Vec2d(0, -0.5), 40, 2000), EntityFaction.GRAPHIC);
            lives--;
            health = maxHealth;
            world.getBank().clearBullets(true);
        } else {
            super.onDeath();
            PsychicMemory.end(true);
        }
    }
}
