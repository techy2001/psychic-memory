package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.EntityFaction;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.LivingEntity;
import mialee.psychicmemory.game.entities.visuals.ScoreTextEntity;
import mialee.psychicmemory.game.tasks.entitytasks.ClearBulletsTask;
import mialee.psychicmemory.game.tasks.entitytasks.FireBulletsRoundSpinTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveToPositionLerpTask;
import mialee.psychicmemory.input.Input;
import mialee.psychicmemory.math.Vec2d;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The class which represents the player character.
 * The player entity has multiple lives and invulnerability frames ({@link #iFrames}) allowing some time after taking damage to avoid further damage.
 * Also holds a number of {@link #blanks} allowing the player to clear all bullets on screen a limited number of times.
 * Spawn Position is used to bring the player back after dying.
 */
public class PlayerEntity extends LivingEntity {
    private int fireCooldown = 0;
    private int blankCooldown = 0;
    private int lives = 3;
    private int blanks = 3;
    private int iFrames = 0;
    private final Vec2d spawnPosition;

    /**
     * Instantiates a new player.
     * Sets the world's player to itself on spawn, used for enemy targeting.
     * Sets the spawn position.
     * @param world The world to spawn the PlayerEntity within.
     * @param position The position to spawn at in the world.
     * @param velocity Velocity for use by certain tasks.
     */
    public PlayerEntity(World world, Vec2d position, Vec2d velocity) {
        super(world, position, velocity);
        this.spawnPosition = position.copy();
        this.world.setPlayer(this);
    }

    /**
     * Manages many player specific traits.
     * iFrames allow the player to ignore incoming bullets, and will reduce every tick.
     * If the iFrame value is above 180 the player is frozen.
     * User input is taken and used to set player velocity.
     * The player cannot go off-screen and will be brought back if they do escape.
     * Bullets and Blanks will use individual cool downs to prevent spam, and will take user input to trigger.
     */
    @Override
    public void tick() {
        super.tick();

        if (iFrames > 0) iFrames--;
        if (iFrames > 180) return;

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
        if (taskList.size() > 0) System.out.println(taskList.get(0));
    }

    @Override
    protected void registerStats() {
        super.registerStats();
        this.name = "Player";
        this.hitRadius = 5;
        this.visualSize = 18;
        this.image = PsychicMemory.getIcon("entities/player.png");
        this.health = 1;
    }

    @Override
    protected void initializeTasks() {}

    /**
     * Player will not be rendered with high iFrames to give the impression the player is gone.
     * While slowed the player will show their hitbox.
     * While invulnerable a shield is shown over the player.
     * @param graphics Graphics to draw the image to.
     */
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

    /**
     * Ignores damage when player has iFrames.
     * @param amount Amount of damage to take.
     * @return Returns true if damage was taken.
     */
    @Override
    public boolean damage(int amount) {
        if (iFrames <= 0) {
            return super.damage(amount);
        } else {
            return false;
        }
    }

    /**
     * On death player will lose a life and be granted iFrames.
     * The player will also lose score and be sent back to their spawn position and emit enemy bullets as a form of death animation.
     * Bullets are cleared to prevent overwhelming damage.
     * If out of lives the game will end in a failure.
     */
    @Override
    protected void onDeath() {
        if (lives > 0) {
            lives--;
            iFrames = 240;
            world.getBank().addEntity(new ScoreTextEntity(world, position.copy(), new Vec2d(0, -0.5f), 80, -2000), EntityFaction.GRAPHIC);
            addTask(new ClearBulletsTask(this, false).setLoop(false));
            addTask(new FireBulletsRoundSpinTask(this, 1, 0, 20, 4, 25).setLoop(false));
            addTask(new MoveToPositionLerpTask(this, spawnPosition.copy(), 0, 0).setLoop(false));
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
