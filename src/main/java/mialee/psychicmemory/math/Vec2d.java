package mialee.psychicmemory.math;

public class Vec2d {
    public double x;
    public double y;

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d multiply(Vec2d vec) {
        this.x = this.x * vec.x;
        this.y = this.y * vec.y;
        return this;
    }

    public Vec2d multiply(double amount) {
        this.x = this.x * amount;
        this.y = this.y * amount;
        return this;
    }

    public Vec2d add(Vec2d vec) {
        this.x = this.x + vec.x;
        this.y = this.y + vec.y;
        return this;
    }

    public Vec2d lerp(Vec2d dest, double amount) {
        return new Vec2d(MathHelper.lerpDouble(amount, this.x, dest.x), MathHelper.lerpDouble(amount, this.y, dest.y));
    }

    @Override
    public String toString() {
        return "vec2d: %.2f, %.2f".formatted(x, y);
    }
}
