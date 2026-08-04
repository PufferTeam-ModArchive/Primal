package net.pufferlab.primal.world.noise;

public class OpenSimplex2D implements Noise2D {

    final FastNoiseLite fnl;
    private float frequency;
    private float midpoint, amplitude;
    private boolean warp;
    private FastNoiseLite.Vector2 vector2;

    public OpenSimplex2D(long seed) {
        this(Long.hashCode(seed));
    }

    public OpenSimplex2D(int seed) {
        fnl = new FastNoiseLite(seed);
        fnl.SetFrequency(1f);
        fnl.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        fnl.SetFractalOctaves(1);
        vector2 = new FastNoiseLite.Vector2(0, 0);

        frequency = 1;
        midpoint = 0;
        amplitude = 1;
    }

    @Override
    public float noise(float x, float z) {
        if (warp) {
            vector2.x = x;
            vector2.y = z;
            fnl.DomainWarp(vector2);
            x = vector2.x;
            z = vector2.y;
        }
        return midpoint + fnl.GetNoise(x, z) * amplitude;
    }

    @Override
    public OpenSimplex2D octaves(int octaves) {
        fnl.SetFractalOctaves(octaves);
        fnl.SetFractalType(FastNoiseLite.FractalType.FBm);
        return this;
    }

    @Override
    public OpenSimplex2D spread(float scaleFactor) {
        frequency *= scaleFactor;
        fnl.SetFrequency(frequency);
        return this;
    }

    @Override
    public Noise2D warped(float amplitude) {
        fnl.SetDomainWarpAmp(amplitude);
        warp = true;
        return this;
    }

    @Override
    public OpenSimplex2D scaled(float min, float max) {
        return scaled(-1, 1, min, max);
    }

    @Override
    public OpenSimplex2D scaled(float oldMin, float oldMax, float min, float max) {
        assert oldMin == -1 && oldMax == 1;
        midpoint = (max + min) / 2;
        amplitude = (max - min) / 2;
        return this;
    }
}
