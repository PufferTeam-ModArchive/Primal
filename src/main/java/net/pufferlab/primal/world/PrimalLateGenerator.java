package net.pufferlab.primal.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.gen.WorldGenVein;

import cpw.mods.fml.common.IWorldGenerator;

public class PrimalLateGenerator implements IWorldGenerator {

    public static final WorldGenVein veinGen = new WorldGenVein();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);

        if (!WorldUtils.isTerraFirma(world)) {
            if (Config.oreVeins.getBoolean() && Config.oreVeinsWorldGen.getBoolean()) {
                veinGen.initNoiseSeed(world.getSeed());
                veinGen.genVein(chunk, random);
            }
        }
    }
}
