package mialee.psychicmemory.menu;

import mialee.psychicmemory.window.PMRenderer;

import java.awt.*;

public record Text(String text, int x, int y) {
    public void render(Graphics graphics) {
        graphics.setFont(PMRenderer.getBaseFont().deriveFont(72f));
        graphics.setColor(Color.GRAY);
        graphics.drawString(text, x - (graphics.getFontMetrics().stringWidth(text) / 2), y);
    }
}
