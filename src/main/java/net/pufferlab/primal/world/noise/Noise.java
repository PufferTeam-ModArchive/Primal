package net.pufferlab.primal.world.noise;

import org.joml.Vector2f;

public class Noise {

    public FastNoiseLite fastNoiseLite;

    public Noise(long seed) {
        FastNoiseLite noise = new FastNoiseLite(Long.hashCode(seed));
        this.fastNoiseLite = noise;
    }

    public Noise setNoise(FastNoiseLite.NoiseType noiseType, float scale) {
        fastNoiseLite.SetNoiseType(noiseType);
        fastNoiseLite.SetFrequency(scale);
        return this;
    }

    public Noise setFractal(FastNoiseLite.FractalType fractalType, int octaves, float lacunarity, float gain,
        float weightedStrength) {
        fastNoiseLite.SetFractalType(fractalType);
        fastNoiseLite.SetFractalOctaves(octaves);
        fastNoiseLite.SetFractalLacunarity(lacunarity);
        fastNoiseLite.SetFractalGain(gain);
        fastNoiseLite.SetFractalWeightedStrength(weightedStrength);
        return this;
    }

    public Noise setDomainWarp(FastNoiseLite.DomainWarpType domainWarpType, float amplitude) {
        fastNoiseLite.SetDomainWarpType(domainWarpType);
        fastNoiseLite.SetDomainWarpAmp(amplitude);
        return this;
    }

    public float getNoise(float x, float z) {
        return fastNoiseLite.GetNoise(x, z);
    }

    FastNoiseLite.Vector2 vector2 = new FastNoiseLite.Vector2(0.0F, 0.0F);
    Vector2f vector = new Vector2f();

    public Vector2f getDomainWarp(float x, float z) {
        vector2.x = x;
        vector2.y = z;
        fastNoiseLite.DomainWarp(vector2);

        vector.x = vector2.x;
        vector.y = vector2.y;
        return vector;
    }
}
