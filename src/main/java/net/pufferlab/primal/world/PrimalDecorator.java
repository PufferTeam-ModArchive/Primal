package net.pufferlab.primal.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockStoneRaw;
import net.pufferlab.primal.blocks.BlockStoneSand;
import net.pufferlab.primal.world.gen.feature.WorldGenGroundRock;
import net.pufferlab.primal.world.gen.feature.WorldGenGroundShell;
import net.pufferlab.primal.world.gen.feature.WorldGenGroundcover;

import cpw.mods.fml.common.IWorldGenerator;

public class PrimalDecorator implements IWorldGenerator {

    public static final PrimalDecorator instance = new PrimalDecorator();

    public static WorldGenGroundcover worldGenRock = new WorldGenGroundRock();
    public static int rockPerChunk = 2;
    public static WorldGenGroundcover worldGenShell = new WorldGenGroundShell();
    public static int shellPerChunk = 3;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        if (!isFlatWorld(chunkGenerator) || world.getWorldInfo()
            .getGeneratorOptions()
            .contains("decoration")) {
            decorate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }

    public void decorate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        int x = (chunkX << 4);
        int z = (chunkZ << 4);
        BiomeGenBase biome = world.getBiomeGenForCoords(x + 8, z + 8);

        if (Config.rockWorldGen.getBoolean()) {
            for (int i = 0; i < rockPerChunk; i++) {
                int px = x + rand.nextInt(16) + 8;
                int pz = z + rand.nextInt(16) + 8;
                int py = world.getHeightValue(px, pz);
                Block block = world.getBlock(px, py - 5, pz);
                int meta = world.getBlockMetadata(px, py - 5, pz);
                if (block instanceof BlockStoneRaw) {
                    worldGenRock.generate(world, rand, px, py, pz, meta);
                }
            }
        }
        if (Config.shellWorldGen.getBoolean() && Utils.isBeachBiome(biome)) {
            for (int i = 0; i < shellPerChunk; i++) {
                int px = x + rand.nextInt(16) + 8;
                int pz = z + rand.nextInt(16) + 8;
                int py = world.getHeightValue(px, pz);
                Block block = world.getBlock(px, py - 1, pz);
                if (block instanceof BlockStoneSand) {
                    worldGenShell.generate(world, rand, px, py, pz);
                }
            }
        }
    }

    protected final boolean isFlatWorld(IChunkProvider chunkProvider) {
        return chunkProvider instanceof ChunkProviderFlat && !chunkProvider.getClass()
            .getName()
            .equals("com.rwtema.extrautils.worldgen.Underdark.ChunkProviderUnderdark");
    }
}
