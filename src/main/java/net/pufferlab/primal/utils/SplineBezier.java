package net.pufferlab.primal.utils;

public class SplineBezier extends Spline {

    public long[] coefficients;

    @Override
    public void onPointAdded() {
        coefficients = calculateCoefficient(size);
    }

    @Override
    public float sample(float value) {
        if (size == 0) return 0.0F;

        if (size == 1) return points[0];

        float t = (value - locations[0]) / (locations[size - 1] - locations[0]);

        return bezier(t);
    }

    private float bezier(float t) {
        float result = 0.0F;

        float u = 1.0F - t;
        int degree = size - 1;

        for (int i = 0; i < size; i++) {
            float weight = (coefficients[i] * Mth.pow(u, degree - i) * Mth.pow(t, i));

            result += points[i] * weight;
        }

        return result;
    }

    public static long[] calculateCoefficient(int points) {
        int degree = points - 1;

        long[] coeff = new long[points];

        for (int i = 0; i < points; i++) {
            coeff[i] = Mth.binomCoefficient(degree, i);
        }

        return coeff;
    }

}
