package net.pufferlab.primal.world.terrafirma.gen;

import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.utils.Mth;
import net.pufferlab.primal.utils.NoiseUtils;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.noise.Noise2D;
import net.pufferlab.primal.world.noise.OpenSimplex2D;
import net.pufferlab.primal.world.noise.Perlin2D;
import net.pufferlab.primal.world.terrafirma.BiomesTF;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;

public class WorldGenTerrainTF {

    public Noise2D terrainNoise;
    public Noise2D hillNoise;
    public Noise2D detailNoise;
    public Noise2D detailSmallNoise;
    public Noise2D continentNoise;

    public static float[][] hillSpline = { { 0.1F, 0.00001F }, { 0.2F, 0.00032F }, { 0.3F, 0.00243F },
        { 0.4F, 0.01024F }, { 0.5F, 0.03125F }, { 0.6F, 0.07776F }, { 0.65F, 0.116029F }, { 0.7F, 0.16807F },
        { 0.75F, 0.237305F }, { 0.8F, 0.32768F }, { 0.85F, 0.47715F }, { 0.9F, 0.531441F }, { 1.0F, 0.7F }, };

    public WorldGenTerrainTF() {}

    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (seed != lastSeed) {
            lastSeed = seed;
            terrainNoise = new OpenSimplex2D(seed).spread(0.01F)
                .octaves(3)
                .normalize();

            detailNoise = new Perlin2D(seed + 10).spread(0.004F)
                .normalize()
                .map(x -> { return NoiseUtils.fastpow(x, 2); });

            hillNoise = new OpenSimplex2D(seed + 4).spread(0.006F)
                .octaves(3)
                .normalize();

            continentNoise = new OpenSimplex2D(seed + 12).spread(0.002F)
                .normalize()
                .map(x -> { return NoiseUtils.fastpow(x, 6); });

            detailSmallNoise = new Perlin2D(seed + 20).spread(0.05F)
                .octaves(3);
        }
    }

    public void genTerrain(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;

                float height = 100.0F;

                height -= continentNoise.noise(worldX, worldZ) * 30.0F;
                float hill = hillNoise.noise(worldX, worldZ);
                height += (NoiseUtils.sample(hillSpline, hill) + 0.1F) * 65.0F;

                ChunkDataTF data = ChunkDataTF.get(chunk);
                data.setRockiness(x, z, Mth.clamp(hill, 0.0F, 1.0F));
                height += detailSmallNoise.noise(worldX, worldZ) * 3.0F;

                float terrain = terrainNoise.noise(worldX, worldZ);
                float detail = detailNoise.noise(worldX, worldZ);

                height += terrain * detail * 80.0F;

                for (int y = Constants.minHeight; y <= Constants.maxHeight; y++) {
                    if (y == Constants.minHeight) {
                        WorldUtils.setChunkBlock(chunk, x, y, z, Blocks.bedrock, 0);
                    } else {
                        if (y < height) {
                            data.setHeight(x, z, y + 1);
                            WorldUtils.setChunkBlock(chunk, x, y, z, Blocks.stone, 0);
                        } else {
                            if (y < Config.seaLevelTF.getInt()) {
                                data.setBiome(x, z, BiomesTF.ocean);
                                WorldUtils.setChunkBlock(chunk, x, y, z, Blocks.water, 0);
                            }
                        }
                    }

                }
            }
        }
    }
}
