package mialee.psychicmemory.game;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.PMRenderer;
import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.bosses.BossEntity;
import mialee.psychicmemory.game.tasks.Task;
import mialee.psychicmemory.game.tasks.entitytasks.BossPauseTask;
import mialee.psychicmemory.game.tasks.entitytasks.FireAtPlayerTask;
import mialee.psychicmemory.game.tasks.entitytasks.FireBulletsRoundSpinRotTask;
import mialee.psychicmemory.game.tasks.entitytasks.FireBulletsRoundSpinTask;
import mialee.psychicmemory.game.tasks.entitytasks.LargeTrailAtPlayerTask;
import mialee.psychicmemory.game.tasks.entitytasks.LargeTrailDownwardTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveToPositionLerpTask;
import mialee.psychicmemory.game.tasks.entitytasks.MoveToRandomLerpTask;
import mialee.psychicmemory.game.tasks.entitytasks.OverheadConeTask;
import mialee.psychicmemory.game.tasks.tasks.MultiTask;
import mialee.psychicmemory.game.tasks.tasks.WaitTask;
import mialee.psychicmemory.game.tasks.worldtasks.SpawnEntityTask;
import mialee.psychicmemory.game.tasks.worldtasks.WaitForBossTask;
import mialee.psychicmemory.lang.TranslatableText;
import mialee.psychicmemory.math.MathHelper;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.math.Vec2i;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class World {
    private final EntityBank entities = new EntityBank(this);
    private PlayerEntity player;
    public final Vec2i size = new Vec2i(620, 720);
    private int score = 0;
    private int scoreOld = 0;
    private int scoreVisual = 0;
    private int scoreProgress = 0;
    private int highScore = DataManager.readHighScore().score();
    private final ArrayList<Task> taskList = new ArrayList<>();
    private final ArrayList<Task> pendingTasks = new ArrayList<>();
    private BossEntity lastBoss;

    public World() {
        setPlayer(new PlayerEntity(this, new Vec2d(310, 550), new Vec2d(0, 0)));
        populateTasks();
    }

    protected void populateTasks() {
        addTask(new WaitTask(150));

//        for (int i = 0; i < 8; i++) {
//            addTask(new WaitTask(10));
//            FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(50, -50));
//            flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(150, 200), 1, 100));
//            flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x + 50, 200), 1, 140));
//            flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//            addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//        }
//        addTask(new WaitTask(40));
//        for (int i = 0; i < 8; i++) {
//            addTask(new WaitTask(10));
//            FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(size.x - 50, -50));
//            flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x - 150, 200), 1, 100));
//            flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(-50, 200), 1, 140));
//            flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//            addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//        }
//        addTask(new WaitTask(200));
//
//        for (int i = 0; i < 8; i++) {
//            addTask(new WaitTask(20));
//            RandomExtraEntity randomExtra = new RandomExtraEntity(this, new Vec2d(-50, 60), 0.1f);
//            randomExtra.setTask(new FireAtPlayerTask(randomExtra, 1, 12, 1, 15, 3));
//            randomExtra.addTask(new MoveWithVelocityTask(randomExtra, 100));
//            randomExtra.velocity.set(new Vec2d(3, 1f));
//            addTask(new SpawnEntityTask(this, randomExtra, EntityFaction.ENEMY));
//        }
//        addTask(new WaitTask(40));
//        for (int i = 0; i < 8; i++) {
//            addTask(new WaitTask(20));
//            RandomExtraEntity randomExtra = new RandomExtraEntity(this, new Vec2d(size.x + 50, 60), 0.1f);
//            randomExtra.setTask(new FireAtPlayerTask(randomExtra, 1, 12, 1, 15, 3));
//            randomExtra.addTask(new MoveWithVelocityTask(randomExtra, 100));
//            randomExtra.velocity.set(new Vec2d(-3, 1f));
//            addTask(new SpawnEntityTask(this, randomExtra, EntityFaction.ENEMY));
//        }
//        addTask(new WaitTask(200));
//
//        for (int i = 0; i < 8; i++) {
//            {
//                addTask(new WaitTask(5));
//                FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(50, -50));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(150, 200), 1, 100));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x - 150, 200), 1, 140));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(150, size.y + 50), 1, 140));
//                flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//                addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//            }
//            {
//                addTask(new WaitTask(5));
//                FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(size.x - 50, -50));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x - 150, 200), 1, 100));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(150, 200), 1, 140));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d((float) size.x - 150, size.y + 50), 1, 140));
//                flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//                addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//            }
//        }
//        addTask(new WaitTask(240));
//
//        for (int i = 0; i < 16; i++) {
//            addTask(new WaitTask(30));
//            double x = PsychicMemory.RANDOM.nextDouble(size.x - 100) + 50;
//            FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(x, -50));
//            flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(x, 100), 1, 80));
//            flyRouteEnemy.addTask(new FireBulletsRoundTask(flyRouteEnemy, 1, 0, 6, 2));
//            flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(x + (PsychicMemory.RANDOM.nextBoolean() ? -80 : 80), size.y + 50), 1, 220));
//            flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//            addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//        }
//        addTask(new WaitTask(240));
//
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 4; j++) {
//                {
//                    addTask(new WaitTask(10));
//                    RandomExtraEntity randomExtra = new RandomExtraEntity(this, new Vec2d(-50, 0), 0.01f);
//                    randomExtra.setTask(new FireAtPlayerTask(randomExtra, 1, 120, 1, 15, 3));
//                    randomExtra.addTask(new MoveToPositionLerpTask(randomExtra, new Vec2d(size.x + 50, 100 + (20 * i) + (60 * j)), 1, 200));
//                    randomExtra.addTask(new DeleteSelfTask(randomExtra));
//                    randomExtra.setHealth(2);
//                    addTask(new SpawnEntityTask(this, randomExtra, EntityFaction.ENEMY));
//                }
//                {
//                    addTask(new WaitTask(10));
//                    RandomExtraEntity randomExtra = new RandomExtraEntity(this, new Vec2d(size.x + 50, 0), 0.01f);
//                    randomExtra.setTask(new FireAtPlayerTask(randomExtra, 1, 120, 1, 15, 3));
//                    randomExtra.addTask(new MoveToPositionLerpTask(randomExtra, new Vec2d(-50, 100 + (20 * i) + (60 * j)), 1, 200));
//                    randomExtra.addTask(new DeleteSelfTask(randomExtra));
//                    randomExtra.setHealth(2);
//                    addTask(new SpawnEntityTask(this, randomExtra, EntityFaction.ENEMY));
//                }
//            }
//        }
//        addTask(new WaitTask(300));
//
//        {
//            BossEntity miniBossEntity = new BossEntity(this, new Vec2d(260, -20));
//            miniBossEntity.name = "BossStage1";
//            miniBossEntity.hitRadius = 20;
//            miniBossEntity.visualSize = 18;
//            miniBossEntity.image = PsychicMemory.getIcon("entities/tropical_fish.png");
//            miniBossEntity.health = 90;
//            miniBossEntity.maxHealth = 90;
//            miniBossEntity.lives = 0;
//            miniBossEntity.score = 10000;
//            miniBossEntity.addTask(new MoveToPositionLerpTask(miniBossEntity, new Vec2d(310, 120), 1, 40));
//            miniBossEntity.addTask(new WaitTask(40));
//            ArrayList<Task> phase1 = new ArrayList<>();
//            phase1.add(new FireBulletsRoundSpinTask(miniBossEntity, 50, 10, 8, 2, -15));
//            phase1.add(new MoveToPositionLerpTask(miniBossEntity, new Vec2d(520, 200), 1, 100));
//            phase1.add(new FireAtPlayerTask(miniBossEntity, 75, 25, 7, 25, 2));
//            phase1.add(new WaitTask(50));
//            phase1.add(new MoveToPositionLerpTask(miniBossEntity, new Vec2d(310, 120), 1, 80));
//            phase1.add(new FireBulletsRoundSpinTask(miniBossEntity, 1, 0, 10, 1.4, 15));
//            phase1.add(new FireBulletsRoundSpinTask(miniBossEntity, 1, 0, 10, 1.6, 15));
//            phase1.add(new FireBulletsRoundSpinTask(miniBossEntity, 1, 0, 10, 1.8, 15));
//            phase1.add(new FireBulletsRoundSpinTask(miniBossEntity, 1, 0, 10, 2, 15));
//            phase1.add(new WaitTask(120));
//            phase1.add(new MoveToPositionLerpTask(miniBossEntity, new Vec2d(100, 200), 1, 100));
//            phase1.add(new FireAtPlayerTask(miniBossEntity, 60, 20, 3, 5, 4));
//            phase1.add(new WaitTask(50));
//            phase1.add(new MoveToPositionLerpTask(miniBossEntity, new Vec2d(310, 120), 1, 80));
//            miniBossEntity.addPhase(phase1);
//            addTask(new SpawnEntityTask(this, miniBossEntity, EntityFaction.ENEMY));
//            addTask(new WaitForBossTask(this, miniBossEntity));
//        }
//        addTask(new WaitTask(40));
//
//        for (int i = 0; i < 8; i++) {
//            addTask(new WaitTask(10));
//            {
//                FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(100, -50));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(150, 120), 1, 100));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x - 50, 120), 1, 120));
//                flyRouteEnemy.addTask(new FireAtPlayerTask(flyRouteEnemy, 2, 0, 3, 20, 2));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x + 50, 120), 1, 140));
//                flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//                addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//            }
//            addTask(new WaitTask(10));
//            {
//                FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(size.x - 100, -50));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x - 150, 120), 1, 100));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(50, 120), 1, 120));
//                flyRouteEnemy.addTask(new FireAtPlayerTask(flyRouteEnemy, 2, 0, 3, 20, 2));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(-50, 120), 1, 140));
//                flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//                addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//            }
//            addTask(new WaitTask(10));
//            {
//                FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(50, -50));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(100, 200), 1, 100));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x - 50, 200), 1, 120));
//                flyRouteEnemy.addTask(new FireAtPlayerTask(flyRouteEnemy, 2, 0, 3, 20, 2));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x + 50, 200), 1, 40));
//                flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//                addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//            }
//            addTask(new WaitTask(10));
//            {
//                FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(size.x - 50, -50));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(size.x - 100, 200), 1, 100));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(50, 200), 1, 120));
//                flyRouteEnemy.addTask(new FireAtPlayerTask(flyRouteEnemy, 2, 0, 3, 20, 2));
//                flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(-50, 200), 1, 40));
//                flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//                addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//            }
//        }
//        addTask(new WaitTask(300));
//
//        for (int i = 0; i < 24; i++) {
//            addTask(new WaitTask(20));
//            double x = PsychicMemory.RANDOM.nextDouble(size.x - 100) + 50;
//            FlyRouteEnemy flyRouteEnemy = new FlyRouteEnemy(this, new Vec2d(x, -50));
//            flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(x, 100), 1, 80));
//            double spin = PsychicMemory.RANDOM.nextDouble(360);
//            flyRouteEnemy.addTask(new FireBulletsRoundSpinTask(flyRouteEnemy, 1, 0, 6, 1.8, spin));
//            flyRouteEnemy.addTask(new FireBulletsRoundSpinTask(flyRouteEnemy, 1, 0, 6, 2, spin));
//            flyRouteEnemy.addTask(new FireBulletsRoundSpinTask(flyRouteEnemy, 1, 0, 6, 2.2, spin));
//            flyRouteEnemy.addTask(new MoveToPositionLerpTask(flyRouteEnemy, new Vec2d(x + (PsychicMemory.RANDOM.nextBoolean() ? -120 : 120), size.y + 50), 1, 200));
//            flyRouteEnemy.addTask(new DeleteSelfTask(flyRouteEnemy));
//            addTask(new SpawnEntityTask(this, flyRouteEnemy, EntityFaction.ENEMY));
//        }
//        addTask(new WaitTask(360));

        {
            BossEntity bossEntity = new BossEntity(this, new Vec2d(380, -20));
            bossEntity.name = "BossStage12";
            bossEntity.hitRadius = 20;
            bossEntity.visualSize = 18;
            bossEntity.image = PsychicMemory.getIcon("entities/tropical_fish.png");
            bossEntity.health = 10;
            bossEntity.maxHealth = 100;
            bossEntity.lives = 0;
            bossEntity.score = 10000;
            bossEntity.addTask(new MoveToPositionLerpTask(bossEntity, new Vec2d(310, 120), 4, 40));

            ArrayList<Task> phase1 = new ArrayList<>();
            phase1.add(new WaitTask(20));
            phase1.add(new FireBulletsRoundSpinTask(bossEntity, 100, 10, 8, 2, -15));
            phase1.add(new MoveToPositionLerpTask(bossEntity, new Vec2d(520, 200), 4, 100));
            phase1.add(new FireAtPlayerTask(bossEntity, 75, 25, 7, 15, 2));
            phase1.add(new WaitTask(20));
            phase1.add(new MoveToPositionLerpTask(bossEntity, new Vec2d(310, 120), 4, 80));
            phase1.add(new FireBulletsRoundSpinTask(bossEntity, 1, 0, 16, 1, 15));
            phase1.add(new FireBulletsRoundSpinTask(bossEntity, 1, 0, 16, 1.2, 15));
            phase1.add(new FireBulletsRoundSpinTask(bossEntity, 1, 0, 16, 1.4, 15));
            phase1.add(new FireBulletsRoundSpinTask(bossEntity, 1, 0, 16, 1.6, 15));
            phase1.add(new FireBulletsRoundSpinTask(bossEntity, 1, 0, 16, 1.8, 15));
            phase1.add(new FireBulletsRoundSpinTask(bossEntity, 1, 0, 16, 2, 15));
            phase1.add(new WaitTask(90));
            phase1.add(new WaitTask(20));
            phase1.add(new MoveToPositionLerpTask(bossEntity, new Vec2d(100, 200), 4, 100));
            phase1.add(new FireAtPlayerTask(bossEntity, 75, 25, 7, 15, 2));
            phase1.add(new WaitTask(20));
            phase1.add(new MoveToPositionLerpTask(bossEntity, new Vec2d(310, 120), 4, 80));
            ArrayList<Task> phase1sub = new ArrayList<>();
            phase1sub.add(new MoveToRandomLerpTask(bossEntity, 4, 120));
            phase1sub.add(new FireBulletsRoundSpinTask(bossEntity, 90, 5, 8, 3, -10));
            phase1sub.add(new FireBulletsRoundSpinTask(bossEntity, 90, 5, 8, 3, 10));
            bossEntity.addPhase(phase1, phase1sub, 0.1f);

            ArrayList<Task> phase2 = new ArrayList<>();
            phase2.add(new WaitTask(20));
            phase2.add(new MultiTask(80,
                    new MoveToRandomLerpTask(bossEntity, 4, 80),
                    new FireAtPlayerTask(bossEntity, 80, 20, 1, 0, 3)));
            phase2.add(new WaitTask(20));
            phase2.add(new MultiTask(80,
                    new MoveToRandomLerpTask(bossEntity, 4, 80),
                    new FireAtPlayerTask(bossEntity, 120, 40, 3, 15, 3)));
            ArrayList<Task> phase2sub = new ArrayList<>();
            phase2sub.add(new MoveToRandomLerpTask(bossEntity, 4, 160));
            phase2sub.add(new FireBulletsRoundSpinRotTask(bossEntity, 1, 0, 16, 2, 16, 90));
            phase2sub.add(new FireBulletsRoundSpinRotTask(bossEntity, 1, 0, 16, 2, 16, -90));
            phase2sub.add(new WaitTask(80));
            phase2sub.add(new FireBulletsRoundSpinRotTask(bossEntity, 1, 0, 16, 2, 32, 90));
            phase2sub.add(new FireBulletsRoundSpinRotTask(bossEntity, 1, 0, 16, 2, 32, -90));
            phase2sub.add(new WaitTask(80));
            phase2sub.add(new FireBulletsRoundSpinRotTask(bossEntity, 1, 0, 16, 2, 64, 90));
            phase2sub.add(new FireBulletsRoundSpinRotTask(bossEntity, 1, 0, 16, 2, 64, -90));
            phase2sub.add(new WaitTask(80));
            bossEntity.addPhase(phase2, phase2sub, 0.1f);

            ArrayList<Task> phase3 = new ArrayList<>();
            phase3.add(new WaitTask(40));
            phase3.add(new OverheadConeTask(bossEntity, 1, 0, 40, 2));
            phase3.add(new LargeTrailDownwardTask(bossEntity, -45, 6));
            phase3.add(new WaitTask(20));
            phase3.add(new OverheadConeTask(bossEntity, 1, 0, 40, 2));
            phase3.add(new LargeTrailDownwardTask(bossEntity, -15, 6));
            phase3.add(new WaitTask(20));
            phase3.add(new OverheadConeTask(bossEntity, 1, 0, 40, 2));
            phase3.add(new LargeTrailDownwardTask(bossEntity, 15, 6));
            phase3.add(new WaitTask(20));
            phase3.add(new OverheadConeTask(bossEntity, 1, 0, 40, 2));
            phase3.add(new LargeTrailDownwardTask(bossEntity, 45, 6));
            phase3.add(new MoveToRandomLerpTask(bossEntity, 4, 120));
            phase3.add(new WaitTask(40));
            phase3.add(new OverheadConeTask(bossEntity, 1, 0, 40, 2));
            phase3.add(new LargeTrailDownwardTask(bossEntity, -30, 6));
            phase3.add(new WaitTask(15));
            phase3.add(new OverheadConeTask(bossEntity, 1, 0, 40, 2));
            phase3.add(new LargeTrailDownwardTask(bossEntity, -15, 6));
            phase3.add(new WaitTask(15));
            phase3.add(new OverheadConeTask(bossEntity, 1, 0, 40, 2));
            phase3.add(new LargeTrailDownwardTask(bossEntity, 0, 6));
            phase3.add(new WaitTask(15));
            phase3.add(new OverheadConeTask(bossEntity, 1, 0, 40, 2));
            phase3.add(new LargeTrailDownwardTask(bossEntity, 15, 6));
            phase3.add(new WaitTask(15));
            phase3.add(new OverheadConeTask(bossEntity, 1, 0, 40, 2));
            phase3.add(new LargeTrailDownwardTask(bossEntity, 30, 6));
            phase3.add(new MoveToRandomLerpTask(bossEntity, 4, 120));
            ArrayList<Task> phase3sub = new ArrayList<>();
            phase3sub.add(new WaitTask(180));
            phase3sub.add(new FireBulletsRoundSpinTask(bossEntity, 1, 0, 32, 4, 25));
            phase3sub.add(new WaitTask(40));
            phase3sub.add(new BossPauseTask(bossEntity, true));
            phase3sub.add(new LargeTrailAtPlayerTask(bossEntity, 0, 4));
            phase3sub.add(new MultiTask(120,
                    new MoveToPositionLerpTask(bossEntity, new Vec2d(100, 100), 1, 120) {
                        @Override
                        public void refresh() {
                            super.refresh();
                            destination.x = PsychicMemory.RANDOM.nextDouble(100) + 50;
                            destination.y = PsychicMemory.RANDOM.nextDouble(100) + 50;
                        }
                    },
                    new FireAtPlayerTask(bossEntity, 120, 10, 6, 25, 3.2f)));
            phase3sub.add(new WaitTask(20));
            phase3sub.add(new LargeTrailAtPlayerTask(bossEntity, 0, 4));
            phase3sub.add(new BossPauseTask(bossEntity, false));
            phase3sub.add(new WaitTask(180));
            phase3sub.add(new FireBulletsRoundSpinTask(bossEntity, 1, 0, 32, 4, 25));
            phase3sub.add(new WaitTask(40));
            phase3sub.add(new BossPauseTask(bossEntity, true));
            phase3sub.add(new LargeTrailAtPlayerTask(bossEntity, 0, 4));
            phase3sub.add(new MultiTask(120,
                    new MoveToPositionLerpTask(bossEntity, new Vec2d(size.x - 100, 100), 1, 120) {
                        @Override
                        public void refresh() {
                            super.refresh();
                            destination.x = size.x - (PsychicMemory.RANDOM.nextDouble(100) + 50);
                            destination.y = PsychicMemory.RANDOM.nextDouble(100) + 50;
                        }
                    },
                    new FireAtPlayerTask(bossEntity, 120, 10, 7, 25, 3.2f)));
            phase3sub.add(new WaitTask(20));
            phase3sub.add(new LargeTrailAtPlayerTask(bossEntity, 0, 4));
            phase3sub.add(new BossPauseTask(bossEntity, false));
            bossEntity.addPhase(phase3, phase3sub, 0.1f);
            bossEntity.setEndBoss(true);
            addTask(new SpawnEntityTask(this, bossEntity, EntityFaction.ENEMY));
            addTask(new WaitForBossTask(this, bossEntity));
        }
    }

    public void tick() {
        if (!taskList.isEmpty()) {
            Task task = taskList.get(0);
            task.tick();
            if (task.isComplete()) {
                if (task.shouldLoop()) {
                    pendingTasks.add(task);
                    task.refresh();
                }
                taskList.remove(0);
            }
        }
        taskList.addAll(pendingTasks);
        pendingTasks.clear();

        entities.tick();
        player.tick();
        if (scoreProgress < 100) scoreProgress++;
        scoreVisual = MathHelper.lerpInt((float) scoreProgress / 100, scoreOld, score);
        if (scoreVisual > highScore) highScore = scoreVisual;
    }

    public void addTask(Task task) {
        pendingTasks.add(task);
    }

    public void render(Graphics graphics) {
        graphics.setColor(new Color(33, 23, 56));
        if (lastBoss != null && !lastBoss.isMarkedForDeletion() && lastBoss.isInSubPhase()) {
            graphics.setColor(new Color(22, 12, 6));
        }
        graphics.fillRect(0, 0, size.x, size.y);

        entities.render(graphics);
        player.render(graphics);

        if (PsychicMemory.gameState == GameState.PAUSED) {
            graphics.setColor(new Color(0, 0, 0, 80));
            graphics.fillRect(0, 0, size.x, size.y);
        }
        if (PsychicMemory.gameState == GameState.BOSS_PAUSED) {
            graphics.setColor(new Color(120, 0, 0, 80));
            graphics.fillRect(0, 0, size.x, size.y);
        }

        PMRenderer.resetFont(graphics);
        PsychicMemory.world.renderUI(graphics);
    }

    public void renderUI(Graphics graphics) {
        graphics.setColor(new Color(41, 41, 57));
        graphics.fillRect(size.x, 0, PMRenderer.dimensions.x - size.x, PMRenderer.dimensions.y);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(size.x, 0, PMRenderer.dimensions.x - size.x, PMRenderer.dimensions.y);

        if (PsychicMemory.gameState == GameState.PAUSED || PsychicMemory.gameState == GameState.BOSS_PAUSED) {
            graphics.setFont(PMRenderer.getBaseFont().deriveFont(72f));
            int xOffset = graphics.getFontMetrics().stringWidth(new TranslatableText("pm.game.paused").toString()) / 2;
            int yOffset = graphics.getFontMetrics().getHeight() / 2;
            graphics.setColor(Color.BLACK);
            graphics.drawString(new TranslatableText("pm.game.paused").toString(), 311 - xOffset, 361 - yOffset);
            graphics.setColor(PsychicMemory.gameState == GameState.PAUSED ? Color.WHITE : Color.RED);
            graphics.drawString(new TranslatableText("pm.game.paused").toString(), 310 - xOffset, 360 - yOffset);
        }

        graphics.setFont(PMRenderer.getBaseFont().deriveFont(24f));
        graphics.setColor(Color.BLACK);
        graphics.drawString(new TranslatableText("pm.game.high_score") + ": ", 651, 201);
        graphics.drawString("%08d".formatted(highScore), 811, 201);
        graphics.setColor(Color.WHITE);
        graphics.drawString(new TranslatableText("pm.game.high_score") + ": ", 650, 200);
        graphics.drawString("%08d".formatted(highScore), 810, 200);
        graphics.setColor(Color.BLACK);
        graphics.drawString(new TranslatableText("pm.game.score") + ": ", 651, 231);
        graphics.drawString("%08d".formatted(getScoreVisual()), 811, 231);
        graphics.setColor(Color.WHITE);
        graphics.drawString(new TranslatableText("pm.game.score") + ": ", 650, 230);
        graphics.drawString("%08d".formatted(getScoreVisual()), 810, 230);

        if (player != null) {
            graphics.setColor(Color.BLACK);
            graphics.drawString(new TranslatableText("pm.game.life") + ": ", 651, 301);
            graphics.setColor(Color.WHITE);
            graphics.drawString(new TranslatableText("pm.game.life") + ": ", 650, 300);
            for (int i = 0; i < player.getLives(); i++) {
                graphics.setColor(Color.RED);
                graphics.fillOval(760 + (i * 30), 300 - 20, 24, 24);
                graphics.setColor(Color.ORANGE);
                graphics.fillOval(762 + (i * 30), 302 - 20, 20, 20);
            }
            graphics.setColor(Color.BLACK);
            graphics.drawString(new TranslatableText("pm.game.blanks") + ": ", 651, 331);
            graphics.setColor(Color.WHITE);
            graphics.drawString(new TranslatableText("pm.game.blanks") + ": ", 650, 330);
            for (int i = 0; i < player.getBlanks(); i++) {
                graphics.setColor(Color.BLUE);
                graphics.fillOval(760 + (i * 30), 330 - 20, 24, 24);
                graphics.setColor(Color.CYAN);
                graphics.fillOval(762 + (i * 30), 332 - 20, 20, 20);
            }
        }
    }

    public EntityBank getBank() {
        return entities;
    }

    public void addScore(int value) {
        scoreProgress = 0;
        scoreOld = scoreVisual;
        score += value;
        if (score < 0) score = 0;
    }

    public int getScore() {
        return score;
    }

    public int getScoreVisual() {
        return scoreVisual;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public BossEntity getLastBoss() {
        return lastBoss;
    }

    public void setLastBoss(BossEntity lastBoss) {
        this.lastBoss = lastBoss;
    }
}
