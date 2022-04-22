package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.LivingEntity;
import mialee.psychicmemory.input.Input;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerEntity extends LivingEntity {
    private int fireCooldown = 0;
    private int blankCooldown = 0;
    private int lives = 3;
    private int blanks = 3;
    private int iFrames = 0;
    private final Vec2d spawnPosition;

    public PlayerEntity(World board, Vec2d position, Vec2d velocity) {
        super(board, position, velocity);
        spawnPosition = position.copy();
        world.setPlayer(this);
    }

    @Override
    public void tick() {
        if (iFrames > 0) iFrames--;
        if (iFrames > 180) return;

        super.tick();

        velocity.x = 0;
        velocity.y = 0;
        int speed = 6;

        boolean left = Input.getKey(PsychicMemory.SETTING_VALUES.LEFT_KEY);
        boolean right = Input.getKey(PsychicMemory.SETTING_VALUES.RIGHT_KEY);
        boolean up = Input.getKey(PsychicMemory.SETTING_VALUES.UP_KEY);
        boolean down = Input.getKey(PsychicMemory.SETTING_VALUES.DOWN_KEY);

        if (Input.getKey(PsychicMemory.SETTING_VALUES.SLOW_KEY)) {
            speed /= 2;
        }
        if (left) {
            velocity.x -= speed;
        }
        if (right) {
            velocity.x += speed;
        }
        if (up) {
            velocity.y -= speed;
        }
        if (down) {
            velocity.y += speed;
        }

        position.add(velocity);
        if (position.x > world.size.x) {
            position.x = world.size.x;
        }
        if (position.y > world.size.y) {
            position.y = world.size.y;
        }
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.y < 0) {
            position.y = 0;
        }

        if (fireCooldown > 0) fireCooldown--;
        if (Input.getKey(PsychicMemory.SETTING_VALUES.FIRE_KEY) && fireCooldown <= 0) {
            this.world.getBank().addEntity(new PlayerBulletEntity(this.world, this.position.copy(), new Vec2d(0, -25), 1), EntityFaction.PLAYER_BULLET);
            fireCooldown = 6;
        }
        if (blankCooldown > 0) blankCooldown--;
        if (Input.getKey(PsychicMemory.SETTING_VALUES.BLANK_KEY) && blankCooldown <= 0 && blanks > 0) {
            this.world.getBank().clearBullets(true);
            blanks--;
            blankCooldown = 200;
        }
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.name = "Player";
        this.hitRadius = 5;
        this.visualSize = 18;
        this.image = PsychicMemory.getIcon("entities/salmon.png");
        this.health = 1;
    }

    @Override
    protected void initializeTasks() {}

    @Override
    public void render(Graphics graphics) {
        if (iFrames > 180) return;
        super.render(graphics);
        if (Input.getKey(PsychicMemory.SETTING_VALUES.SLOW_KEY)) {
            graphics.setColor(Color.BLACK);
            graphics.fillOval((int) (position.x - (getHitRadius())), (int) (position.y - getHitRadius()), getHitRadius() * 2, getHitRadius() * 2);
            graphics.setColor(Color.RED);
            graphics.fillOval((int) (position.x - (getHitRadius())) + 2, (int) (position.y - getHitRadius()) + 2, (getHitRadius() * 2) - 4, (getHitRadius() * 2) - 4);
        }
        if (iFrames > 0) {
            graphics.setColor(new Color(0, 255, 255, 120));
            graphics.fillOval((int) (position.x - visualSize * 1.2), (int) (position.y - visualSize * 1.2), (int) (visualSize * 2.4), (int) (visualSize * 2.4));
            graphics.setColor(Color.BLACK);
            graphics.drawOval((int) (position.x - visualSize * 1.2), (int) (position.y - visualSize * 1.2), (int) (visualSize * 2.4), (int) (visualSize * 2.4));
        }
    }

    @Override
    public boolean damage(int amount) {
        if (iFrames <= 0) {
            return super.damage(amount);
        } else {
            return false;
        }
    }

    @Override
    protected void onDeath() {
        if (lives > 0) {
            lives--;
            iFrames = 240;
            world.getBank().addEntity(new ScoreTextEntity(world, position.copy(), new Vec2d(0, -0.5f), 80, -5000), EntityFaction.GRAPHIC);
            position.set(spawnPosition);
            world.getBank().clearBullets(false);
        } else {
            PsychicMemory.end(false);
        }
    }

    public int getLives() {
        return lives;
    }

    public int getBlanks() {
        return blanks;
    }
}
