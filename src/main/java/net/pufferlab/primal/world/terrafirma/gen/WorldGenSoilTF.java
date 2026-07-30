package net.pufferlab.primal.world.terrafirma.gen;

import net.minecraft.block.Block;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.*;
import net.pufferlab.primal.world.noise.Noise;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;

public class WorldGenSoilTF {

    public Noise rainfallNoise;
    public Noise depthNoise;

    public WorldGenSoilTF() {

    }

    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (seed != lastSeed) {
            lastSeed = seed;
            rainfallNoise = new Noise(seed + 314).setNoise(Noise.NoiseType.OpenSimplex2S, 0.0005F)
                .setFractal(Noise.FractalType.FBm, 3, 2.0F, 0.440F, 0.0F);
            depthNoise = new Noise(seed + 294).setNoise(Noise.NoiseType.Perlin, 0.005F);
        }
    }

    public void genSoil(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;
                int topY = chunk.getHeightValue(x, z);

                float rainfallValue = NoiseUtils.normalize(rainfallNoise.getNoise(worldX, worldZ));
                float depthValue = NoiseUtils.normalize(depthNoise.getNoise(worldX, worldZ));

                ChunkDataTF data = ChunkDataTF.get(chunk);

                data.rainfall[x][z] = rainfallValue;
                float elevationValue = data.rockness[x][z];

                int depthBlocks = Utils.floor(depthValue * ((1 - elevationValue) + 0.15F) * 6.0F);

                for (int i = 0; i < depthBlocks; i++) {
                    int y = topY - 1 - i;

                    Block block = Registry.dirt;
                    if (i == 0) {
                        block = Registry.grass;
                    }

                    ExtendedBlockStorage storage = WorldUtils.getStorage(chunk, y);

                    if (storage == null) continue;

                    Block blockReplacing = WorldUtils.getChunkBlock(storage, x, y, z);
                    if (BlockUtils.isNaturalStone(blockReplacing)) {
                        SoilType soil = SoilType.pickOneSoilType(chunk.worldObj, rainfallValue);
                        int meta = SoilType.getMeta(Constants.soilTypes, soil);

                        WorldUtils.setChunkBlock(storage, x, y, z, block, meta);
                    }
                }

            }
        }
    }
}
