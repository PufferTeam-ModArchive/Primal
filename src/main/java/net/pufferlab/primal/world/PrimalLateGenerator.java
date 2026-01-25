package net.pufferlab.primal.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.world.gen.WorldGenVein;
import net.pufferlab.primal.world.gen.WorldGenVeinThaumcraft;

import cpw.mods.fml.common.IWorldGenerator;

public class PrimalLateGenerator implements IWorldGenerator {

    public static final PrimalLateGenerator instance = new PrimalLateGenerator();
    public static final WorldGenVein veinGen = new WorldGenVein();
    public static final WorldGenVeinThaumcraft veinGenThaumcraft = new WorldGenVeinThaumcraft();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);

        if (Config.oreVeins.getBoolean() && Config.oreVeinsWorldGen.getBoolean()) {
            if (veinGen.lastWorld != world) {
                veinGen.initNoiseSeed(world);
            }
            veinGen.genVein(world, random, chunk, chunkX, chunkZ);
        }
        if (Mods.tc.isLoaded() && Config.thaumcraftOreVeins.getBoolean()
            && Config.thaumcraftOreVeinsWorldGen.getBoolean()) {
            if (veinGenThaumcraft.lastWorld != world) {
                veinGenThaumcraft.initNoiseSeed(world);
            }
            veinGenThaumcraft.genVein(world, random, chunk, chunkX, chunkZ);
        }
    }
}
