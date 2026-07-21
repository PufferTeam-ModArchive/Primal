package net.pufferlab.primal.world.noise;

public class Noise {

    FastNoiseLite fastNoiseLite;
    int octaveAmount;

    static {
        generateOctave(10);
    }

    public Noise(long seed, float scale, int octaveAmount) {
        FastNoiseLite noise = new FastNoiseLite(Long.hashCode(seed));
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        float defaultScale = 0.01F;
        noise.SetFrequency(defaultScale * scale);
        this.octaveAmount = octaveAmount;
        this.fastNoiseLite = noise;
    }

    public static float[][] octaves;

    public static void generateOctave(int amount) {
        float[][] octave = new float[amount][];
        for (int i = 0; i < amount; i++) {
            float pow = (float) Math.pow(2, i);
            octave[i] = new float[] { (1 / pow), pow, pow };
        }
        octaves = octave;
    }

    public float getNoise(float x, float z) {
        return fastNoiseLite.GetNoise(x, z);
    }

    public float getOctaveNoise(float x, float z) {
        float value = 0.0F;
        float valueAdd = 0.0F;
        for (int i = 0; i < octaveAmount; i++) {
            float[] octave = octaves[i];
            float a = octave[0];
            float b = octave[1];
            float c = octave[2];
            value += a * getNoise(x * b, z * c);
            valueAdd += a;
        }
        value = value / valueAdd;
        return value;
    }

    FastNoiseLite.Vector2 vector2 = new FastNoiseLite.Vector2(0.0F, 0.0F);

    public FastNoiseLite.Vector2 getDomainWarp(float x, float z) {
        vector2.x = x;
        vector2.y = z;
        fastNoiseLite.DomainWarp(vector2);
        return vector2;
    }
}
