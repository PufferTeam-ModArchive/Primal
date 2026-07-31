package net.pufferlab.primal.utils;

public class Mth {

    public static int floor(float value) {
        return (int) Math.floor(value);
    }

    public static int floor(double value) {
        return (int) Math.floor(value);
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    public static float clamp(float value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    public static int pow(int value) {
        return (value * value);
    }
}
