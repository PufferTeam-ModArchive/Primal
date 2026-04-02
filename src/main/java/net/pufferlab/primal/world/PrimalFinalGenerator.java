package net.pufferlab.primal.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

public class PrimalFinalGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {

                int cx = chunkX + dx;
                int cz = chunkZ + dz;

                if (world.getChunkProvider()
                    .chunkExists(cx, cz)) {
                    ChunkPlacerData.tickPlacement(world, cx, cz);
                }
            }
        }
    }
}
