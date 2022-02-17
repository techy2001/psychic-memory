package mialee.psychicmemory.window;

import mialee.psychicmemory.lang.TranslatableText;

import javax.swing.*;

import static mialee.psychicmemory.PMGame.LOGGER;

public class PMWindow extends JFrame {
    public PMWindow() {
        super(new TranslatableText("pm.game").toString());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
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