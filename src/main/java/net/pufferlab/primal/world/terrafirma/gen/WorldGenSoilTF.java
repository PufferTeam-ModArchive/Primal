package net.pufferlab.primal.world.terrafirma.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.*;
import net.pufferlab.primal.world.noise.Noise;
import net.pufferlab.primal.world.terrafirma.BiomesTF;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;

public class WorldGenSoilTF {

    public Noise rainfallNoise;
    public Noise vegetationNoise;

    public WorldGenSoilTF() {

    }

    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (seed != lastSeed) {
            lastSeed = seed;
            rainfallNoise = new Noise(seed + 314).setNoise(Noise.NoiseType.OpenSimplex2S, 0.0005F)
                .setFractal(Noise.FractalType.FBm, 3, 2.0F, 0.440F, 0.0F);
            vegetationNoise = new Noise(seed + 234).setNoise(Noise.NoiseType.OpenSimplex2S, 0.001F)
                .setFractal(Noise.FractalType.FBm, 3, 2.0F, 0.440F, 0.0F);
        }
    }

    public void genSoil(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;

                float rainfallValue = NoiseUtils.normalize(rainfallNoise.getNoise(worldX, worldZ));
                float vegetationValue = NoiseUtils.normalize(vegetationNoise.getNoise(worldX, worldZ));

                ChunkDataTF data = ChunkDataTF.get(chunk);
                int topY = data.getHeight(x, z);

                data.setRainfall(x, z, rainfallValue);
                data.setVegetation(x, z, vegetationValue);
                float elevationValue = data.getRockiness(x, z);

                int depthBlocks = Utils.floor(((1 - elevationValue)) * 5.0F);

                for (int i = 0; i < depthBlocks; i++) {
                    int y = topY - 1 - i;

                    Block block = Registry.dirt;
                    if (i == 0) {
                        block = Registry.grass;
                    }
                    if (vegetationValue < 0.3F) {
                        block = Registry.dirt;
                    }
                    if (rainfallValue < 0.3F) {
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
                        SoilType soil = SoilType.pickOneSoilType(chunk.worldObj, rainfallValue);
                        int meta = SoilType.getMeta(Constants.soilTypes, soil);

                        WorldUtils.setChunkBlock(chunk, x, y, z, block, meta);
                    }
                }

            }
        }
    }
}
