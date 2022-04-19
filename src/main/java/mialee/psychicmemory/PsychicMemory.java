package mialee.psychicmemory;

import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.data.GameRecord;
import mialee.psychicmemory.data.PMSettings;
import mialee.psychicmemory.data.TextLogger;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.game.entities.PlayerEntity;
import mialee.psychicmemory.game.entities.TestEntity;
import mialee.psychicmemory.game.entities.core.EntityFaction;
import mialee.psychicmemory.lang.Language;
import mialee.psychicmemory.lang.TranslatableText;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.math.Vec2i;
import mialee.psychicmemory.menu.Menu;
import mialee.psychicmemory.window.PMRenderer;

import javax.swing.ImageIcon;
import javax.xml.crypto.Data;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * The main game class.
 */
public class PsychicMemory {
    public static String dir = "PMData";
    public static TextLogger LOGGER;
    public static Language LANGUAGE;
    public static Random RANDOM = new Random();
    public static PMSettings SETTING_VALUES;
    private final static Map<String, ImageIcon> sprites = new LinkedHashMap<>();
    public static final ImageIcon missingTexture = new ImageIcon(Objects.requireNonNull(PsychicMemory.class.getClassLoader().getResource("assets/textures/entities/cod.png")));
    public static World world;
    public static Menu menu;
    public static long ticksPerSecond = 0;
    public static GameState gameState = GameState.MENU;

    /**
     * The main method of the program.
     * This method does all the setup for the game, and starts both the main game thread and the render thread in {@link PMRenderer#startRenderer()}
     *
     * Both the main thread and the render thread keep track of the ticks per second (TPS) and frames per second (FPS) respectively.
     */
    public static void main(String[] args) {
        LOGGER = new TextLogger();
        LANGUAGE = new Language("en_ie");
        if (new File(dir + "/logs/").mkdirs()) LOGGER.loggedPrint(new TranslatableText("pm.data.setup"));
        SETTING_VALUES = DataManager.readSettings();

        PMRenderer.startRenderer();

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
                    PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.sleep.error"), e.getMessage());
                }
            }
        });
        gameThread.setName("gameThread");
        gameThread.start();

        restart();
    }

    /**
     * Used to start the game, beginning the selected stage.
     */
    public static void start() {
        gameState = GameState.INGAME;
        world.addEntity(new TestEntity(world, new Vec2d(0, 100), new Vec2d(3, 0), EntityFaction.ENEMY));
        world.addEntity(new PlayerEntity(world, new Vec2d(360, 400), new Vec2d(0, 0), EntityFaction.PLAYER));
    }

    /**
     * Used to reset the world back to its default state and return to the main menu, also used for the first time start.
     */
    public static void restart() {
        menu = new Menu();
        PMRenderer.addInput(menu);
        world = new World(new Vec2i(620, 720));
        gameState = GameState.MENU;
    }

    /**
     * Called when the player dies or wins the game.
     * @param win Tells if the end state was triggered by a win or a loss.
     */
    public static void end(boolean win) {
        DataManager.writeScore("dummy", world.getScore());
        restart();
    }

    /**
     * Used to get image files from the stored cache. If a file is not in the cache it will call {@link #loadImage(String)} to load and add the file to the cache.
     * @param name Name of the image file to load.
     */
    public static ImageIcon getIcon(String name) {
        if (!sprites.containsKey(name)) {
            sprites.put(name, loadImage(name));
        }
        return sprites.get(name);
    }

    /**
     * Reads the requested texture file from the resources' folder, and adds it to the cache.
     * If the image is not found it will use the preset missing texture.
     * @param location The location of the image file to load.
     */
    private static ImageIcon loadImage(String location) {
        ImageIcon icon;
        try {
            icon = new ImageIcon(Objects.requireNonNull(PsychicMemory.class.getClassLoader().getResource("assets/textures/" + location)));
        } catch (Exception e) {
            icon = missingTexture;
            LOGGER.loggedError(new TranslatableText("pm.data.image.missing"), location, e.getMessage());
        }
        return icon;
    }
}
