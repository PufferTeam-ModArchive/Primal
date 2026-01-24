package net.pufferlab.primal.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.world.gen.WorldGenSoil;
import net.pufferlab.primal.world.gen.WorldGenStrata;

import cpw.mods.fml.common.IWorldGenerator;

public class PrimalEarlyGenerator implements IWorldGenerator {

    public static final PrimalEarlyGenerator instance = new PrimalEarlyGenerator();

    public static final WorldGenStrata strataGen = new WorldGenStrata();
    public static final WorldGenSoil soilGen = new WorldGenSoil();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);

        if (Config.strataStoneTypes.getBoolean() && Config.strataWorldGen.getBoolean()) {
            if (strataGen.lastWorld != world) {
                strataGen.initNoiseSeed(world);
            }
            strataGen.genStrata(chunk);
        }

        if (Config.soilTypes.getBoolean() && Config.soilWorldGen.getBoolean()) {
            if (soilGen.lastWorld != world) {
                soilGen.initNoiseSeed(world);
            }
            soilGen.genSoil(chunk);
        }
    }
}
