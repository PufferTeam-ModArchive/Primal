package net.pufferlab.primal.world.terrafirma.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.*;
import net.pufferlab.primal.world.noise.Noise2D;
import net.pufferlab.primal.world.noise.OpenSimplex2D;
import net.pufferlab.primal.world.terrafirma.BiomesTF;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;

public class WorldGenSoilTF {

    public Noise2D rainfallNoise;
    public Noise2D vegetationNoise;

    public WorldGenSoilTF() {

    }

    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (seed != lastSeed) {
            lastSeed = seed;
            rainfallNoise = new OpenSimplex2D(seed + 314).spread(0.0005F)
                .octaves(3)
                .normalize();
            vegetationNoise = new OpenSimplex2D(seed + 234).spread(0.001F)
                .octaves(3)
                .normalize();
        }
    }

    public void genSoil(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;

                float rainfall = rainfallNoise.noise(worldX, worldZ);
                float vegetation = vegetationNoise.noise(worldX, worldZ);

                ChunkDataTF data = ChunkDataTF.get(chunk);
                int topY = data.getHeight(x, z);

                data.setRainfall(x, z, rainfall);
                data.setVegetation(x, z, vegetation);
                float elevationValue = data.getRockiness(x, z);

                int depthBlocks = Mth.floor(((1 - elevationValue)) * 5.0F);

                for (int i = 0; i < depthBlocks; i++) {
                    int y = topY - 1 - i;

                    Block block = Registry.dirt;
                    if (i == 0) {
                        block = Registry.grass;
                    }
                    if (vegetation < 0.3F) {
                        block = Registry.dirt;
                    }
                    if (rainfall < 0.3F) {
                        block = Blocks.sand;
                    }
                    if (elevationValue > 0.65F) {
                        block = Blocks.gravel;
                    }

                    if (data.getBiome(x, z) == BiomesTF.ocean) {
                        block = Blocks.gravel;
                    }

                    Block blockReplacing = WorldUtils.getChunkBlock(chunk, x, y, z);
                    if (BlockUtils.isNaturalStone(blockReplacing)) {
                        SoilType soil = SoilType.pickOneSoilType(chunk.worldObj, rainfall);
                        int meta = SoilType.getMeta(Constants.soilTypes, soil);

                        WorldUtils.setChunkBlock(chunk, x, y, z, block, meta);
                    }
                }

            }
        }
    }
}
