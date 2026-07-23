package net.pufferlab.primal.world.gen;

import static net.pufferlab.primal.world.noise.FastNoiseLite.*;

import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.utils.NoiseUtils;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.noise.Noise;

public class WorldGenTerrain {

    public Noise terrainNoise;
    public Noise detailNoise;
    public Noise detailSmallNoise;

    public WorldGenTerrain() {

    }

    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (seed != lastSeed) {
            lastSeed = seed;
            terrainNoise = new Noise(seed).setNoise(NoiseType.OpenSimplex2S, 0.01F)
                .setFractal(FractalType.FBm, 3, 2.0F, 0.5F, 0.0F);
            detailNoise = new Noise(seed + 10).setNoise(NoiseType.Perlin, 0.004F);
            detailSmallNoise = new Noise(seed + 20).setNoise(NoiseType.Perlin, 0.05F)
                .setFractal(FractalType.FBm, 3, 2.0F, 0.5F, 0.0F);

        }
    }

    public void genTerrain(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;

                float terrainValue = NoiseUtils.normalize(terrainNoise.getNoise(worldX, worldZ));
                float detailValue = NoiseUtils.normalize(detailNoise.getNoise(worldX, worldZ));
                float detailSmallValue = detailSmallNoise.getNoise(worldX, worldZ);

                float height = 100.0F;

                height += detailSmallValue * 2.0F;
                height += terrainValue * detailValue * 80.0F;

                for (int y = 0; y <= Constants.maxHeight; y++) {

                    ExtendedBlockStorage array = WorldUtils.getStorage(chunk, y);
                    if (array == null) continue;

                    if (y < height) {
                        WorldUtils.setChunkBlock(array, x, y, z, Blocks.stone, 0);
                    } else {
                        if (y < 105) {
                            WorldUtils.setChunkBlock(array, x, y, z, Blocks.water, 0);
                        }
                    }

                }
            }
        }
    }
}
