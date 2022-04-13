package mialee.psychicmemory.game.tasks.entitytasks;

import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.core.BulletEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.entities.core.EntityType;
import mialee.psychicmemory.game.tasks.EntityTask;
import mialee.psychicmemory.math.Vec2d;

public class FireAtPlayerTask extends EntityTask {
    private final int cooldownMax;
    private final int count;
    private final double deviation;
    private final double speed;
    private int cooldown;
    private PlayerEntity player;

    public FireAtPlayerTask(Entity owner, int length, int cooldown, int count, double deviation, double speed, boolean loop) {
        super(owner, length);
        this.cooldownMax = cooldown;
        this.count = count;
        this.deviation = deviation;
        this.speed = speed;
        this.loop = loop;
        findPlayer();
    }

    @Override
    public void tick() {
        super.tick();
        if (cooldown <= 0) {
            if (player != null) {
                double size = Math.abs(player.position.x - owner.position.x) + Math.abs(player.position.y - owner.position.y);
                int amount = (count - 1) / 2;
                for (double i = -(amount * deviation); i <= (amount * deviation); i += deviation) {
                    double rotation = (Math.toDegrees(Math.asin((player.position.x - owner.position.x) / size)));
                    double f = Math.sin((i + rotation) * ((float) Math.PI / 180));
                    double h = Math.cos((i + rotation) * ((float) Math.PI / 180));
                    owner.world.addEntity(new BulletEntity(owner.world, owner.position.copy(), new Vec2d(f * speed, h * speed), EntityType.ENEMY_BULLET, EntityType.PLAYER, 1));
                }
                cooldown = cooldownMax;
            } else {
                findPlayer();
            }
        }
        cooldown--;
    }

    private void findPlayer() {
        for (Entity entity : owner.world.getEntities()) {
            if (entity instanceof PlayerEntity player) {
                this.player = player;
            }
        }
    }
}