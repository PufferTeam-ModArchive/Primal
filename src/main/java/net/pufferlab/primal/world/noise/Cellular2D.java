package net.pufferlab.primal.world.noise;

public class Cellular2D implements Noise2D {

    final FastNoiseLite fnl;
    private float frequency;
    private float midpoint, amplitude;

    public Cellular2D(long seed) {
        this(Long.hashCode(seed));
    }

    public Cellular2D(int seed) {
        fnl = new FastNoiseLite(seed);
        fnl.SetFrequency(1f);
        fnl.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        fnl.SetFractalOctaves(1);

        frequency = 1;
        midpoint = 0;
        amplitude = 1;
    }

    @Override
    public float noise(float x, float z) {
        return midpoint + fnl.GetNoise(x, z) * amplitude;
    }

    @Override
    public Cellular2D octaves(int octaves) {
        fnl.SetFractalOctaves(octaves);
        fnl.SetFractalType(FastNoiseLite.FractalType.FBm);
        return this;
    }

    @Override
    public Cellular2D spread(float scaleFactor) {
        frequency *= scaleFactor;
        fnl.SetFrequency(frequency);
        return this;
    }

    public Cellular2D cellvalue() {
        fnl.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
        return this;
    }

    public Cellular2D cellhybrid() {
        fnl.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
        return this;
    }

    public Cellular2D celljitter(float jitter) {
        fnl.SetCellularJitter(jitter);
        return this;
    }

    @Override
    public Cellular2D scaled(float min, float max) {
        return scaled(-1, 1, min, max);
    }

    @Override
    public Cellular2D scaled(float oldMin, float oldMax, float min, float max) {
        assert oldMin == -1 && oldMax == 1;
        midpoint = (max + min) / 2;
        amplitude = (max - min) / 2;
        return this;
    }
}
