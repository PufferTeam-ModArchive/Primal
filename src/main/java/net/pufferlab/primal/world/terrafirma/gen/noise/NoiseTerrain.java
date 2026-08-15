package net.pufferlab.primal.world.terrafirma.gen.noise;

import static net.pufferlab.primal.world.terrafirma.gen.noise.NoiseSplines.*;

import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.Mth;
import net.pufferlab.primal.world.noise.Noise2D;
import net.pufferlab.primal.world.noise.OpenSimplex2D;
import net.pufferlab.primal.world.noise.Perlin2D;
import net.pufferlab.primal.world.terrafirma.BiomesTF;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public class NoiseTerrain {

    public long seed;

    public Noise2D terrainNoise;
    public Noise2D hillNoise;
    public Noise2D detailNoise;

    public Noise2D continentalnessNoise;
    public Noise2D erosionNoise;
    public Noise2D peaksvalleysNoise;

    public NoiseTerrain(World world) {
        this.seed = world.getSeed();

        terrainNoise = new OpenSimplex2D(seed).spread(0.01F)
            .octaves(3)
            .product(
                new Perlin2D(seed + 10).spread(0.004F)
                    .normalize()
                    .map(x -> { return Mth.fastpow(x, 2); }))
            .normalize();

        hillNoise = new OpenSimplex2D(seed + 4).spread(0.004F)
            .octaves(3)
            .normalize()
            .map(hillSpline::sample);

        detailNoise = new Perlin2D(seed + 20).spread(0.05F)
            .octaves(3);

        continentalnessNoise = new OpenSimplex2D(seed + 100).spread(0.002F)
            .octaves(3)
            .normalize()
            .map(continentalnessSpline::sample);

        erosionNoise = new OpenSimplex2D(seed + 200).spread(0.004F)
            .octaves(3)
            .normalize()
            .map(erosionSpline::sample);

        peaksvalleysNoise = new OpenSimplex2D(seed + 300).spread(0.008F)
            .octaves(3)
            .normalize()
            .map(peaksvalleysSpline::sample);
    }

    public void genTerrain(ChunkNoiseData data, int chunkX, int chunkZ) {
        for (int x = -1; x < 17; x++) {
            for (int z = -1; z < 17; z++) {
                int worldX = (chunkX << 4) + x;
                int worldZ = (chunkZ << 4) + z;

                float value = 110.0F;

                float continentValue = continentalnessNoise.noise(worldX, worldZ);

                float erosionValue = erosionNoise.noise(worldX, worldZ);

                float peaksValue = peaksvalleysNoise.noise(worldX, worldZ);

                float terrain = terrainNoise.noise(worldX, worldZ);

                float hill = hillNoise.noise(worldX, worldZ);

                float detail = detailNoise.noise(worldX, worldZ);

                value -= continentValue * 45.0F;
                value -= erosionValue * 20.0F;
                value += peaksValue * 40.0F;
                value += terrain * 30.0F;
                value += hill * 75.0F;
                value += detail * 3.0F;

                data.setHeight(x, z, (int) value);
                if (x >= 0 && z >= 0 && x < 16 && z < 16) {
                    if (value < Config.seaLevelTF.getInt()) {
                        data.setBiome(x, z, BiomesTF.ocean);
                    }
                }
            }
        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int center = data.getHeight(x, z);
                int maxDifference = 0;

                for (int dz = -1; dz <= 1; dz++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        if (dx == 0 && dz == 0) continue;

                        int height = data.getHeight(x + dx, z + dz);
                        maxDifference = Math.max(maxDifference, Math.abs(center - height));
                    }
                }

                data.setSteepness(x, z, maxDifference);
            }
        }
    }
}
