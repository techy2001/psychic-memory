package mialee.psychicmemory;

import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.data.GameRecord;
import mialee.psychicmemory.data.PMSettings;
import mialee.psychicmemory.data.TextLogger;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.input.Input;
import mialee.psychicmemory.lang.Language;
import mialee.psychicmemory.lang.TranslatableText;
import mialee.psychicmemory.menu.Menu;
import mialee.psychicmemory.menu.ScoreMenu;

import javax.swing.ImageIcon;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * The main game class.
 * The overall game has no real underlying issues left, all that could really be done to improve would simply be some more game content.
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

                if (gameState == GameState.INGAME && Input.getKey(PsychicMemory.SETTING_VALUES.PAUSE_KEY)) {
                    gameState = GameState.PAUSED;
                    safeSleep(200);
                } else if (gameState == GameState.PAUSED && Input.getKey(PsychicMemory.SETTING_VALUES.PAUSE_KEY)) {
                    gameState = GameState.INGAME;
                    safeSleep(200);
                }

                if (gameState == GameState.INGAME) {
                    world.tick();
                }
                if (gameState == GameState.BOSS_PAUSED) {
                    if (world.getLastBoss() != null) {
                        world.getLastBoss().tick();
                        world.getBank().addAllNew();
                    }
                }

                safeSleep(10);
            }
        });
        gameThread.setName("gameThread");
        gameThread.start();

        restart();
    }

    /**
     * Used to wait with error detection.
     * Used to slow the tick rate and to add a delay after hitting pause.
     * @param millis Length of time in milliseconds to wait.
     */
    private static void safeSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.sleep.error"), e.getMessage());
        }
    }

    /**
     * Used to start the game, beginning the selected stage.
     */
    public static void start() {
        world = new World();
        gameState = GameState.INGAME;
    }

    /**
     * Used to reset the world back to its default state and return to the main menu.
     * Called at the game's initial start and after winning/losing.
     */
    public static void restart() {
        Input.menu = new Menu();
        gameState = GameState.MENU;
    }

    /**
     * Called when the player dies or wins the game.
     * @param win Tells if the end state was triggered by a win or a loss.
     */
    public static void end(boolean win) {
        GameRecord highScore = DataManager.readHighScore();
        Input.scoreMenu = new ScoreMenu(win, world.getScore(), highScore == null || world.getScore() > highScore.score());
        gameState = GameState.SCORE;
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
