package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.EntityFaction;
import mialee.psychicmemory.game.entities.core.LivingEntity;
import mialee.psychicmemory.input.Input;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerEntity extends LivingEntity {
    private int fireCooldown = 0;
    private int lives = 0;
    private int iFrames = 0;
    private final Vec2d spawnPosition;

    public PlayerEntity(World board, Vec2d position, Vec2d velocity, EntityFaction faction) {
        super(board, position, velocity, faction);
        spawnPosition = position;
        world.setPlayer(this);
    }

    @Override
    public void tick() {
        super.tick();
        velocity.x = 0;
        velocity.y = 0;
        int speed = 4;
        if (iFrames > 0) iFrames--;

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
            this.world.addEntity(new PlayerBulletEntity(this.world, this.position.copy(), new Vec2d(0, -25), 1));
            fireCooldown = 4;
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
        super.render(graphics);
        if (Input.getKey(PsychicMemory.SETTING_VALUES.SLOW_KEY)) {
            graphics.setColor(Color.BLACK);
            graphics.fillOval((int) (position.x - (getHitRadius())), (int) (position.y - getHitRadius()), getHitRadius() * 2, getHitRadius() * 2);
            graphics.setColor(Color.RED);
            graphics.fillOval((int) (position.x - (getHitRadius())) + 2, (int) (position.y - getHitRadius()) + 2, (getHitRadius() * 2) - 4, (getHitRadius() * 2) - 4);
        }
    }

    @Override
    public boolean damage(int amount) {
        if (iFrames == 0) {
            return super.damage(amount);
        } else {
            return false;
        }
    }

    @Override
    protected void onDeath() {
        if (lives > 0) {
            lives--;
            iFrames = 120;
            position.set(spawnPosition);
            world.clearBullets(false);
        } else {
            PsychicMemory.end(false);
        }
    }

    public int getLives() {
        return lives;
    }
}
