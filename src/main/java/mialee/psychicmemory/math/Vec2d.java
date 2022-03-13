package mialee.psychicmemory.math;

public class Vec2d {
    double x;
    double y;

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void multiply(Vec2d vec) {
        this.x = this.x * vec.x;
        this.y = this.y * vec.y;
    }

    public void add(Vec2d vec) {
        this.x = this.x + vec.x;
        this.y = this.y + vec.y;
    }

    public Vec2d lerp(Vec2d dest, double amount) {
        return new Vec2d(MathHelper.lerpDouble(amount, this.x, dest.x), MathHelper.lerpDouble(amount, this.y, dest.y));
    }
}
