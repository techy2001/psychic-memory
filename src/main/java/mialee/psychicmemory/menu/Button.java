package mialee.psychicmemory.menu;

import mialee.psychicmemory.PMRenderer;

import java.awt.Color;
import java.awt.Graphics;

public class Button {
    protected final String text;
    private final Runnable press;
    protected final int x;
    protected final int y;
    protected final int offset;

    public Button(String text, Runnable press, int x, int y, int offset) {
        this.text = text;
        this.press = press;
        this.x = x;
        this.y = y;
        this.offset = offset;
    }

    protected int getX(Graphics graphics, boolean selected) {
        return (selected ? x + offset : x) - (graphics.getFontMetrics().stringWidth(text) / 2);
    }

    protected int getY(boolean selected) {
        return (selected ? y + offset : y);
    }

    protected Color getColor(boolean selected) {
        return selected ? Color.WHITE : Color.GRAY;
    }

    public void render(Graphics graphics, boolean selected) {
        graphics.setFont(PMRenderer.getBaseFont().deriveFont(72f));
        graphics.setColor(getColor(selected));
        graphics.drawString(text, getX(graphics, selected), getY(selected));
    }

    public void press() {
        press.run();
    }
}
