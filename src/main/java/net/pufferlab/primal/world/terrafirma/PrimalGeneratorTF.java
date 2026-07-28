package net.pufferlab.primal.world.terrafirma;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.world.terrafirma.gen.WorldGenStrataTF;
import net.pufferlab.primal.world.terrafirma.gen.WorldGenTerrainTF;
import net.pufferlab.primal.world.terrafirma.gen.WorldGenVeinTF;

public class PrimalGeneratorTF {

    public WorldGenStrataTF worldGenStrata;
    public WorldGenVeinTF worldGenVein;
    public WorldGenTerrainTF worldGenTerrain;

    public void initGenerators(World world) {
        this.worldGenTerrain = new WorldGenTerrainTF();
        this.worldGenStrata = new WorldGenStrataTF();
        this.worldGenVein = new WorldGenVeinTF();
        this.worldGenTerrain.initNoiseSeed(world.getSeed());
        this.worldGenStrata.initNoiseSeed(world.getSeed());
        this.worldGenStrata.initBlockList();
        this.worldGenVein.initNoiseSeed(world.getSeed());
    }

    public void generate(Chunk chunk, Random randomChunk) {
        this.worldGenTerrain.genTerrain(chunk);
        if (Config.strataStoneTypes.getBoolean() && Config.strataWorldGenTF.getBoolean()) {
            this.worldGenStrata.genStrata(chunk);
        }

    }

    public void populate(Chunk chunk, Random randomChunk) {
        if (Config.oreVeins.getBoolean() && Config.oreVeinsWorldGenTF.getBoolean()) {
            this.worldGenVein.genVein(chunk, randomChunk);
        }
    }
}
