package net.pufferlab.primal.utils;

public class HashUtils {

    private static final float totalRange = 4.0f * (float) Math.PI;
    private static final int totalBuckets = 128;

    // Precompute the multiplier to avoid division at runtime
    private static final float floatScale = totalBuckets / totalRange;

    public static int angleHashCode(float value) {
        return (int) (value * floatScale);
    }
}
