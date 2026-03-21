package net.pufferlab.primal.utils;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderFlat;
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

    public static Block getBlock(World world, int x, int y, int z) {
        Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
        int x2 = x & 15;
        int z2 = z & 15;
        ExtendedBlockStorage storage = WorldUtils.getStorage(chunk, y);
        if (storage == null) return null;

        return WorldUtils.getChunkBlock(storage, x2, y, z2);
    }

    public static int getBlockMetadata(World world, int x, int y, int z) {
        Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
        int x2 = x & 15;
        int z2 = z & 15;
        ExtendedBlockStorage storage = WorldUtils.getStorage(chunk, y);
        if (storage == null) return 0;

        return WorldUtils.getChunkBlockMetadata(storage, x2, y, z2);
    }

    public static void setBlock(World world, int x, int y, int z, Block block, int meta) {
        Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
        int x2 = x & 15;
        int z2 = z & 15;
        ExtendedBlockStorage storage = WorldUtils.getStorage(chunk, y);
        if (storage == null) return;

        WorldUtils.setChunkBlock(storage, x2, y, z2, block, meta);
    }

    public static ExtendedBlockStorage getStorage(Chunk chunk, int y) {
        ExtendedBlockStorage[] storageArray = chunk.getBlockStorageArray();
        int section = y >> 4;
        if (section < 0) return null;

        ExtendedBlockStorage storage = storageArray[section];

        if (storage == null) {
            storage = new ExtendedBlockStorage(section << 4, !chunk.worldObj.provider.hasNoSky);
            storageArray[section] = storage;
        }
        return storage;
    }

    public static double getPerlin(NoiseGeneratorPerlin noise, int x, int z, double scale) {
        return noise.func_151601_a(x * scale, z * scale);
    }

    public static BiomeGenBase getBiome(Chunk chunk, int x, int z) {
        return chunk.getBiomeGenForWorldCoords(x, z, chunk.worldObj.getWorldChunkManager());
    }

    public static int getPerlinValue(double noise, int number) {
        double n01 = (noise + 1.0) * 0.5;

        double scaled = n01 * (double) number;

        int choice = Utils.floor(scaled);
        return Math.min(number - 1, Math.max(0, choice));
    }

    public static boolean canDecorate(IChunkProvider chunkProvider, World world) {
        return !isFlatWorld(chunkProvider) || world.getWorldInfo()
            .getGeneratorOptions()
            .contains("decoration");
    }

    protected static final boolean isFlatWorld(IChunkProvider chunkProvider) {
        return chunkProvider instanceof ChunkProviderFlat && !chunkProvider.getClass()
            .getName()
            .equals("com.rwtema.extrautils.worldgen.Underdark.ChunkProviderUnderdark");
    }
}
