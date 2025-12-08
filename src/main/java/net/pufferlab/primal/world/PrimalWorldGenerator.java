package net.pufferlab.primal.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.world.gen.WorldGenGround;

public class PrimalWorldGenerator implements IWorldGenerator {

    WorldGenGround worldGenRock = new WorldGenGround(Registry.ground_rock, 0);

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {

    }
}
