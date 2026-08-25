package net.pufferlab.primal.world.terrafirma.gen.noise;

import static net.pufferlab.primal.utils.HashUtils.hashString;

import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.Mth;
import net.pufferlab.primal.utils.Spline;
import net.pufferlab.primal.utils.SplineBezier;
import net.pufferlab.primal.world.noise.Noise2D;
import net.pufferlab.primal.world.noise.OpenSimplex2D;
import net.pufferlab.primal.world.noise.Perlin2D;
import net.pufferlab.primal.world.terrafirma.BiomesTF;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public class NoiseTerrain implements INoiseLayer {

    public long seed;

    public static final Spline continentalnessSpline;
    public static final Spline erosionSpline;
    public static final Spline peaksvalleysSpline;
    public static final Spline hillSpline;

    static {
        hillSpline = new Spline();
        hillSpline.addPoint(0.1F, 0.00001F);
        hillSpline.addPoint(0.2F, 0.00032F);
        hillSpline.addPoint(0.3F, 0.00243F);
        hillSpline.addPoint(0.4F, 0.01024F);
        hillSpline.addPoint(0.5F, 0.03125F);
        hillSpline.addPoint(0.6F, 0.07776F);
        hillSpline.addPoint(0.65F, 0.116029F);
        hillSpline.addPoint(0.7F, 0.16807F);
        hillSpline.addPoint(0.75F, 0.237305F);
        hillSpline.addPoint(0.8F, 0.32768F);
        hillSpline.addPoint(0.85F, 0.47715F);
        hillSpline.addPoint(0.9F, 0.631441F);
        hillSpline.addPoint(1.0F, 1.0F);

        continentalnessSpline = new Spline();
        continentalnessSpline.addPoint(0.0F, 1.0F);
        continentalnessSpline.addPoint(0.1F, 0.1F);
        continentalnessSpline.addPoint(0.35F, 0.2F);
        continentalnessSpline.addPoint(0.38F, 0.4F);
        continentalnessSpline.addPoint(0.51F, 0.5F);
        continentalnessSpline.addPoint(0.55F, 0.7F);
        continentalnessSpline.addPoint(0.6F, 0.75F);
        continentalnessSpline.addPoint(0.7F, 0.8F);
        continentalnessSpline.addPoint(1.0F, 1.0F);

        erosionSpline = new Spline();
        erosionSpline.addPoint(0.00F, 1.00F);
        erosionSpline.addPoint(0.08F, 0.70F);
        erosionSpline.addPoint(0.18F, 0.62F);
        erosionSpline.addPoint(0.30F, 0.40F);
        erosionSpline.addPoint(0.38F, 0.45F); // small bump
        erosionSpline.addPoint(0.48F, 0.05F);
        erosionSpline.addPoint(0.62F, 0.10F); // long flat
        erosionSpline.addPoint(0.72F, 0.23F);
        erosionSpline.addPoint(0.74F, 0.30F); // sharp rise
        erosionSpline.addPoint(0.80F, 0.30F); // plateau
        erosionSpline.addPoint(0.90F, 0.03F); // sharp fall
        erosionSpline.addPoint(0.96F, 0.00F);

        peaksvalleysSpline = new SplineBezier();
        peaksvalleysSpline.addPoint(0.0F, 0.0F);
        peaksvalleysSpline.addPoint(0.25F, 0.8F);
        peaksvalleysSpline.addPoint(0.5F, 0.2F);
        peaksvalleysSpline.addPoint(0.75F, 0.3F);
        peaksvalleysSpline.addPoint(1.0F, 1.0F);
    }

    public Noise2D terrainNoise;
    public Noise2D hillNoise;
    public Noise2D detailNoise;

    public Noise2D continentalnessNoise;
    public Noise2D erosionNoise;
    public Noise2D peaksvalleysNoise;

    public NoiseTerrain(World world) {
        this.seed = world.getSeed();

        int terrainSeed = hashString(seed, "terrain");
        int terrain2Seed = hashString(seed, "terrain2");
        int hillSeed = hashString(seed, "hill");
        int detailSeed = hashString(seed, "detail");
        int continentSeed = hashString(seed, "continent");
        int erosionSeed = hashString(seed, "erosion");
        int peaksvalleysSeed = hashString(seed, "peaks_valleys");

        terrainNoise = new OpenSimplex2D(terrainSeed).spread(0.01F)
            .octaves(3)
            .product(
                new Perlin2D(terrain2Seed).spread(0.004F)
                    .normalize()
                    .map(x -> { return Mth.fastpow(x, 2); }))
            .normalize();

        hillNoise = new OpenSimplex2D(hillSeed).spread(0.004F)
            .octaves(3)
            .normalize()
            .map(hillSpline::sample);

        detailNoise = new Perlin2D(detailSeed).spread(0.05F)
            .octaves(3);

        continentalnessNoise = new OpenSimplex2D(continentSeed).spread(0.002F)
            .octaves(3)
            .normalize()
            .map(continentalnessSpline::sample);

        erosionNoise = new OpenSimplex2D(erosionSeed).spread(0.004F)
            .octaves(3)
            .normalize()
            .map(erosionSpline::sample);

        peaksvalleysNoise = new OpenSimplex2D(peaksvalleysSeed).spread(0.008F)
            .octaves(3)
            .normalize()
            .map(peaksvalleysSpline::sample);
    }

    @Override
    public void generate(ChunkNoiseData data, int chunkX, int chunkZ) {
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
