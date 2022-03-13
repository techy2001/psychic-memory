package mialee.psychicmemory.game.entities;

import mialee.psychicmemory.game.Board;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.window.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    private final Board board;
    public final Vec2d position;
    public final Vec2d velocity;
    private double rotation;

    public BufferedImage image = null;

    public Entity(Board board, Vec2d position, Vec2d velocity) {
        this.board = board;
        this.position = position;
        this.velocity = velocity;

        try {
            image = Renderer.loadSprite("assets/textures/cod.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void tick(float deltaTime) {
        position.add(velocity);
    }

    public void render(Graphics graphics) {
        if (image == null) return;
        graphics.drawImage(image, (int) position.x - (image.getWidth() / 2), (int) position.y - (image.getHeight() / 2), image.getWidth(), image.getHeight(), null);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
