package mialee.psychicmemory.math;

/**
 * A class containing some useful static methods related to maths.
 */
public class MathHelper {
    /**
     * Gets the double which is midway between two other doubles.
     * @param amount Amount from 0 to 1 representing the lerp progress.
     * @param start Position to start from.
     * @param end Position to end at.
     * @return The number which is (amount) distance between (start) and (end).
     */
    public static double lerpDouble(double amount, double start, double end) {
        return start + amount * (end - start);
    }

    /**
     * Gets the integer which is midway between two other integers.
     * @param amount Amount from 0 to 1 representing the lerp progress.
     * @param start Position to start from.
     * @param end Position to end at.
     * @return The number which is (amount) distance between (start) and (end).
     */
    public static int lerpInt(double amount, int start, int end) {
        return (int) (start + amount * (end - start));
    }

    /**
     * Gets an int within the bound of (start) and (end), looping around if the input is too small or large.
     * @param start Minimum size of the output.
     * @param end Maximum size of the output.
     * @param input Inputted integer.
     * @return Integer looped around into the bounds.
     */
    public static int clampLoop(int start, int end, int input) {
        if (input < start) {
            return clampLoop(start, end, (end - start + 1) + input);
        } else if (input > end) {
            return clampLoop(start, end, input - (end - start + 1));
        }
        return input;
    }

    /**
     * Gets a double within the bound of (start) and (end), reducing or raising to be within the bound.
     * @param start Minimum size of the output.
     * @param end Maximum size of the output.
     * @param input Inputted double.
     * @return Double within the given bounds.
     */
    public static double clampDouble(double start, double end, double input) {
        if (input < start) {
            return start;
        } else if (input > end) {
            return end;
        }
        return input;
    }
}