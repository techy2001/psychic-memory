package mialee.psychicmemory.window;

import javax.swing.*;

import static mialee.psychicmemory.PMGame.LOGGER;

public class PMWindow extends JFrame {
    public PMWindow() {
        super("Psychic Memory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
    }

    public static void createWindow() {
        Runnable initFrame = PMWindow::new;
        try {
            SwingUtilities.invokeAndWait(initFrame);
        } catch (Exception e) {
            //Logs in the case that the window fails to load.
            LOGGER.loggedError("Game window failed to initialize.");
        }
    }
}