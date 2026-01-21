package net.pufferlab.primal.utils;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class WorldUtils {

    public static Block getChunkBlock(ExtendedBlockStorage array, int x, int y, int z) {
        return array.getBlockByExtId(x, y & 15, z);
    }

    public static int getChunkBlockMetadata(ExtendedBlockStorage array, int x, int y, int z) {
        return array.getExtBlockMetadata(x, y & 15, z);
    }

    public static void setChunkBlock(ExtendedBlockStorage array, int x, int y, int z, Block block, int meta) {
        array.func_150818_a/* setExtBlockID */(x, y & 15, z, block);
        array.setExtBlockMetadata(x, y & 15, z, meta & 15);
    }

    public static double getPerlin(NoiseGeneratorPerlin noise, int x, int z, double scale) {
        return noise.func_151601_a(x * scale, z * scale);
    }

    public static BiomeGenBase getBiome(Chunk chunk, int x, int z) {
        return chunk.getBiomeGenForWorldCoords(x, z, chunk.worldObj.getWorldChunkManager());
    }

    public static int getPerlinQuad(double noise) {
        double n01 = (noise + 1.0) * 0.5;

        double scaled = n01 * 4.0;

        int choice = (int) Math.floor(scaled);
        return Math.min(3, Math.max(0, choice));
    }

    public static int getPerlinNeg(double perlin) {
        if (perlin > 0) {
            return 1;
        }
        if (perlin < 0) {
            return -1;
        }
        return 1;
    }
}
