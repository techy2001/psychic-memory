package mialee.psychicmemory.menu;

import mialee.psychicmemory.Main;
import mialee.psychicmemory.PMRenderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * A button specific to the key rebindings menu, showing both the key and the text.
 * Renders a specific key's text gotten from {@link java.awt.event.KeyEvent#getKeyText(int)}.
 */
public class KeyButton extends Button {
    private final int key;

    public KeyButton(int key, String text, Runnable press, int x, int y, int offset) {
        super(text, press, x, y, offset);
        this.key = key;
    }

    protected int getX(Graphics graphics, boolean selected, boolean key) {
        return (selected ? x + offset : x) + (key ? graphics.getFontMetrics().stringWidth(" ") : -graphics.getFontMetrics().stringWidth(text));
    }

    protected int getY(boolean selected) {
        return (selected ? y + offset : y);
    }

    public void renderKeyButton(Graphics graphics, boolean selected, boolean rebinding) {
        graphics.setFont(PMRenderer.getBaseFont().deriveFont(48f));
        graphics.setColor(getColor(false));
        graphics.drawString(text, getX(graphics, selected, false), getY(selected));
        graphics.setColor(rebinding && selected ? Color.ORANGE : getColor(selected));
        graphics.drawString(KeyEvent.getKeyText(Main.SETTING_VALUES.getKeyByID(key)), getX(graphics, selected, true), getY(selected));
    }

    @Override
    public void render(Graphics graphics, boolean selected) {
        renderKeyButton(graphics, selected, false);
    }
}
