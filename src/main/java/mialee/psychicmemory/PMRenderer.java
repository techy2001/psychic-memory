package mialee.psychicmemory;

import mialee.psychicmemory.input.Input;
import mialee.psychicmemory.lang.TranslatableText;
import mialee.psychicmemory.math.Vec2i;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

/**
 * The class which handles all the games rendering and visuals.
 */
public class PMRenderer {
    private static Canvas canvas;
    public static final Vec2i dimensions = new Vec2i(960, 720);
    private static Font baseFont;

    /**
     * This class is called from {@link Main#main(String[])} and starts the main render thread.
     * The render thread will render what is relevant based on the GameState
     */
    public static void startRenderer() {
        canvas = new Canvas();
        canvas.setSize(new Dimension(dimensions.x, dimensions.y));
        canvas.addKeyListener(new Input());

        JFrame frame = new JFrame(new TranslatableText("pm.game").toString());
        frame.add(canvas);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.MAGENTA);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread renderThread = new Thread(() -> {
            GraphicsConfiguration graphicsConfiguration = canvas.getGraphicsConfiguration();
            VolatileImage volatileImage = graphicsConfiguration.createCompatibleVolatileImage(dimensions.x, dimensions.y);

            int frames = 0;
            long lastFrameTime = 0;
            int frameRate = 0;
            while(true) {
                frames++;
                if (System.nanoTime() > lastFrameTime + 1000000000) {
                    lastFrameTime = System.nanoTime();
                    frameRate = frames;
                    frames = 0;
                }

                if (volatileImage.validate(graphicsConfiguration) == VolatileImage.IMAGE_INCOMPATIBLE) {
                    volatileImage = graphicsConfiguration.createCompatibleVolatileImage(dimensions.x, dimensions.y);
                }

                Graphics graphics = volatileImage.getGraphics();
                graphics.setColor(new Color(41, 41, 57));
                graphics.fillRect(0, 0, dimensions.x, dimensions.y);

                if (Main.gameState == GameState.INGAME || Main.gameState == GameState.PAUSED || Main.gameState == GameState.BOSS_PAUSED) {
                    if (Main.world != null) {
                        Main.world.render(graphics);
                    }
                }
                if (Main.gameState == GameState.MENU) {
                    if (Input.menu != null) Input.menu.render(graphics);
                }
                if (Main.gameState == GameState.SCORE) {
                    if (Input.scoreMenu != null) Input.scoreMenu.render(graphics);
                }

                resetFont(graphics);
                String fps = "FPS: %d".formatted(frameRate);
                String tps = "TPS: %d".formatted(Main.ticksPerSecond);
                graphics.setColor(new Color(61, 60, 69));
                graphics.drawString(fps, dimensions.x - 53, 13);
                graphics.drawString(tps, dimensions.x - 53, 25);
                graphics.setColor(new Color(202, 199, 220));
                graphics.drawString(fps, dimensions.x - 54, 12);
                graphics.drawString(tps, dimensions.x - 54, 24);

                graphics.dispose();
                graphics = canvas.getGraphics();
                graphics.drawImage(volatileImage, 0, 0, dimensions.x, dimensions.y, null);
                graphics.dispose();

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    Main.LOGGER.loggedError(new TranslatableText("pm.sleep.error"), e.getMessage());
                }
            }
        });
        renderThread.setName("renderThread");
        renderThread.start();

        frame.setVisible(true);
    }

    /**
     * Resets the font to the base font at size 12, the base font being Ubuntu-M.
     * @param graphics The graphics to set the font for.
     */
    public static void resetFont(Graphics graphics) {
        try {
            if (getBaseFont() == null) {
                baseFont = Font.createFont(0, new File(Main.class.getClassLoader().getResource("assets/font/Ubuntu-M.ttf").getFile()));
            }
            graphics.setFont(getBaseFont().deriveFont(12f));
        } catch (FontFormatException | IOException e) {
            Main.LOGGER.loggedError(new TranslatableText("pm.data.font.error"), "Ubuntu-M.tff", e.getMessage());
        }
    }

    public static Font getBaseFont() {
        return baseFont;
    }
}