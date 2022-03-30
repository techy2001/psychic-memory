package mialee.psychicmemory.game.entities.core;

import javax.swing.*;

public class BaseEntity {
    public final String name;
    public final ImageIcon image;
    public final double hitRadius;
    public final int visualSize;
    public final int health;

    public BaseEntity(String name, double hitRadius, int visualSize, int health, ImageIcon image) {
        this.name = name;
        this.hitRadius = hitRadius;
        this.visualSize = visualSize;
        this.health = health;
        this.image = image;
    }

    @Override
    public String toString() {
        return "\nBaseEntity: %s\nimage = %s\nhitRadius = %s\nvisualSize = %s\n".formatted(name, hitRadius, visualSize, image);
    }
}
