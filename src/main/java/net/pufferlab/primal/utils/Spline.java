package net.pufferlab.primal.utils;

public class Spline {

    public int size;
    public float[] locations = new float[2];
    public float[] points = new float[2];

    public int minSize;
    public float minValue;
    public int maxSize;
    public float maxValue;

    public void addPoint(float location, float point) {
        if (size >= locations.length) {
            float[] newLocations = new float[locations.length * 2];
            System.arraycopy(locations, 0, newLocations, 0, locations.length);
            locations = newLocations;
        }

        if (size >= points.length) {
            float[] newPoints = new float[points.length * 2];
            System.arraycopy(points, 0, newPoints, 0, points.length);
            points = newPoints;
        }

        if (location < locations[size] || point < points[size])
            throw new IllegalArgumentException("Should be added in ascending order.");
        locations[size] = location;
        points[size] = point;
        minSize = 0;
        maxSize = 0;
        size++;
        onPointAdded();
    }

    public float minValue() {
        if (size == minSize) {
            return minValue;
        }
        for (int i = 0; i < size; i++) {
            minValue = Math.min(minValue, locations[i]);
            minSize = size;
        }
        return minValue;
    }

    public float maxValue() {
        if (size == maxSize) {
            return maxValue;
        }
        for (int i = 0; i < size; i++) {
            maxValue = Math.max(maxValue, locations[i]);
            maxSize = size;
        }
        return maxValue;
    }

    public void onPointAdded() {}

    public float sample(float value) {
        if (size == 0) return 0.0F;

        if (size == 1) return points[0];

        if (value < locations[0]) {
            return Mth.interpolate(value, locations[0], points[0], locations[1], points[1]);
        }
        int lastIndex = size - 1;
        if (value > locations[lastIndex]) {
            return Mth.interpolate(
                value,
                locations[lastIndex - 1],
                points[lastIndex - 1],
                locations[lastIndex],
                points[lastIndex]);
        }
        for (int i = 0; i < size - 1; i++) {
            float location = locations[i];
            float locationNext = locations[i + 1];

            if (value >= location && value <= locationNext) {
                float point = points[i];
                float pointNext = points[i + 1];
                return Mth.interpolate(value, location, point, locationNext, pointNext);
            }
        }
        return 0.0F;
    }

}
