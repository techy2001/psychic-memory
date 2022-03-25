package mialee.psychicmemory.game.entities;

import java.awt.image.BufferedImage;

public class EntityType {
    public final BufferedImage image;
    public final double hitRadius;
    public final int visualSize;

    public EntityType(BufferedImage image, double hitRadius, int visualSize) {
        this.hitRadius = hitRadius;
        this.image = image;
        this.visualSize = visualSize;
    }
}
