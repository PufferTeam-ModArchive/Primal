package net.pufferlab.primal.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.world.gen.WorldGenSoil;
import net.pufferlab.primal.world.gen.WorldGenStrata;

import cpw.mods.fml.common.IWorldGenerator;

public class PrimalWorldGenerator implements IWorldGenerator {

    public static final WorldGenStrata strataGen = new WorldGenStrata();
    public static final WorldGenSoil soilGen = new WorldGenSoil();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {

        if (Config.strataStoneTypes.getBoolean() && Config.strataWorldGen.getBoolean()) {
            if (strataGen.lastWorld != world) {
                strataGen.initNoiseSeed(world);
            }
            Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
            strataGen.genStrata(chunk);
        }

        if (Config.soilTypes.getBoolean() && Config.soilWorldGen.getBoolean()) {
            if (soilGen.lastWorld != world) {
                soilGen.initNoiseSeed(world);
            }
            Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
            soilGen.genSoil(chunk);
        }
    }

    protected final boolean isFlatWorld(IChunkProvider chunkProvider) {
        return chunkProvider instanceof ChunkProviderFlat && !chunkProvider.getClass()
            .getName()
            .equals("com.rwtema.extrautils.worldgen.Underdark.ChunkProviderUnderdark");
    }
}
