package net.pufferlab.primal.world.gen;

import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.utils.NoiseUtils;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.noise.Noise;

public class WorldGenTerrain {

    public Noise noise;
    public Noise noiseBiome;

    public float[][] spline2 = { { -1.0F, 0.01F }, { 1.0F, 1F }, };
    public float[][] spline = { { -1.0F, 70 }, { 1.0F, 255 } };

    public WorldGenTerrain() {

    }

    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (seed != lastSeed) {
            noise = new Noise(seed, 0.5F, 3);
            noiseBiome = new Noise(seed + 4541, 0.1F, 3);
        }
    }

    public void genTerrain(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;

                float scale = NoiseUtils.normalize(noiseBiome.getOctaveNoise(worldX, worldZ));
                float scaleN = NoiseUtils.sample(spline2, scale);
                float elevation = NoiseUtils.normalize(noise.getOctaveNoise(worldX * scaleN, worldZ * scaleN));
                float height = NoiseUtils.sample(spline, elevation);

                for (int y = 0; y <= Constants.maxHeight; y++) {

                    ExtendedBlockStorage array = WorldUtils.getStorage(chunk, y);
                    if (array == null) continue;

                    if (y < height) {
                        WorldUtils.setChunkBlock(array, x, y, z, Blocks.stone, 0);
                    }

                }
            }
        }
    }
}
