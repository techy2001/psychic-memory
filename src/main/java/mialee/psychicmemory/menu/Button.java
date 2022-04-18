package mialee.psychicmemory.menu;

import mialee.psychicmemory.window.PMRenderer;

import java.awt.*;

public record Button(String text, Runnable press, int x, int y, int offset) {
    public int getX(boolean selected) {
        return (selected ? x + offset : x) - (text.length() * 36);
    }

    public int getY(boolean selected) {
        return (selected ? y + offset : y) - 36;
    }

    public Color getColor(boolean selected) {
        return selected ? Color.WHITE : Color.GRAY;
    }

    public void render(Graphics graphics, boolean selected) {
        graphics.setFont(PMRenderer.getBaseFont().deriveFont(72f));
        graphics.setColor(getColor(selected));
        graphics.drawString(text, getX(selected), getY(selected));
    }
}
