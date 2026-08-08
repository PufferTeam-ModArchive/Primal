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

    public static float pow(float value, float exp) {
        return (float) Math.pow(value, exp);
    }

    public static float pow(float value, int exp) {
        return Mth.fastpow(value, exp);
    }

    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }

    public static float fastpow(float base, int exp) {
        float result = 1.0F;

        if (exp < 0) {
            base = 1.0F / base;
            exp = -exp;
        }

        while (exp > 0) {
            if ((exp & 1) != 0) {
                result *= base;
            }

            base *= base;
            exp >>= 1;
        }

        return result;
    }

    public static float smoothstep(float x, float edge0, float edge1) {
        x = Math.max(0.0F, Math.min(1.0F, (x - edge0) / (edge1 - edge0)));
        return x * x * (3.0F - 2.0F * x);
    }

    public static long binomCoefficient(long n, long k) {
        if (k < 0 || k > n) return 0;

        if (k > n - k) k = n - k;

        long c = 1;

        for (long i = 1; i <= k; i++) {
            c *= n--;
            c /= i;
        }

        return c;
    }

    public static float interpolate(float x, float x0, float y0, float x1, float y1) {
        return ((y0 * (x1 - x)) + (y1 * (x - x0))) / (x1 - x0);
    }

    public static float normalize(float v) {
        return (v + 1.0F) / 2.0F;
    }
}
