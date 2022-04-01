package mialee.psychicmemory.math;

public class Vec2i {
    public int x;
    public int y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i multiply(Vec2i vec) {
        this.x = this.x * vec.x;
        this.y = this.y * vec.y;
        return this;
    }

    public Vec2i multiply(int amount) {
        this.x = this.x * amount;
        this.y = this.y * amount;
        return this;
    }

    public Vec2i add(Vec2i vec) {
        this.x = this.x + vec.x;
        this.y = this.y + vec.y;
        return this;
    }

    public Vec2i lerp(Vec2i dest, double amount) {
        return new Vec2i(MathHelper.lerpInt(amount, this.x, dest.x), MathHelper.lerpInt(amount, this.y, dest.y));
    }

    @Override
    public String toString() {
        return "vec2i: %d, %d".formatted(x, y);
    }
}
