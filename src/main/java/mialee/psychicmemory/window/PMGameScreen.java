package mialee.psychicmemory.window;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PMGameScreen extends JPanel {
    public PMGameScreen() {

        setVisible(true);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 1000, 1000);

        drawElements(g);
        update();

        repaint();
    }

    public void update() {

    }

    public void drawElements(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int)0, (int)0, 30, 30);
    }
}