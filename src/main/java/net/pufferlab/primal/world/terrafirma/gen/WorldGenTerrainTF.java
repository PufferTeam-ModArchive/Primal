package net.pufferlab.primal.world.terrafirma.gen;

import static net.pufferlab.primal.world.noise.Noise.*;

import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.utils.NoiseUtils;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.noise.Noise;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;

public class WorldGenTerrainTF {

    public Noise terrainNoise;
    public Noise hillNoise;
    public Noise detailNoise;
    public Noise detailSmallNoise;
    public Noise continentNoise;

    public static float[][] hillSpline = { { 0.1F, 0.00001F }, { 0.2F, 0.00032F }, { 0.3F, 0.00243F },
        { 0.4F, 0.01024F }, { 0.5F, 0.03125F }, { 0.6F, 0.07776F }, { 0.65F, 0.116029F }, { 0.7F, 0.16807F },
        { 0.75F, 0.237305F }, { 0.8F, 0.32768F }, { 0.85F, 0.47715F }, { 0.9F, 0.531441F }, { 1.0F, 0.6F }, };

    public WorldGenTerrainTF() {}

    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (seed != lastSeed) {
            lastSeed = seed;
            terrainNoise = new Noise(seed).setNoise(NoiseType.OpenSimplex2S, 0.01F)
                .setFractal(FractalType.FBm, 3, 2.4F, 0.5F, 0.630F)
                .setDomainWarp(DomainWarpType.OpenSimplex2, 10.0F);
            hillNoise = new Noise(seed + 4).setNoise(NoiseType.OpenSimplex2S, 0.006F)
                .setFractal(FractalType.FBm, 3, 2.4F, 0.5F, 0.230F);
            detailNoise = new Noise(seed + 10).setNoise(NoiseType.Perlin, 0.004F);
            detailSmallNoise = new Noise(seed + 20).setNoise(NoiseType.Perlin, 0.05F)
                .setFractal(FractalType.FBm, 3, 2.0F, 0.5F, 0.0F);
            continentNoise = new Noise(seed + 102).setNoise(NoiseType.OpenSimplex2, 0.002F);
        }
    }

    public void genTerrain(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;

                float terrainValue = NoiseUtils.normalize(terrainNoise.getNoise(worldX, worldZ));
                float detailValue = NoiseUtils.normalize(detailNoise.getNoise(worldX, worldZ));
                float hillValue = NoiseUtils.normalize(hillNoise.getNoise(worldX, worldZ));
                float hillSampledValue = NoiseUtils.sample(hillSpline, hillValue);
                float detailSmallValue = detailSmallNoise.getNoise(worldX, worldZ);
                float continentValue = NoiseUtils.normalize(continentNoise.getNoise(worldX, worldZ));

                float height = 100.0F;

                height -= NoiseUtils.fastpow(continentValue, 6) * 30.0F;
                height += (hillSampledValue + 0.1F) * 65.0F;

                ChunkDataTF data = ChunkDataTF.get(chunk);
                data.rockness[x][z] = hillValue;

                height += detailSmallValue * 3.0F;
                height += terrainValue * NoiseUtils.fastpow(detailValue, 2) * 80.0F;

                for (int y = Constants.minHeight; y <= Constants.maxHeight; y++) {

                    ExtendedBlockStorage array = WorldUtils.getStorage(chunk, y);
                    if (array == null) continue;

                    if (y == Constants.minHeight) {
                        WorldUtils.setChunkBlock(array, x, y, z, Blocks.bedrock, 0);
                    } else {
                        if (y < height) {
                            WorldUtils.setChunkBlock(array, x, y, z, Blocks.stone, 0);
                        } else {
                            if (y < Config.seaLevelTF.getInt()) {
                                WorldUtils.setChunkBlock(array, x, y, z, Blocks.water, 0);
                            }
                        }
                    }

                }
            }
        }
    }
}
