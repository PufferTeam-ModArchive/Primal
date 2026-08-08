package net.pufferlab.primal.world.terrafirma.gen.noise;

import net.minecraft.world.World;
import net.pufferlab.primal.world.noise.Noise2D;
import net.pufferlab.primal.world.noise.OpenSimplex2D;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public class NoiseLayer {

    public World world;
    public long seed;

    public Noise2D temperatureNoise;
    public Noise2D rainfallNoise;
    public Noise2D vegetationNoise;
    public Noise2D forestnessNoise;
    public Noise2D rockinessNoise;
    public Noise2D detailNoise;

    public NoiseLayer(World world) {
        this.world = world;
        this.seed = world.getSeed();

        long temperatureSeed = seed + 200;
        long rainfallSeed = seed + 314;
        long vegetationSeed = seed + 234;
        long forestnessSeed = seed + 144;
        long rockinessSeed = seed + 4;

        long detailSeed = seed + 2415;
        long detailSmallSeed = seed + 2417;

        temperatureNoise = new OpenSimplex2D(temperatureSeed).spread(0.0005F)
            .octaves(3)
            .normalize();

        rainfallNoise = new OpenSimplex2D(rainfallSeed).spread(0.0005F)
            .octaves(3)
            .normalize();

        vegetationNoise = new OpenSimplex2D(vegetationSeed).spread(0.001F)
            .octaves(3)
            .normalize();

        forestnessNoise = new OpenSimplex2D(forestnessSeed).spread(0.004F)
            .octaves(3)
            .normalize();

        rockinessNoise = new OpenSimplex2D(rockinessSeed).spread(0.006F)
            .octaves(3)
            .normalize();

        detailNoise = new OpenSimplex2D(detailSeed).spread(0.01F)
            .octaves(3)
            .add(
                new OpenSimplex2D(detailSmallSeed).spread(0.1F)
                    .octaves(3)
                    .map(x -> x * 0.25F));
    }

    public void genNoiseLayers(ChunkNoiseData data, int chunkX, int chunkZ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunkX << 4) + x;
                int worldZ = (chunkZ << 4) + z;

                float temperature = temperatureNoise.noise(worldX, worldZ);
                float rainfall = rainfallNoise.noise(worldX, worldZ);
                float vegetation = vegetationNoise.noise(worldX, worldZ);
                float rockiness = rockinessNoise.noise(worldX, worldZ);
                float forestness = forestnessNoise.noise(worldX, worldZ);

                float detail = detailNoise.noise(worldX, worldZ);

                data.setTemperature(x, z, temperature);
                data.setRainfall(x, z, rainfall);
                data.setVegetation(x, z, vegetation);
                data.setRockiness(x, z, rockiness);
                data.setForestness(x, z, forestness);

                data.setDetail(x, z, detail);
            }
        }
    }
}
