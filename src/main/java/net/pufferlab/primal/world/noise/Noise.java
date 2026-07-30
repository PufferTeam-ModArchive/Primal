package net.pufferlab.primal.world.noise;

import org.joml.Vector2f;

public class Noise {

    private final FastNoiseLite fastNoiseLite;

    public enum NoiseType {

        OpenSimplex2(FastNoiseLite.NoiseType.OpenSimplex2),
        OpenSimplex2S(FastNoiseLite.NoiseType.OpenSimplex2S),
        Cellular(FastNoiseLite.NoiseType.Cellular),
        Perlin(FastNoiseLite.NoiseType.Perlin),
        ValueCubic(FastNoiseLite.NoiseType.ValueCubic),
        Value(FastNoiseLite.NoiseType.Value);

        private final FastNoiseLite.NoiseType noiseType;

        NoiseType(FastNoiseLite.NoiseType noiseType) {
            this.noiseType = noiseType;
        }

        public FastNoiseLite.NoiseType getFNL() {
            return noiseType;
        }
    }

    public enum FractalType {

        None(FastNoiseLite.FractalType.None),
        FBm(FastNoiseLite.FractalType.FBm),
        Ridged(FastNoiseLite.FractalType.Ridged),
        PingPong(FastNoiseLite.FractalType.PingPong),
        DomainWarpProgressive(FastNoiseLite.FractalType.DomainWarpProgressive),
        DomainWarpIndependent(FastNoiseLite.FractalType.DomainWarpIndependent);

        private final FastNoiseLite.FractalType fractalType;

        FractalType(FastNoiseLite.FractalType fractalType) {
            this.fractalType = fractalType;
        }

        public FastNoiseLite.FractalType getFNL() {
            return fractalType;
        }
    }

    public enum DomainWarpType {

        OpenSimplex2(FastNoiseLite.DomainWarpType.OpenSimplex2),
        OpenSimplex2Reduced(FastNoiseLite.DomainWarpType.OpenSimplex2Reduced),
        BasicGrid(FastNoiseLite.DomainWarpType.BasicGrid);

        private final FastNoiseLite.DomainWarpType domainWarpType;

        DomainWarpType(FastNoiseLite.DomainWarpType domainWarpType) {
            this.domainWarpType = domainWarpType;
        }

        public FastNoiseLite.DomainWarpType getFNL() {
            return domainWarpType;
        }
    }

    public Noise(long seed) {
        this.fastNoiseLite = new FastNoiseLite(Long.hashCode(seed));
    }

    public Noise setNoise(NoiseType noiseType, float scale) {
        fastNoiseLite.SetNoiseType(noiseType.getFNL());
        fastNoiseLite.SetFrequency(scale);
        return this;
    }

    public Noise setFractal(FractalType fractalType, int octaves, float lacunarity, float gain,
        float weightedStrength) {
        fastNoiseLite.SetFractalType(fractalType.getFNL());
        fastNoiseLite.SetFractalOctaves(octaves);
        fastNoiseLite.SetFractalLacunarity(lacunarity);
        fastNoiseLite.SetFractalGain(gain);
        fastNoiseLite.SetFractalWeightedStrength(weightedStrength);
        return this;
    }

    public Noise setDomainWarp(DomainWarpType domainWarpType, float amplitude) {
        fastNoiseLite.SetDomainWarpType(domainWarpType.getFNL());
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
