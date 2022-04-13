package mialee.psychicmemory;

import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.data.PMSave;
import mialee.psychicmemory.data.PMSettings;
import mialee.psychicmemory.data.TextLogger;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.core.EntityType;
import mialee.psychicmemory.game.entities.core.TestEntity;
import mialee.psychicmemory.lang.Language;
import mialee.psychicmemory.lang.TranslatableText;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.math.Vec2i;
import mialee.psychicmemory.menu.Menu;
import mialee.psychicmemory.window.Renderer;

import javax.swing.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class PsychicMemory {
    public static String dir = "PMData";
    public static TextLogger LOGGER;
    public static Language LANGUAGE;
    public static PMSettings SETTING_VALUES;
    public static Map<Integer, PMSave> SAVE_VALUES;
    private final static Map<String, ImageIcon> sprites = new LinkedHashMap<>();
    public static final ImageIcon missingTexture = new ImageIcon(Objects.requireNonNull(PsychicMemory.class.getClassLoader().getResource("assets/textures/entities/cod.png")));
    public static World world = new World(new Vec2i(720, 840));
    public static Menu menu = new Menu();
    public static long ticksPerSecond = 0;
    public static GameState gameState = GameState.MENU;

    public static void main(String[] args) {
        LOGGER = new TextLogger();
        LANGUAGE = new Language("en_ie");
        if (new File(dir + "/logs/").mkdirs()) LOGGER.loggedPrint(new TranslatableText("pm.data.setup"));
        SETTING_VALUES = DataManager.populateSettings();
        SAVE_VALUES = new LinkedHashMap<>();
        for(int i = 1; i <= 3; i++) SAVE_VALUES.put(i, DataManager.populateSave(i));

//        for (int i = -2; i <= 2; i++) {
//            for (int j = -2; j <= 2; j++) {
//                world.addEntity(new TestEntity(world, new Vec2d(0, 0), new Vec2d(i, j), EntityType.ENEMY));
//            }
//        }
        world.addEntity(new TestEntity(world, new Vec2d(0, 100), new Vec2d(3, 0), EntityType.ENEMY));
        world.addEntity(new PlayerEntity(world, new Vec2d(360, 400), new Vec2d(0, 0), EntityType.PLAYER));

        Renderer.startRenderer();

        Thread gameThread = new Thread(() -> {
            int ticks = 0;
            long lastTickTime = 0;
            long lastSecond = 0;
            while(true) {
                if (System.nanoTime() > lastSecond + 1000000000) {
                    lastSecond = System.nanoTime();
                    ticksPerSecond = ticks;
                    ticks = 0;
                }
                if (System.nanoTime() > lastTickTime + (1000000000 / 60)) {
                    lastTickTime = System.nanoTime();
                    ticks++;
                } else {
                    continue;
                }

                if (gameState == GameState.INGAME) {
                    world.tick();
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.setName("gameThread");
        gameThread.start();
    }

    public static ImageIcon getIcon(String name) {
        if (!sprites.containsKey(name)) {
            sprites.put(name, loadImage(name));
        }
        return sprites.get(name);
    }

    private static ImageIcon loadImage(String location) {
        ImageIcon icon;
        try {
            icon = new ImageIcon(Objects.requireNonNull(PsychicMemory.class.getClassLoader().getResource("assets/textures/" + location)));
        } catch (Exception e) {
            icon = missingTexture;
            LOGGER.loggedError(new TranslatableText("pm.data.image.missing"), location, e);
        }
        return icon;
    }

    public static void start() {
        System.out.println("start");
        gameState = GameState.INGAME;
    }
}
