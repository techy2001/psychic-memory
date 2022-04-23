package mialee.psychicmemory.game.entities.visuals;

import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.game.tasks.entitytasks.MoveWithVelocityTask;
import mialee.psychicmemory.math.Vec2d;

/**
 * Visual entity which acts as a background element.
 * Goes unused in final game.
 */
public class BackgroundElementEntity extends Entity {
    public BackgroundElementEntity(World world, Vec2d position, Vec2d velocity) {
        super(world, position, velocity);
    }

    @Override
    public void tick() {
        super.tick();
        if (position.x > world.size.x + 200) {
            this.markForDeletion();
        }
        if (position.y > world.size.y + 200) {
            this.markForDeletion();
        }
        if (position.x + 200 < 0) {
            this.markForDeletion();
        }
        if (position.y + 200 < 0) {
            this.markForDeletion();
        }
    }

    @Override
    protected void initializeTasks() {
        super.initializeTasks();
//        int random = PsychicMemory.RANDOM.nextInt(3);
//        String randomNumber = switch (random) {
//            case 0 -> String.valueOf(PsychicMemory.RANDOM.nextInt(20) + 1);
//            case 1 -> String.valueOf(PsychicMemory.RANDOM.nextInt(16) + 1);
//            default -> String.valueOf(PsychicMemory.RANDOM.nextInt(48) + 1);
//        };
//        if (randomNumber.length() == 1) randomNumber = "0" + randomNumber;
//        this.image = switch (random) {
//            case 0 -> PsychicMemory.getIcon("environment/scifiEnvironment_%2s.png".formatted(randomNumber));
//            case 1 -> PsychicMemory.getIcon("environment/scifiStructure_%2s.png".formatted(randomNumber));
//            default -> PsychicMemory.getIcon("environment/scifiUnit_%2s.png".formatted(randomNumber));
//        };
//        this.visualSize = image.getIconWidth() / 2;
        this.addTask(new MoveWithVelocityTask(this, Integer.MAX_VALUE));
    }
}
