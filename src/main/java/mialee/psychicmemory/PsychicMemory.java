package mialee.psychicmemory;

import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.data.PMSave;
import mialee.psychicmemory.data.PMSettings;
import mialee.psychicmemory.data.TextLogger;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.core.TestEntity;
import mialee.psychicmemory.lang.Language;
import mialee.psychicmemory.lang.TranslatableText;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.window.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;
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
    public static World world = new World();

    public static void main(String[] args) {
        LOGGER = new TextLogger();
        LANGUAGE = new Language("en_ie");
        if (new File(dir + "/logs/").mkdirs()) LOGGER.loggedPrint(new TranslatableText("pm.data.setup"));
        SETTING_VALUES = DataManager.populateSettings();
        SAVE_VALUES = new LinkedHashMap<>();
        for(int i = 1; i <= 3; i++) SAVE_VALUES.put(i, DataManager.populateSave(i));

        Renderer.startRenderer();

        Thread gameThread = new Thread(() -> {
            long lastTickTime = 0;
            while(true) {
                if (System.nanoTime() > lastTickTime + (1000000000 / 60)) {
                    lastTickTime = System.nanoTime();
                } else {
                    continue;
                }

                world.tick();
            }
        });
        gameThread.setName("gameThread");
        gameThread.start();

        for (int i = -0; i <= 8; i++) {
            for (int j = -0; j <= 8; j++) {
                world.entities.add(new TestEntity(world, new Vec2d(0, 0), new Vec2d(i, j)));
            }
        }
    }

    private final static Map<String, ImageIcon> sprites = new LinkedHashMap<>();
    public static ImageIcon getIcon(String name) {
        if (!sprites.containsKey(name)) {
            sprites.put(name, loadImage(name));
        }
        return sprites.get(name);
    }

    private static final ImageIcon missingTexture = new ImageIcon(Objects.requireNonNull(PsychicMemory.class.getClassLoader().getResource("assets/textures/cod.png")));
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
}
