package net.pufferlab.primal.world.terrafirma;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.world.terrafirma.gen.*;

public class PrimalGeneratorTF {

    public WorldGenStrataTF worldGenStrata;
    public WorldGenVeinTF worldGenVein;
    public WorldGenTerrainTF worldGenTerrain;
    public WorldGenSoilTF worldGenSoil;
    public WorldGenVegetationTF worldGenVegetation;

    public void initGenerators(World world) {
        long seed = world.getSeed();
        this.worldGenTerrain = new WorldGenTerrainTF();
        this.worldGenStrata = new WorldGenStrataTF();
        this.worldGenVein = new WorldGenVeinTF();
        this.worldGenSoil = new WorldGenSoilTF();
        this.worldGenVegetation = new WorldGenVegetationTF();
        this.worldGenTerrain.initNoiseSeed(seed);
        this.worldGenStrata.initNoiseSeed(seed);
        this.worldGenStrata.initBlockList();
        this.worldGenVein.initNoiseSeed(seed);
        this.worldGenSoil.initNoiseSeed(seed);
        this.worldGenVegetation.initNoiseSeed(seed);
    }

    public void earlyGenerate(Chunk chunk, Random randomChunk) {
        this.worldGenTerrain.genTerrain(chunk);
    }

    public void generate(Chunk chunk, Random randomChunk) {
        if (Config.soilTypes.getBoolean()) {
            this.worldGenSoil.genSoil(chunk);
        }

        if (Config.strataStoneTypes.getBoolean() && Config.strataWorldGenTF.getBoolean()) {
            this.worldGenStrata.genStrata(chunk);
        }
    }

    public void populate(Chunk chunk, Random randomChunk) {
        if (Config.oreVeins.getBoolean() && Config.oreVeinsWorldGenTF.getBoolean()) {
            this.worldGenVein.genVein(chunk, randomChunk);
        }
        this.worldGenVegetation.genVegetation(chunk, randomChunk);
    }
}
