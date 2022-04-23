package mialee.psychicmemory.menu;

import mialee.psychicmemory.PMRenderer;
import mialee.psychicmemory.lang.TranslatableText;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A class representing a button in a menu.
 */
public class Button {
    protected final String text;
    private final Runnable press;
    protected final int x;
    protected final int y;
    protected final int offset;

    /**
     * @param text Text which the button will show.
     * @param press Action to perform when pressed.
     * @param x Horizontal Position.
     * @param y Vertical Position.
     * @param offset Offset to move the button by when selected.
     */
    public Button(TranslatableText text, Runnable press, int x, int y, int offset) {
        this(text.toString(), press, x, y, offset);
    }

    /**
     * @param text Text which the button will show.
     * @param press Action to perform when pressed.
     * @param x Horizontal Position.
     * @param y Vertical Position.
     * @param offset Offset to move the button by when selected.
     */
    public Button(String text, Runnable press, int x, int y, int offset) {
        this.text = text;
        this.press = press;
        this.x = x;
        this.y = y;
        this.offset = offset;
    }

    /**
     * Gets the X Position of the button, effected by offset if the button is selected.
     * Position is centered, based on the font metrics.
     * @param graphics Graphics to get the font details from.
     * @param selected If this button is selected.
     * @return The X position to draw this button at.
     */
    protected int getX(Graphics graphics, boolean selected) {
        return (selected ? x + offset : x) - (graphics.getFontMetrics().stringWidth(text) / 2);
    }

    /**
     * Gets the Y Position of the button, effected by offset if the button is selected.
     * @param selected If this button is selected.
     * @return The Y position to draw this button at.
     */
    protected int getY(boolean selected) {
        return (selected ? y + offset : y);
    }

    /**
     * Selects the colour for the button, selected buttons are brighter than otherwise.
     * @param selected If this button is selected.
     * @return The colour to draw the button in.
     */
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
