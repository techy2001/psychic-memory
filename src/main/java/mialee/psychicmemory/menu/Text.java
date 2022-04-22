package mialee.psychicmemory.menu;

import mialee.psychicmemory.PMRenderer;
import mialee.psychicmemory.lang.TranslatableText;

import java.awt.*;

public class Text {
    protected final String text;
    protected final float size;
    protected final int x;
    protected final int y;
    protected final Alignment alignment;

    public Text(TranslatableText text, float size, int x, int y, Alignment alignment) {
        this(text.toString(), size, x, y, alignment);
    }

    public Text(String text, float size, int x, int y, Alignment alignment) {
        this.text = text;
        this.size = size;
        this.x = x;
        this.y = y;
        this.alignment = alignment;
    }

    public void render(Graphics graphics) {
        graphics.setFont(PMRenderer.getBaseFont().deriveFont(size));
        graphics.setColor(Color.GRAY);
        int offset = switch (alignment) {
            case LEFT -> 0;
            case CENTER -> -(graphics.getFontMetrics().stringWidth(text) / 2);
            case RIGHT -> -(graphics.getFontMetrics().stringWidth(text));
        };
        graphics.drawString(text, x + offset, y);
    }
}
