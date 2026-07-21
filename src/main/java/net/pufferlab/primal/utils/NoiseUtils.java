package net.pufferlab.primal.utils;

public class NoiseUtils {

    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }

    public static float smoothstep(float x, float edge0, float edge1) {
        x = Math.max(0.0F, Math.min(1.0F, (x - edge0) / (edge1 - edge0)));
        return x * x * (3.0F - 2.0F * x);
    }

    public static float sample(float[][] pts, float x) {
        if (x <= pts[0][0]) return pts[0][1];

        for (int i = 0; i < pts.length - 1; i++) {

            float[] a = pts[i];
            float[] b = pts[i + 1];

            if (x <= b[0]) {

                float t = smoothstep(x, a[0], b[0]);

                return lerp(t, a[1], b[1]);
            }
        }

        return pts[pts.length - 1][1];
    }

    public static float lerpNormalize(float min, float max, float delta) {
        float deltaNormalized = normalize(delta);
        return lerp(deltaNormalized, min, max);
    }

    public static float normalize(float v) {
        return (v + 1.0F) / 2.0F;
    }

}
