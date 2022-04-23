package mialee.psychicmemory.math;

/**
 * A class which stores two integer values.
 */
public class Vec2i {
    public int x;
    public int y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i add(Vec2i vec) {
        this.x = this.x + vec.x;
        this.y = this.y + vec.y;
        return this;
    }

    @Override
    public String toString() {
        return "vec2i: %d, %d".formatted(x, y);
    }
}
