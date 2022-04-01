package mialee.psychicmemory.math;

public class MathHelper {
    public static double lerpDouble(double amount, double start, double end) {
        return start + amount * (end - start);
    }
    public static int lerpInt(double amount, int start, int end) {
        return (int) (start + amount * (end - start));
    }
}
