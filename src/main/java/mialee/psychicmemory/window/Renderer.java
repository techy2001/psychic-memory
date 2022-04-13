package mialee.psychicmemory.window;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.input.Input;
import mialee.psychicmemory.lang.TranslatableText;
import mialee.psychicmemory.math.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;

public class Renderer {
    private static JFrame frame;
    private static Canvas canvas;
    private static final Vec2i dimensions = new Vec2i(960, 720);

    public static void startRenderer() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(dimensions.x, dimensions.y));
        //canvas.addKeyListener(new Input());

        canvas.addKeyListener(PsychicMemory.menu);

        frame = new JFrame(new TranslatableText("pm.game").toString());
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
                graphics.setColor(Color.MAGENTA);
                graphics.fillRect(0, 0, dimensions.x, dimensions.y);

                if (PsychicMemory.gameState != GameState.MENU) {
                    PsychicMemory.world.render(graphics);
                }
                if (PsychicMemory.gameState == GameState.MENU) {
                    PsychicMemory.menu.render(graphics);
                }

                graphics.setColor(Color.BLACK);
                graphics.drawString("FPS: " + frameRate, 0, dimensions.y);
                graphics.drawString("TPS: " + PsychicMemory.ticksPerSecond, 0, dimensions.y - 10);
                graphics.setColor(Color.WHITE);
                graphics.drawString("FPS: " + frameRate, 0, dimensions.y - 1);
                graphics.drawString("TPS: " + PsychicMemory.ticksPerSecond, 0, dimensions.y - 11);

                graphics.dispose();
                graphics = canvas.getGraphics();
                graphics.drawImage(volatileImage, 0, 0, dimensions.x, dimensions.y, null);
                graphics.dispose();

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        renderThread.setName("renderThread");
        renderThread.start();

        frame.setVisible(true);
    }
}