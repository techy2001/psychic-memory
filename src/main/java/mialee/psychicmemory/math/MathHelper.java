package mialee.psychicmemory.math;

public class MathHelper {
    public static double lerpDouble(double amount, double start, double end) {
        return start + amount * (end - start);
    }

    public static int lerpInt(double amount, int start, int end) {
        return (int) (start + amount * (end - start));
    }

    public static int clamp(int start, int end, int input) {
        if (input < start) {
            return start;
        } else if (input > end) {
            return end;
        }
        return input;
    }

    public static int clampLoop(int start, int end, int input) {
        if (input < start) {
            return clampLoop(start, end, (end - start + 1) + input);
        } else if (input > end) {
            return clampLoop(start, end, input - (end - start + 1));
        }
        return input;
    }

    public static double clampDouble(double start, double end, double input) {
        if (input < start) {
            return start;
        } else if (input > end) {
            return end;
        }
        return input;
    }
}