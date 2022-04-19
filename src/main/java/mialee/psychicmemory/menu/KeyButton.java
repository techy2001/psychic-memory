package mialee.psychicmemory.menu;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.window.PMRenderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

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
        graphics.drawString(KeyEvent.getKeyText(PsychicMemory.SETTING_VALUES.getKeyByID(key)), getX(graphics, selected, true), getY(selected));
    }

    @Override
    public void render(Graphics graphics, boolean selected) {
        renderKeyButton(graphics, selected, false);
    }
}
