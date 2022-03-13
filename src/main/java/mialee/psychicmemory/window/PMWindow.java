package mialee.psychicmemory.window;

import mialee.psychicmemory.lang.TranslatableText;

import javax.swing.*;

import java.awt.*;

import static mialee.psychicmemory.PMGame.LOGGER;

public class PMWindow extends JFrame {
    public PMWindow() {
        super(new TranslatableText("pm.game").toString());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        setSize(640, 480);
        setResizable(false);

        setBackground(Color.BLACK);
        setVisible(true);

        add(new PMGameScreen());
    }

    public static void createWindow() {
        Runnable initFrame = PMWindow::new;
        try {
            SwingUtilities.invokeAndWait(initFrame);
        } catch (Exception e) {
            //Logs in the case that the window fails to load.
            LOGGER.loggedError(new TranslatableText("pm.window.fail"));
        }
    }
}