package mialee.psychicmemory.menu;

import javax.swing.*;
import java.awt.*;

public record Button(ImageIcon[] visuals, Runnable press, int x, int y, int offset) {
    public int getX(boolean selected) {
        return selected ? x + offset : x;
    }

    public int getY(boolean selected) {
        return selected ? y + offset : y;
    }

    public Image getImage(boolean selected) {
        return selected ? this.visuals[0].getImage() : this.visuals[1].getImage();
    }

    public void render(Graphics graphics, boolean selected) {
        if (visuals == null) return;
        Image image = getImage(selected);
        graphics.drawImage(image, getX(selected), getY(selected), image.getWidth(null), image.getHeight(null), null);
    }
}
